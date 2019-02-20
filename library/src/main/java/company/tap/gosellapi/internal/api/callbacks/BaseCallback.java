package company.tap.gosellapi.internal.api.callbacks;

import android.support.annotation.NonNull;

import java.io.IOException;

import company.tap.gosellapi.internal.api.responses.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Base callback with response handling
 *
 * @param <K> the type parameter
 */
public final class BaseCallback<K extends BaseResponse> implements Callback<K> {
    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";
    private APIRequestCallback<K> requestCallback;

    /**
     * Instantiates a new Base callback.
     *
     * @param requestCallback the request callback
     */
    public BaseCallback(APIRequestCallback<K> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onResponse(@NonNull Call<K> call, @NonNull Response<K> response) {
        if (response.isSuccessful()) {
            requestCallback.onSuccess(response.code(), response.body());
        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    requestCallback.onFailure(new GoSellError(response.code(), errorBody.string(), null));
                } catch (IOException e) {
                    requestCallback.onFailure(new GoSellError(GoSellError.ERROR_CODE_UNAVAILABLE, null, e));
                }
            } else {
                requestCallback.onFailure(new GoSellError(response.code(), UNABLE_TO_FETCH_ERROR_INFO, null));
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<K> call, @NonNull Throwable t) {
        requestCallback.onFailure(new GoSellError(GoSellError.ERROR_CODE_UNAVAILABLE, null, t));
    }
}
