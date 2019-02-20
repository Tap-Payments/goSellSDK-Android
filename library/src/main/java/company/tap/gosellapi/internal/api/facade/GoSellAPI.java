package company.tap.gosellapi.internal.api.facade;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.api_service.APIService;
import company.tap.gosellapi.internal.api.api_service.RetrofitHelper;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateOTPRequest;
import company.tap.gosellapi.internal.api.requests.CreateOTPVerificationRequest;
import company.tap.gosellapi.internal.api.requests.CreateSaveCardRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;

/**
 * The type Go sell api.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GoSellAPI {
    private APIService apiHelper;

    // Declare and pending requests to initialization
    private RequestManager requestManager;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager(apiHelper);
    }

    /**
     * Retrieve authorize.
     *
     * @param identifier      the identifier
     * @param requestCallback the request callback
     */
    public void retrieveAuthorize(String identifier, APIRequestCallback<Authorize> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAuthorize(identifier), requestCallback));
    }

    private static class SingletonCreationAdmin {
        private static final GoSellAPI INSTANCE = new GoSellAPI();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GoSellAPI getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    /**
     * Create charge.
     *
     * @param createChargeRequest the create charge request
     * @param requestCallback     the request callback
     */
//requests
    public void createCharge(final CreateChargeRequest createChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback));
    }


    /**
     * Create save card.
     *
     * @param createSaveCardRequest the create save card request
     * @param requestCallback       the request callback
     */
    public void createSaveCard(final CreateSaveCardRequest createSaveCardRequest, final APIRequestCallback<SaveCard> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createSaveCard(createSaveCardRequest), requestCallback));
    }


    /**
     * Retrieve charge.
     *
     * @param chargeId        the charge id
     * @param requestCallback the request callback
     */
    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback));
    }

    /**
     * Retrieve save card.
     *
     * @param saveCardId      the save card id
     * @param requestCallback the request callback
     */
    public void retrieveSaveCard(final String saveCardId,final APIRequestCallback<SaveCard> requestCallback){
        System.out.println("#################### saveCardId :"+saveCardId);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveSaveCard(saveCardId),requestCallback));
    }


    /**
     * Create authorize.
     *
     * @param createAuthorizeRequest the create authorize request
     * @param requestCallback        the request callback
     */
    public void createAuthorize(final CreateAuthorizeRequest createAuthorizeRequest, final APIRequestCallback<Authorize> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAuthorize(createAuthorizeRequest), requestCallback));
    }

    /**
     * Authenticate charge transaction.
     *
     * @param chargeIdentifier the charge identifier
     * @param createOTPRequest the create otp request
     * @param requestCallback  the request callback
     */
    public void authenticate_charge_transaction(final String chargeIdentifier, final CreateOTPVerificationRequest createOTPRequest, final APIRequestCallback<Charge> requestCallback){
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.authenticate(chargeIdentifier,createOTPRequest),requestCallback));
    }

    /**
     * Authenticate authorize transaction.
     *
     * @param authorizeIdentifier the authorize identifier
     * @param createOTPRequest    the create otp request
     * @param requestCallback     the request callback
     */
    public void authenticate_authorize_transaction(final String authorizeIdentifier, final CreateOTPVerificationRequest createOTPRequest,
                                                   final APIRequestCallback<Authorize> requestCallback){
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.authenticate_authorize_transaction(authorizeIdentifier,createOTPRequest),requestCallback));
    }


    /**
     * Request authenticate for charge transaction.
     *
     * @param chargeIdentifier the charge identifier
     * @param requestCallback  the request callback
     */
    public void request_authenticate_for_charge_transaction(final String chargeIdentifier,final APIRequestCallback<Charge> requestCallback){
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.request_authenticate(chargeIdentifier),requestCallback));
    }

    /**
     * Request authenticate for authorize transaction.
     *
     * @param authorizeIdentifier the authorize identifier
     * @param requestCallback     the request callback
     */
    public void request_authenticate_for_authorize_transaction(final String authorizeIdentifier,final APIRequestCallback<Authorize> requestCallback){
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.request_authenticate_authorization(authorizeIdentifier),requestCallback));
    }

    /**
     * Update charge.
     *
     * @param chargeId        the charge id
     * @param requestCallback the request callback
     */
    public void updateCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateCharge(chargeId), requestCallback));

    }

    /**
     * Retrieve bin lookup bin lookup.
     *
     * @param binNumber       the bin number
     * @param requestCallback the request callback
     */
    public void retrieveBINLookupBINLookup(String binNumber, APIRequestCallback<BINLookupResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBINLookup(binNumber), requestCallback));
    }

    /**
     * Retrieve address formats.
     *
     * @param requestCallback the request callback
     */
    public void retrieveAddressFormats(APIRequestCallback<AddressFormatsResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAddressFormats(), requestCallback));
    }

    /**
     * Create token with encrypted card.
     *
     * @param createTokenRequest the create token request
     * @param requestCallback    the request callback
     */
    public void createTokenWithEncryptedCard(CreateTokenWithCardDataRequest createTokenRequest, final APIRequestCallback<Token> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithEncryptedCard(createTokenRequest), requestCallback));
    }

    /**
     * Create token with existing card.
     *
     * @param createTokenWithExistingCardDataRequest the create token with existing card data request
     * @param requestCallback                        the request callback
     */
    public void createTokenWithExistingCard(final CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest, final APIRequestCallback<Token> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithExistingCard(createTokenWithExistingCardDataRequest), requestCallback));
    }

    /**
     * Gets payment options.
     *
     * @param paymentOptionsRequest the payment options request
     * @param requestCallback       the request callback
     */
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
