package company.tap.gosellapi.internal.api.facade;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.api_service.APIService;
import company.tap.gosellapi.internal.api.api_service.RetrofitHelper;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GoSellAPI {
    private APIService apiHelper;

    //init and pending requests
    private RequestManager requestManager;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager(apiHelper);
    }

    private static class SingletonHolder {
        private static final GoSellAPI INSTANCE = new GoSellAPI();
    }

    public static GoSellAPI getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //requests
    public void createCharge(final CreateChargeRequest createChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createCharge(createChargeRequest), requestCallback));
    }

    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveCharge(chargeId), requestCallback));
    }

    public void updateCharge(final String chargeId, final UpdateChargeRequest updateChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateCharge(chargeId, updateChargeRequest), requestCallback));
    }

    public void getPaymentTypes(PaymentInfo paymentInfo, final APIRequestCallback<PaymentOptionsResponse> requestCallback) {
        GlobalDataManager.getInstance().setPaymentInfo(paymentInfo);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getPaymentTypes(paymentInfo), new APIRequestCallback<PaymentOptionsResponse>() {
            @Override
            public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {
                GlobalDataManager.getInstance().createPaymentOptionsDataManager(serializedResponse);
                requestCallback.onSuccess(responseCode, serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                requestCallback.onFailure(errorDetails);
            }
        }));
    }
}
