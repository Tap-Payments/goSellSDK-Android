package company.tap.gosellapi.api.facade;

import android.support.annotation.NonNull;

import java.io.IOException;

import company.tap.gosellapi.api.responses.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Base callback with response handling
 */

final class BaseCallback<K extends BaseResponse> implements Callback<K> {
    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";
    private APIRequestCallback<K> requestCallback;

    @Override
    public void onResponse(@NonNull Call<K> call, @NonNull Response<K> response) {
        if (response.isSuccessful()) {
            requestCallback.onSuccess(response.code(), response.body());
        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    requestCallback.onFailure(response.code(), errorBody.string());
                } catch (IOException e) {
                    requestCallback.onFailure(e);
                }
            } else {
                requestCallback.onFailure(response.code(), UNABLE_TO_FETCH_ERROR_INFO);
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<K> call, @NonNull Throwable t) {
        requestCallback.onFailure(t);
    }

    BaseCallback(APIRequestCallback<K> requestCallback) {
        this.requestCallback = requestCallback;
    }
}
