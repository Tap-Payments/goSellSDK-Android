package company.tap.gosellapi.internal.api.api_service;

import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithEncryptedCardDataRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
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

public interface APIService {
    @GET(API_Constants.INIT)
    Call<SDKSettings> init();

    @GET(API_Constants.TOKENS + "/{" + API_Constants.TOKEN_ID + "}")
    Call<Token> retrieveToken(@Path(API_Constants.TOKEN_ID) String tokenId);

    @GET(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> retrieveCharge(@Path(API_Constants.CHARGE_ID) String chargeId);

    @GET(API_Constants.BIN + "/{" + API_Constants.BIN_LOOKUP + "}")
    Call<BINLookupResponse> retrieveBINLookup(@Path(API_Constants.BIN_LOOKUP) String binNumber);

    @GET(API_Constants.BILLING_ADDRESS)
    Call<AddressFormatsResponse> retrieveAddressFormats();

    @POST(API_Constants.TOKEN)
    Call<Token> createToken(@Body CreateTokenRequest createTokenRequest);

    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithEncryptedCard(@Body CreateTokenWithEncryptedCardDataRequest createTokenWithEncryptedDataRequest);

    @POST(API_Constants.TOKEN)
    Call<Token> createTokenWithExistingCard(@Body CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest);

    @POST(API_Constants.CHARGES)
    Call<Charge> createCharge(@Body CreateChargeRequest createChargeRequest);

    @POST(API_Constants.PAYMENT_TYPES)
    Call<PaymentOptionsResponse> getPaymentTypes(@Body PaymentOptionsRequest paymentOptionsRequest);

    @PUT(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> updateCharge(@Path(API_Constants.CHARGE_ID) String chargeId);


}
