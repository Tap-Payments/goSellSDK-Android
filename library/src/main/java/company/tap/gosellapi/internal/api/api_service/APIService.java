package company.tap.gosellapi.api.facade;

import company.tap.gosellapi.api.model.BIN;
import company.tap.gosellapi.api.model.Charge;
import company.tap.gosellapi.api.model.Token;
import company.tap.gosellapi.api.requests.CaptureChargeRequest;
import company.tap.gosellapi.api.requests.CreateChargeRequest;
import company.tap.gosellapi.api.requests.CreateTokenRequest;
import company.tap.gosellapi.api.requests.UpdateChargeRequest;
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

interface APIService {
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

    @POST(API_Constants.CHARGES + "/{" + API_Constants.CHARGE_ID + "}/" + API_Constants.CAPTURE)
    Call<Charge> captureCharge(@Path(API_Constants.CHARGE_ID) String chargeId, @Body CaptureChargeRequest captureChargeRequest);

    @GET(API_Constants.BIN + "/{" + API_Constants.NUMBER + "}")
    Call<BIN> getBINNumberDetails(@Path(API_Constants.NUMBER) String binNumber);
}
