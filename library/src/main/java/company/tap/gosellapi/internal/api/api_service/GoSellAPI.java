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
    private InitResponse initResponse;
    private ArrayList<DelayedRequest> delayedRequests;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
        delayedRequests = new ArrayList<>();
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
        request(new DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback));
    }

    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        request(new DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback));
    }

    public void updateCharge(final String chargeId, final UpdateChargeRequest updateChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        request(new DelayedRequest<>(apiHelper.updateCharge(chargeId, updateChargeRequest), requestCallback));
    }


    //all requests are wrapped in DelayedRequest, until init() would be finished
    private void request(DelayedRequest delayedRequest) {
        delayedRequests.add(delayedRequest);
        if (initResponse == null) {
            init();
        } else {
            runDelayedRequests();
        }
    }

    private void init() {
        apiHelper
                .init()
                .enqueue(new BaseCallback<>(new APIRequestCallback<InitResponse>() {
                    @Override
                    public void onSuccess(int responseCode, InitResponse serializedResponse) {
                        initResponse = serializedResponse;
                        runDelayedRequests();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {

                    }
                }));
    }

    private void runDelayedRequests() {
        for (DelayedRequest delayedRequest : delayedRequests) {
            delayedRequest.run();
        }
        delayedRequests.clear();
    }

    private static class DelayedRequest<T extends BaseResponse> {
        private Call<T> request;
        private APIRequestCallback<T> requestCallback;

        private DelayedRequest(Call<T> request, APIRequestCallback<T> requestCallback) {
            this.request = request;
            this.requestCallback = requestCallback;
        }

        private void run() {
            request.enqueue(new BaseCallback<>(requestCallback));
        }
    }
}
