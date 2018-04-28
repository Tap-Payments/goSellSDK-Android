package company.tap.gosellapi.internal.api.api_service;

import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenRequest;
import company.tap.gosellapi.internal.api.requests.UpdateChargeRequest;
import company.tap.gosellapi.internal.api.responses.InitResponse;
import okhttp3.ResponseBody;
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
    Call<InitResponse> init();

    @POST(API_Constants.TOKEN)
    Call<Token> createToken(@Body CreateTokenRequest createTokenRequest);

    @GET(API_Constants.TOKENS + "/{" + API_Constants.TOKEN_ID + "}")
    Call<Token> retrieveToken(@Path(API_Constants.TOKEN_ID) String tokenId);

    @POST(API_Constants.CHARGES)
    Call<Charge> createCharge(@Body CreateChargeRequest createChargeRequest);

    @GET(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> retrieveCharge(@Path(API_Constants.CHARGE_ID) String chargeId);

    @PUT(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}")
    Call<Charge> updateCharge(@Path(API_Constants.CHARGE_ID) String chargeId, @Body UpdateChargeRequest updateChargeRequest);

    @GET(API_Constants.INIT)
    Call<ResponseBody> getPaymentTypes();
}
