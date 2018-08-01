package company.tap.gosellapi.internal.data_managers;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CardRequest;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithEncryptedCardDataRequest;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.interfaces.GoSellPaymentDataSource;
import company.tap.gosellapi.internal.utils.AmountCalculator;

public class GlobalDataManager {

    private GoSellPaymentDataSource dataSource;

    private SDKSettings SDKSettings;
    private PaymentOptionsRequest paymentOptionsRequest;
    private PaymentOptionsDataManager paymentOptionsDataManager;
    private BINLookupResponse binLookupResponse;

    private GlobalDataManager() {

    }

    private static class SingletonHolder {
        private static final GlobalDataManager INSTANCE = new GlobalDataManager();
    }

    public static GlobalDataManager getInstance() {
        return GlobalDataManager.SingletonHolder.INSTANCE;
    }

    public GoSellPaymentDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(GoSellPaymentDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SDKSettings getSDKSettings() {
        return SDKSettings;
    }

    public void setSDKSettings(SDKSettings SDKSettings) {
        this.SDKSettings = SDKSettings;
    }

    public PaymentOptionsRequest getPaymentOptionsRequest() {
        return paymentOptionsRequest;
    }

    public BINLookupResponse getBinLookupResponse() {
        return binLookupResponse;
    }

    public void setPaymentOptionsRequest(PaymentOptionsRequest paymentOptionsRequest) {
        this.paymentOptionsRequest = paymentOptionsRequest;
    }

    public void setBinLookupResponse(BINLookupResponse binLookupResponse) {
        this.binLookupResponse = binLookupResponse;
    }

    public void createPaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {
        paymentOptionsDataManager = new PaymentOptionsDataManager(paymentOptionsResponse);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager(PaymentOptionsDataManager.PaymentOptionsDataListener dataListener) {
        return paymentOptionsDataManager.setListener(dataListener);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager() {
        return paymentOptionsDataManager;
    }

    // Managing requests
    public void createTokenWithEncryptedCardData(String cardNumber, String expMonth, String expYear, String cvv, String nameOnCard, final boolean saveCard, final PaymentOption paymentOption, final APIRequestCallback<Charge> callback) {

        String encryptionKey = GlobalDataManager.getInstance().getSDKSettings().getData().getEncryption_key();

        CardRequest cardRequest = new CardRequest.Builder(cardNumber, expMonth, expYear, cvv, encryptionKey).build();

        String cryptedData = cardRequest.getCryptedData();

        CreateTokenCard createTokenCard = new CreateTokenCard(cryptedData);
        CreateTokenWithEncryptedCardDataRequest request = new CreateTokenWithEncryptedCardDataRequest.Builder(createTokenCard).build();

        APIRequestCallback<Token> tokenRequestCallback = new APIRequestCallback<Token>() {
            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {

                Source source = new Source(serializedResponse.getId());
                callChargeAPI(source, paymentOption, saveCard, callback);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {

            }
        };

        GoSellAPI.getInstance().createTokenWithEncryptedCard(request, tokenRequestCallback);
    }

    public void callChargeAPI(Source source, PaymentOption paymentOption, @Nullable Boolean saveCard, APIRequestCallback<Charge> callback) {

        saveCard = saveCard == null ? false : saveCard;

        PaymentOptionsResponse paymentOptionsResponse = getPaymentOptionsDataManager().getPaymentOptionsResponse();
        ArrayList<AmountedCurrency> supportedCurrencies = paymentOptionsResponse.getSupportedCurrencies();

        CustomerInfo customer = this.dataSource.getCustomerInfo();
        String orderID = this.getPaymentOptionsDataManager().getPaymentOptionsResponse().getOrderID();

        AmountedCurrency amountedCurrency = this.getPaymentOptionsDataManager().getSelectedCurrency();
        double fee = AmountCalculator.calculateExtraFeesAmount(paymentOption.getExtraFees(), supportedCurrencies, amountedCurrency);
        Order order = new Order(orderID);
        Redirect redirect = new Redirect(Constants.RETURN_URL, this.dataSource.getPostURL());
        String paymentDescription = this.dataSource.getPaymentDescription();
        HashMap<String, String> paymentMetadata = this.dataSource.getPaymentMetadata();
        Reference reference = this.dataSource.getPaymentReference();
        String statementDescriptor = this.dataSource.getPaymentStatementDescriptor();
        boolean require3DSecure = this.dataSource.getRequires3DSecure() || this.chargeRequires3DSecure();
        Receipt receiptSettings = this.dataSource.getReceiptSettings();

        CreateChargeRequest request = new CreateChargeRequest(

                amountedCurrency.getAmount(),
                amountedCurrency.getCurrency(),
                customer,
                fee,
                order,
                redirect,
                source,
                paymentDescription,
                paymentMetadata,
                reference,
                saveCard,
                statementDescriptor,
                require3DSecure,
                receiptSettings
        );

        GoSellAPI.getInstance().createCharge(request, callback);
    }

    private boolean chargeRequires3DSecure() {

        return !this.getSDKSettings().getData().getPermissions().contains(Permission.THREEDSECURE_DISABLED);
    }
}
