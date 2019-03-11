package company.tap.gosellapi.internal.api.api_service;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.enums.AuthenticationType;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateOTPVerificationRequest;
import company.tap.gosellapi.internal.api.requests.CreateSaveCardRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by eugene.goltsev on 13.02.2018.
 * <br>
 * Interface for Retrofit methods
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIService {

    /**
     * Init call.
     *
     * @return the call
     */
    @GET(API_Constants.INIT)
    Call<SDKSettings> init();

    /**
     * Retrieve token call.
     *
     * @param tokenId the token id
     * @return the call
     */
    @GET(API_Constants.TOKENS + "/{" + API_Constants.TOKEN_ID + "}")
    Call<Token> retrieveToken(@Path(API_Constants.TOKEN_ID) String tokenId);


    /**
     * Retrieve bin lookup call.
     *
     * @param binNumber the bin number
     * @return the call
     */
    @GET(API_Constants.BIN + "/{" + API_Constants.BIN_LOOKUP + "}")
    Call<BINLookupResponse> retrieveBINLookup(@Path(API_Constants.BIN_LOOKUP) String binNumber);

    /**
     * Retrieve address formats call.
     *
     * @return the call
     */
    @GET(API_Constants.BILLING_ADDRESS)
    Call<AddressFormatsResponse> retrieveAddressFormats();

    /**
     * Create token with encrypted card call.
     *
     * @param createTokenWithEncryptedDataRequest the create token with encrypted data request
     * @return the call
     */
    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithEncryptedCard(@Body CreateTokenWithCardDataRequest createTokenWithEncryptedDataRequest);

    /**
     * Create token with existing card call.
     *
     * @param createTokenWithExistingCardDataRequest the create token with existing card data request
     * @return the call
     */
    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithExistingCard(@Body
                                                CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest);

    /**
     * Create charge call.
     *
     * @param createChargeRequest the create charge request
     * @return the call
     */
    @POST(API_Constants.CHARGES)
    Call<Charge> createCharge(@Body CreateChargeRequest createChargeRequest);

    /**
     * Create save card call.
     *
     * @param createSaveCardRequest the create save card request
     * @return the call
     */
    @POST(API_Constants.SAVE_CARD)
    Call<SaveCard> createSaveCard(@Body CreateSaveCardRequest createSaveCardRequest);

    /**
     * Retrieve charge call.
     *
     * @param chargeId the charge id
     * @return the call
     */
    @GET(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> retrieveCharge(@Path(API_Constants.CHARGE_ID) String chargeId);

    /**
     * Retrieve save card call.
     *
     * @param saveCardId the save card id
     * @return the call
     */
    @GET(API_Constants.SAVE_CARD +"/{"+API_Constants.SAVE_CARD_ID+"}")
    Call<SaveCard> retrieveSaveCard(@Path(API_Constants.SAVE_CARD_ID) String saveCardId);

    /**
     * Create authorize call.
     *
     * @param createAuthorizeRequest the create authorize request
     * @return the call
     */
    @POST(API_Constants.AUTHORIZE)
    Call<Authorize> createAuthorize(@Body CreateAuthorizeRequest createAuthorizeRequest);


    /**
     * Retrieve authorize call.
     *
     * @param authorizeId the authorize id
     * @return the call
     */
    @GET(API_Constants.AUTHORIZE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Authorize> retrieveAuthorize(@Path(API_Constants.AUTHORIZE_ID) String authorizeId);

    /**
     * Authenticate call.
     *
     * @param chargeID                     the charge id
     * @param createOTPVerificationRequest the create otp verification request
     * @return the call
     */
    @POST(API_Constants.CHARGES +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> authenticate(@Path(API_Constants.CHARGE_ID )String chargeID, @Body
                              CreateOTPVerificationRequest createOTPVerificationRequest);


    /**
     * Authenticate authorize transaction call.
     *
     * @param authorizeID                  the authorize id
     * @param createOTPVerificationRequest the create otp verification request
     * @return the call
     */
    @POST(API_Constants.AUTHORIZE +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Authorize> authenticate_authorize_transaction(@Path(API_Constants.AUTHORIZE_ID )String authorizeID, @Body
        CreateOTPVerificationRequest createOTPVerificationRequest);


    /**
     * Request authenticate call.
     *
     * @param chargeID the charge id
     * @return the call
     */
    @PUT(API_Constants.CHARGES +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> request_authenticate(@Path(API_Constants.CHARGE_ID )String chargeID);

    /**
     * Request authenticate authorization call.
     *
     * @param authorizeID the authorize id
     * @return the call
     */
    @PUT(API_Constants.AUTHORIZE +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Authorize> request_authenticate_authorization(@Path(API_Constants.AUTHORIZE_ID )String authorizeID);

    /**
     * Gets payment options.
     *
     * @param paymentOptionsRequest the payment options request
     * @return the payment options
     */
    @POST(API_Constants.PAYMENT_TYPES)
    Call<PaymentOptionsResponse> getPaymentOptions(@Body PaymentOptionsRequest paymentOptionsRequest);

    /**
     * Update charge call.
     *
     * @param chargeId the charge id
     * @return the call
     */
    @PUT(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> updateCharge(@Path(API_Constants.CHARGE_ID) String chargeId);


    /**
     * Delete Saved Card
     * @param customerId
     * @param cardId
     * @return
     */
   @DELETE(API_Constants.DELETE_CARD+"/{"+API_Constants.CUSTOMER_ID+"}/"+"{"+API_Constants.CARD_ID+"}")
    Call<DeleteCardResponse> deleteCard(@Path(API_Constants.CUSTOMER_ID) String customerId,@Path(API_Constants.CARD_ID) String cardId);






}
