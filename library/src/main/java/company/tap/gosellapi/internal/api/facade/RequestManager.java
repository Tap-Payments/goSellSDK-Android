package company.tap.gosellapi.internal.api.facade;

import android.support.annotation.RestrictTo;

import java.io.IOException;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.api_service.APIService;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.BaseCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.responses.BaseResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;

@RestrictTo(RestrictTo.Scope.LIBRARY)
class RequestManager {
    private APIService apiHelper;

    //all requests are wrapped in DelayedRequest, until init() would be finished
    private boolean initIsRunning;
    private ArrayList<DelayedRequest> delayedRequests;

    RequestManager(APIService apiHelper) {
        this.apiHelper = apiHelper;
        delayedRequests = new ArrayList<>();
    }

    void request(DelayedRequest delayedRequest) {
        delayedRequests.add(delayedRequest);
        if (PaymentDataManager.getInstance().getSDKSettings() == null) {
            if (!initIsRunning) {
                init();
            }
        } else {
            runDelayedRequests();
        }
    }

  /**
   * Retrieve SDKSettings from server.
   */
  private void init() {
        initIsRunning = true;
        apiHelper
                .init()
                .enqueue(new BaseCallback<>(new APIRequestCallback<SDKSettings>() {
                    @Override
                    public void onSuccess(int responseCode, SDKSettings serializedResponse) {
                        initIsRunning = false;
                        PaymentDataManager.getInstance().setSDKSettings(serializedResponse);
                        runDelayedRequests();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        initIsRunning = false;
                        failDelayedRequests(errorDetails);
                    }
                }));
    }


    private void runDelayedRequests() {
        for (DelayedRequest delayedRequest : delayedRequests) {
          System.out.println("delayedRequest.toString() : " + delayedRequest.getRequest().request());
          try {
            final Buffer buffer = new Buffer();
            if(delayedRequest.getRequest().request().body()!=null ) {
              delayedRequest.getRequest().request().body().writeTo(buffer);
              System.out.println("delayedRequest.toString() :" + buffer.readUtf8().toString());
            }
          }catch (IOException s){
            System.out.println("ex : " + s.getLocalizedMessage());
          }
          delayedRequest.run();
        }
        delayedRequests.clear();
    }


    private void failDelayedRequests(GoSellError errorDetails) {
        for (DelayedRequest delayedRequest : delayedRequests) {
            delayedRequest.fail(errorDetails);
        }
        delayedRequests.clear();
    }

    static class DelayedRequest<T extends BaseResponse> {
        private Call<T> request;
        private APIRequestCallback<T> requestCallback;

        DelayedRequest(Call<T> request, APIRequestCallback<T> requestCallback) {
            this.request = request;
            this.requestCallback = requestCallback;
        }

      private String bodyToString(final RequestBody request){
        try {
          final RequestBody copy = request;
          final Buffer buffer = new Buffer();
          if(copy != null)
            copy.writeTo(buffer);
          else
            return "body null";
          return buffer.readUtf8();
        }
        catch (final IOException e) {
          return "did not work";
        }

      }


        void run() {
            request.enqueue(new BaseCallback<>(requestCallback));
        }

        void fail(GoSellError errorDetails) {
            requestCallback.onFailure(errorDetails);
        }

        Call  getRequest(){
          return this.request;
        }
    }


}
