package company.tap.gosellapi.internal.api.facade;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.api_service.APIService;
import company.tap.gosellapi.internal.api.api_service.RetrofitHelper;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GoSellAPI {
    private APIService apiHelper;

    // Declare and pending requests to initialization
    private RequestManager requestManager;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager(apiHelper);
    }

    public void retrieveAuthorize(String identifier, APIRequestCallback<Authorize> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAuthorize(identifier), requestCallback));
    }

    private static class SingletonCreationAdmin {
        private static final GoSellAPI INSTANCE = new GoSellAPI();
    }

    public static GoSellAPI getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    //requests
    public void createCharge(final CreateChargeRequest createChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback));
    }

    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback));
    }

    public void createAuthorize(final CreateAuthorizeRequest createAuthorizeRequest, final APIRequestCallback<Authorize> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAuthorize(createAuthorizeRequest), requestCallback));
    }

    public void updateCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateCharge(chargeId), requestCallback));

    }

    public void retrieveBINLookupBINLookup(String binNumber, APIRequestCallback<BINLookupResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBINLookup(binNumber), requestCallback));
    }

    public void retrieveAddressFormats(APIRequestCallback<AddressFormatsResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAddressFormats(), requestCallback));
    }

    public void createToken(CreateTokenWithCardDataRequest createTokenRequest, final APIRequestCallback<Token> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createToken(createTokenRequest), requestCallback));
    }


//    public void createTokenWithEncryptedCard(final CreateTokenWithCardDataRequest createTokenWithEncryptedCardDataRequest, final APIRequestCallback<Token> requestCallback) {
//        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithEncryptedCard(createTokenWithEncryptedCardDataRequest), requestCallback));
//    }
//
//    public void createTokenWithExistingCard(final CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest, final APIRequestCallback<Token> requestCallback) {
//        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithExistingCard(createTokenWithExistingCardDataRequest), requestCallback));
//    }

    public void getPaymentOptions(PaymentOptionsRequest paymentOptionsRequest, final APIRequestCallback<PaymentOptionsResponse> requestCallback) {

        // check paymentOptions Request
        System.out.println( "### paymentOptionsRequest ### " + paymentOptionsRequest.getPaymentOptionRequestInfo());



        PaymentDataManager.getInstance().setPaymentOptionsRequest(paymentOptionsRequest);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getPaymentOptions(paymentOptionsRequest), new APIRequestCallback<PaymentOptionsResponse>() {
            @Override
            public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {

                PaymentDataManager.getInstance().createPaymentOptionsDataManager(serializedResponse);
                requestCallback.onSuccess(responseCode, serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                requestCallback.onFailure(errorDetails);
            }
        }));
    }
}
