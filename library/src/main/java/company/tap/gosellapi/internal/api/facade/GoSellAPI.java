package company.tap.gosellapi.internal.api.facade;

import android.support.annotation.RestrictTo;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.api_service.APIService;
import company.tap.gosellapi.internal.api.api_service.RetrofitHelper;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.From;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.To;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateOTPVerificationRequest;
import company.tap.gosellapi.internal.api.requests.CreateSaveCardRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.requests.SupportedCurrunciesRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;
import company.tap.gosellapi.internal.api.responses.ListCardsResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.api.responses.SupportedCurreciesResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;

/**
 * The type Go sell api.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GoSellAPI {
    private APIService apiHelper;
    private PaymentOptionsResponse paymentOptionsResponse;

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
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAuthorize(identifier), requestCallback), false);
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
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback), true);
    }


    /**
     * Create save card.
     *
     * @param createSaveCardRequest the create save card request
     * @param requestCallback       the request callback
     */
    public void createSaveCard(final CreateSaveCardRequest createSaveCardRequest, final APIRequestCallback<SaveCard> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createSaveCard(createSaveCardRequest), requestCallback), true);
    }


    /**
     * Retrieve charge.
     *
     * @param chargeId        the charge id
     * @param requestCallback the request callback
     */
    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback), false);
    }

    /**
     * Retrieve save card.
     *
     * @param saveCardId      the save card id
     * @param requestCallback the request callback
     */
    public void retrieveSaveCard(final String saveCardId, final APIRequestCallback<SaveCard> requestCallback) {
//        System.out.println("#################### saveCardId :"+saveCardId);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveSaveCard(saveCardId), requestCallback), false);
    }


    /**
     * Create authorize.
     *
     * @param createAuthorizeRequest the create authorize request
     * @param requestCallback        the request callback
     */
    public void createAuthorize(final CreateAuthorizeRequest createAuthorizeRequest, final APIRequestCallback<Authorize> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAuthorize(createAuthorizeRequest), requestCallback), true);
    }

    /**
     * Authenticate charge transaction.
     *
     * @param chargeIdentifier the charge identifier
     * @param createOTPRequest the create otp request
     * @param requestCallback  the request callback
     */
    public void authenticate_charge_transaction(final String chargeIdentifier, final CreateOTPVerificationRequest createOTPRequest, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.authenticate(chargeIdentifier, createOTPRequest), requestCallback), true);
    }

    /**
     * Authenticate authorize transaction.
     *
     * @param authorizeIdentifier the authorize identifier
     * @param createOTPRequest    the create otp request
     * @param requestCallback     the request callback
     */
    public void authenticate_authorize_transaction(final String authorizeIdentifier, final CreateOTPVerificationRequest createOTPRequest,
                                                   final APIRequestCallback<Authorize> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.authenticate_authorize_transaction(authorizeIdentifier, createOTPRequest), requestCallback), true);
    }


    /**
     * Request authenticate for charge transaction.
     *
     * @param chargeIdentifier the charge identifier
     * @param requestCallback  the request callback
     */
    public void request_authenticate_for_charge_transaction(final String chargeIdentifier, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.request_authenticate(chargeIdentifier), requestCallback), true);
    }

    /**
     * Request authenticate for authorize transaction.
     *
     * @param authorizeIdentifier the authorize identifier
     * @param requestCallback     the request callback
     */
    public void request_authenticate_for_authorize_transaction(final String authorizeIdentifier, final APIRequestCallback<Authorize> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.request_authenticate_authorization(authorizeIdentifier), requestCallback), true);
    }

    /**
     * Update charge.
     *
     * @param chargeId        the charge id
     * @param requestCallback the request callback
     */
    public void updateCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateCharge(chargeId), requestCallback), true);

    }

    /**
     * Retrieve bin lookup bin lookup.
     *
     * @param binNumber       the bin number
     * @param requestCallback the request callback
     */
    public void retrieveBINLookupBINLookup(String binNumber, APIRequestCallback<BINLookupResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBINLookup(binNumber), requestCallback), false);
    }

    /**
     * Retrieve address formats.
     *
     * @param requestCallback the request callback
     */
    public void retrieveAddressFormats(APIRequestCallback<AddressFormatsResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveAddressFormats(), requestCallback), false);
    }

    /**
     * Create token with encrypted card.
     *
     * @param createTokenRequest the create token request
     * @param requestCallback    the request callback
     */
    public void createTokenWithEncryptedCard(CreateTokenWithCardDataRequest createTokenRequest, final APIRequestCallback<Token> requestCallback) {

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithEncryptedCard(createTokenRequest), requestCallback), true);
    }


    /**
     * Create token with existing card.
     *
     * @param createTokenWithExistingCardDataRequest the create token with existing card data request
     * @param requestCallback                        the request callback
     */
    public void createTokenWithExistingCard(final CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest, final APIRequestCallback<Token> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createTokenWithExistingCard(createTokenWithExistingCardDataRequest), requestCallback), true);
    }

    /**
     * Gets payment options.
     *
     * @param paymentOptionsRequest the payment options request
     * @param requestCallback       the request callback
     */
    public void getPaymentOptions(PaymentOptionsRequest paymentOptionsRequest, final APIRequestCallback<PaymentOptionsResponse> requestCallback) {

        // check paymentOptions Request
//        Log.d("#paymentOptionsRequest" , paymentOptionsRequest.getPaymentOptionRequestInfo());

        PaymentDataManager.getInstance().setPaymentOptionsRequest(paymentOptionsRequest);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getPaymentOptions(paymentOptionsRequest), new APIRequestCallback<PaymentOptionsResponse>() {
            @Override
            public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {
                paymentOptionsResponse = serializedResponse;
//                PaymentDataManager.getInstance().createPaymentOptionsDataManager(serializedResponse);
                requestCallback.onSuccess(responseCode, serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                requestCallback.onFailure(errorDetails);
            }
        }), true);
    }


    public void getSupportedCurrencies(BigDecimal amount, final APIRequestCallback<SupportedCurreciesResponse> requestCallback) {

        ArrayList<AmountedCurrency> emptySupportedCurrencies;
        String trxCurrency;

        if (paymentOptionsResponse != null) {
            emptySupportedCurrencies = paymentOptionsResponse.getSupportedCurrencies();
            trxCurrency = paymentOptionsResponse.getCurrency();

            if (emptySupportedCurrencies != null) {
                ArrayList<To> destinationCurrencyList = new ArrayList<>();

                for (AmountedCurrency amountedCurrency : emptySupportedCurrencies) {
                    destinationCurrencyList.add(new To(amountedCurrency.getCurrency(), amountedCurrency.getAmount()));
                }

                SupportedCurrunciesRequest supportedCurrunciesRequest = new SupportedCurrunciesRequest(
                        new From(trxCurrency, amount),
                        destinationCurrencyList,
                        "PROVIDER"
                );

                requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getSupportedCurrencies(supportedCurrunciesRequest), new APIRequestCallback<SupportedCurreciesResponse>() {
                    @Override
                    public void onSuccess(int responseCode, SupportedCurreciesResponse serializedResponse) {
                        System.out.println(" success....");

                        ArrayList<AmountedCurrency> newSupportedCurrList = new ArrayList<>();
                        ArrayList<AmountedCurrency> oldSupportedCurrList = paymentOptionsResponse.getSupportedCurrencies();
// check if null
                        int i = 0;
                        for (AmountedCurrency supportedCurr : oldSupportedCurrList) {
                            System.out.println("*****************************************");
                            for (; i< serializedResponse.getTo().size();i++) {
                                System.out.println(serializedResponse.getTo().get(i).getCurrency() + " >>>> " + serializedResponse.getTo().get(i).getValue());
                                System.out.println("supported : " + supportedCurr.getCurrency());
                                if (serializedResponse.getTo().get(i).getCurrency().equalsIgnoreCase(supportedCurr.getCurrency()))
                                    {
                                        supportedCurr.setAmount(serializedResponse.getTo().get(i).getValue());
                                        newSupportedCurrList.add(supportedCurr);
                                        break;
                                    }
                            }
                        }
                        System.out.println("=================================================");
                        for (AmountedCurrency mm : newSupportedCurrList) {
                            System.out.println("api curr:  " + mm.getAmount());
                        }
                        System.out.println("=================================================");
                        paymentOptionsResponse.setSupportedCurrencies(newSupportedCurrList);
                        PaymentDataManager.getInstance().createPaymentOptionsDataManager(paymentOptionsResponse);
                        requestCallback.onSuccess(responseCode, serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        requestCallback.onFailure(errorDetails);
                    }
                }), false);


            }

        }


    }

    /**
     * Delete Card
     *
     * @param customerId
     * @param cardId
     */
    public void deleteCard(String customerId, String cardId, final APIRequestCallback<DeleteCardResponse> requestCallback) {
//        Log.d("Delete Card :", "Customer Id: "+ customerId + " && Card Id : "+cardId);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.deleteCard(customerId, cardId), requestCallback), false);
    }


    public void listAllCards(String customer_id, final APIRequestCallback<ListCardsResponse> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.listAllCards(customer_id), requestCallback), false);
    }
}
