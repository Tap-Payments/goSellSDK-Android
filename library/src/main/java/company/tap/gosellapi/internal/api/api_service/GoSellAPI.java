package company.tap.gosellapi.internal.api.api_service;

import android.content.Context;
import android.support.annotation.RestrictTo;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.model.Charge;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.UpdateChargeRequest;
import company.tap.gosellapi.internal.api.responses.BaseResponse;
import company.tap.gosellapi.internal.api.responses.InitResponse;
import retrofit2.Call;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GoSellAPI {
    private APIService apiHelper;

    //auth information for headers
    private static String authToken;
    private static String applicationId;

    //init and pending requests
    private DataManager dataManager;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
        dataManager = new DataManager(apiHelper);
    }

    private static class SingletonHolder {
        private static final GoSellAPI INSTANCE = new GoSellAPI();
    }

    public static GoSellAPI getInstance() {
        return SingletonHolder.INSTANCE;
    }


    //auth information for headers
    public static void setAuthToken(Context context, String authToken) {
        GoSellAPI.authToken = authToken;
        GoSellAPI.applicationId = context.getPackageName();
    }

    static String getAuthToken() {
        return authToken;
    }

    static String getApplicationId() {
        return applicationId;
    }


    //requests
    public void createCharge(final CreateChargeRequest createChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        dataManager.request(new DataManager.DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback));
    }

    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        dataManager.request(new DataManager.DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback));
    }

    public void updateCharge(final String chargeId, final UpdateChargeRequest updateChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        dataManager.request(new DataManager.DelayedRequest<>(apiHelper.updateCharge(chargeId, updateChargeRequest), requestCallback));
    }
}
