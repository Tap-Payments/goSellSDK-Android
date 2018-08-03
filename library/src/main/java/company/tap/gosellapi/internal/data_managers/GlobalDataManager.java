package company.tap.gosellapi.internal.data_managers;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.AuthenticationType;
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
import company.tap.gosellapi.internal.interfaces.ChargeObservable;
import company.tap.gosellapi.internal.interfaces.ChargeObserver;
import company.tap.gosellapi.internal.interfaces.GoSellPaymentDataSource;
import company.tap.gosellapi.internal.utils.AmountCalculator;

public class GlobalDataManager implements APIRequestCallback<Charge>, ChargeObservable {

    private GoSellPaymentDataSource dataSource;

    private SDKSettings SDKSettings;
    private PaymentOptionsRequest paymentOptionsRequest;
    private PaymentOptionsDataManager paymentOptionsDataManager;
    private BINLookupResponse binLookupResponse;

    private String currentChargeID;
    private ArrayList<ChargeObserver> observers;

    private GlobalDataManager() {

        observers = new ArrayList<>();
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

    public void initiatePayment(ChargeObserver observer, PaymentOption option) {
        addObserver(observer);

        Log.e("OkHttp", "INITIATE PAYMENT");

        switch (option.getPaymentType()) {
            case WEB:
                handleWebPayment(option);
                break;

            case CARD:
                handleCardPayment(option);
                break;
        }
    }

    private void handleCardPayment(PaymentOption option) {

    }

    private void handleWebPayment(PaymentOption option) {

        Log.e("OkHttp", "HANDLE WEB PAYMENT");
        Source source = new Source(option.getSourceId());
        callChargeAPI(source, option, null);
    }

    public void createTokenWithEncryptedCardData(String cardNumber, String expMonth, String expYear, String cvv, String nameOnCard, final boolean saveCard, final PaymentOption paymentOption, final APIRequestCallback<Charge> callback) {

        String encryptionKey = GlobalDataManager.getInstance().getSDKSettings().getData().getEncryptionKey();

        CardRequest cardRequest = new CardRequest.Builder(cardNumber, expMonth, expYear, cvv, encryptionKey).build();

        String cryptedData = cardRequest.getCryptedData();

        CreateTokenCard createTokenCard = new CreateTokenCard(cryptedData);
        CreateTokenWithEncryptedCardDataRequest request = new CreateTokenWithEncryptedCardDataRequest.Builder(createTokenCard).build();

        APIRequestCallback<Token> tokenRequestCallback = new APIRequestCallback<Token>() {
            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {

                Source source = new Source(serializedResponse.getId());
                callChargeAPI(source, paymentOption, saveCard);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {

            }
        };

        GoSellAPI.getInstance().createTokenWithEncryptedCard(request, tokenRequestCallback);
    }

    private void callChargeAPI(Source source, PaymentOption paymentOption, @Nullable Boolean saveCard) {

        Log.e("OkHttp", "CALL CHARGE API");

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

        GoSellAPI.getInstance().createCharge(request, this);
    }

    public void retrieveChargeAPI() {

        Log.e("OkHttp", "RETRIEVE CHARGE API");
        if (currentChargeID.isEmpty()) return;

        Log.e("OkHttp", "CURRENCT CHARGE ID " + currentChargeID);
        GoSellAPI.getInstance().retrieveCharge(currentChargeID, this);
    }

    @Override
    public void onSuccess(int responseCode, Charge serializedResponse) {
        Log.e("OkHttp", "ON SUCCESS");
        currentChargeID = serializedResponse.getId();

        for (ChargeObserver observer : observers) {
            observer.dataAcquired(serializedResponse);
        }

        handleResponse(serializedResponse);
    }

    @Override
    public void onFailure(GoSellError errorDetails) {
        Log.e("OkHttp", "ON FAILURE");
    }

    private final APIRequestCallback<Charge> chargeResponseCallback = new APIRequestCallback<Charge>() {

        @Override
        public void onSuccess(int responseCode, Charge serializedResponse) {


        }

        @Override
        public void onFailure(GoSellError errorDetails) {

        }
    };

    private boolean chargeRequires3DSecure() {

        return !this.getSDKSettings().getData().getPermissions().contains(Permission.THREEDSECURE_DISABLED);
    }

    private void handleResponse(Charge response) {

        Log.e("OkHttp", "RESPONSE STATUS " + response.getStatus());

        switch (response.getStatus()) {

            case INITIATED: // Charge initiated. Redirect or authentication required.
                checkInitiatedStatus(response);
                break;

            case IN_PROGRESS: // Charge status is unknown. Treat as failure.
            case ABANDONED: // Charge abandoned. Treat as failure.
            case CANCELLED: // Charge is cancelled. Treat as failure.
            case FAILED: // Charge is failed. Treat as failure.
            case DECLINED: // Charge is declined. Treat as failure.
            case RESTRICTED: // Charge is restricted. Treat as failure.
            case VOID: // Charge is voided. Treat as failure.

                notifyObserversResponseFailed();
                break;

            case CAPTURED: // Charge is successful. Treat as success.

                notifyObserversResponseSucceed();
                break;
        }
    }

    private void checkInitiatedStatus(Charge response) {

        if ( response.getAuthenticate() != null && response.getAuthenticate().getStatus() == AuthenticationStatus.INITIATED ) {

            switch (response.getAuthenticate().getType()) {

                case OTP:

                    notifyObserversOtpScreenNeedToShown();

                case BIOMETRICS:

                    break;
            }
            return;
        }

        String paymentURL = response.getRedirect().getUrl();
        if (paymentURL != null ) {

            notifyObserversWebScreenNeedToShown();
        }
    }

    @Override
    public void addObserver(ChargeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ChargeObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserversOtpScreenNeedToShown() {
        for (ChargeObserver observer : observers) {
            observer.otpScreenNeedToShown();
        }
    }

    @Override
    public void notifyObserversWebScreenNeedToShown() {
        for (ChargeObserver observer : observers) {
            observer.webScreenNeedToShown();
        }
    }

    @Override
    public void notifyObserversResponseSucceed() {
        for (ChargeObserver observer : observers) {
            observer.responseSucceed();
        }
    }

    @Override
    public void notifyObserversResponseFailed() {
        for (ChargeObserver observer : observers) {
            observer.responseFailed();
        }
    }
}
