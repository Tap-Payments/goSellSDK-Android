package company.tap.gosellapi.internal.api.api_service;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.enums.AuthenticationType;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateOTPVerificationRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET(API_Constants.INIT)
    Call<SDKSettings> init();

    @GET(API_Constants.TOKENS + "/{" + API_Constants.TOKEN_ID + "}")
    Call<Token> retrieveToken(@Path(API_Constants.TOKEN_ID) String tokenId);



    @GET(API_Constants.BIN + "/{" + API_Constants.BIN_LOOKUP + "}")
    Call<BINLookupResponse> retrieveBINLookup(@Path(API_Constants.BIN_LOOKUP) String binNumber);

    @GET(API_Constants.BILLING_ADDRESS)
    Call<AddressFormatsResponse> retrieveAddressFormats();

    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithEncryptedCard(@Body CreateTokenWithCardDataRequest createTokenWithEncryptedDataRequest);

    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithExistingCard(@Body
                                                CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest);

    @POST(API_Constants.CHARGES)
    Call<Charge> createCharge(@Body CreateChargeRequest createChargeRequest);

    @GET(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> retrieveCharge(@Path(API_Constants.CHARGE_ID) String chargeId);

    @POST(API_Constants.AUTHORIZE)
    Call<Authorize> createAuthorize(@Body CreateAuthorizeRequest createAuthorizeRequest);


    @GET(API_Constants.AUTHORIZE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Authorize> retrieveAuthorize(@Path(API_Constants.AUTHORIZE_ID) String authorizeId);

    @POST(API_Constants.CHARGES +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> authenticate(@Path(API_Constants.CHARGE_ID )String chargeID, @Body
                              CreateOTPVerificationRequest createOTPVerificationRequest);


    @POST(API_Constants.AUTHORIZE +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Charge> authenticate_authorize_transaction(@Path(API_Constants.AUTHORIZE_ID )String authorizeID, @Body
        CreateOTPVerificationRequest createOTPVerificationRequest);


    @PUT(API_Constants.CHARGES +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> request_authenticate(@Path(API_Constants.CHARGE_ID )String chargeID);

    @PUT(API_Constants.AUTHORIZE +"/"+ API_Constants.AUTHENTICATE + "/{" + API_Constants.AUTHORIZE_ID + "}")
    Call<Charge> request_authenticate_authorization(@Path(API_Constants.AUTHORIZE_ID )String authorizeID);

    @POST(API_Constants.PAYMENT_TYPES)
    Call<PaymentOptionsResponse> getPaymentOptions(@Body PaymentOptionsRequest paymentOptionsRequest);

    @PUT(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> updateCharge(@Path(API_Constants.CHARGE_ID) String chargeId);


}
