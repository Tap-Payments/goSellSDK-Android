package company.tap.gosellapi.internal.data_managers;

import android.util.Log;

import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CardRequest;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithEncryptedCardDataRequest;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.interfaces.CardRequestInterface;

public class GlobalDataManager {
    private SDKSettings SDKSettings;
    private PaymentInfo paymentInfo;
    private PaymentOptionsDataManager paymentOptionsDataManager;

    private GlobalDataManager() {

    }

    private static class SingletonHolder {
        private static final GlobalDataManager INSTANCE = new GlobalDataManager();
    }

    public static GlobalDataManager getInstance() {
        return GlobalDataManager.SingletonHolder.INSTANCE;
    }

    public SDKSettings getSDKSettings() {
        return SDKSettings;
    }

    public void setSDKSettings(SDKSettings SDKSettings) {
        this.SDKSettings = SDKSettings;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void createPaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {
        paymentOptionsDataManager = new PaymentOptionsDataManager(paymentOptionsResponse);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager(PaymentOptionsDataManager.PaymentOptionsDataListener dataListener) {
        return paymentOptionsDataManager.setListener(dataListener);
    }

    // Managing requests
    public void createTokenWithExistingCardData() {

    }

    public void createTokenWithEncryptedCardData(String cardNumber, String expMonth, String expYear, String cvv, final CardRequestInterface cardRequestInterface) {

        String encryptionKey = GlobalDataManager.getInstance().getSDKSettings().getData().getEncryption_key();

        CardRequest cardRequest = new CardRequest.Builder(cardNumber, expMonth, expYear, cvv, encryptionKey).build();

        String cryptedData = cardRequest.getCryptedData();

        CreateTokenCard createTokenCard = new CreateTokenCard(cryptedData);
        CreateTokenWithEncryptedCardDataRequest request = new CreateTokenWithEncryptedCardDataRequest.Builder(createTokenCard).build();

        GoSellAPI.getInstance().createTokenWithEncryptedCard(request, new APIRequestCallback<Token>() {
            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {
                Log.e("TEST", "SERIALIZED RESPONSE " + serializedResponse.toString());
                createCharge(cardRequestInterface);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                Log.e("TEST", "FAILURE");
            }
        });
    }

    public void createCharge(CardRequestInterface cardRequestInterface) {

        // Configure request body
        Source source = new Source("src_kw.knet");
        PhoneNumber phoneNumber = new PhoneNumber("965", "77777777");
        CustomerInfo customerInfo = new CustomerInfo("Customer", "Customerenko", "so@me.mail", phoneNumber);
        Redirect redirect = new Redirect("gosellsdk://return_url");

//        Order order = new Order(dataSource.getPaymentOptionsResponse().getOrderID());

        CreateChargeRequest request = new CreateChargeRequest
                .Builder(100, "KWD", 20, false)
                .customer(customerInfo)
//                .order(order)
                .redirect(redirect)
                .source(source)
                .build();

        // Configure request callbacks
        APIRequestCallback<Charge> requestCallback = new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {
                Log.d("Web Payment", "onSuccess createCharge: serializedResponse:" + serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                Log.d("Web Payment", "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
            }
        };

        // Create charge
        GoSellAPI.getInstance().createCharge(request, requestCallback);
    }

}
