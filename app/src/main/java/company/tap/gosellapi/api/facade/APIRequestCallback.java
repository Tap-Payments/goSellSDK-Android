package company.tap.gosellapi.api.facade;

import company.tap.gosellapi.api.responses.BaseResponse;

/**
 * Base callback to process API responses
 * @param <T> {@link Class} implementing {@link BaseResponse} interface
 */
public interface APIRequestCallback<T extends BaseResponse> {
    /**
     * Success callback. Request is considered as successful when response code is between 200 and 299
     * @param responseCode Response code, from 200 to 299
     * @param serializedResponse Serialized response of {@link T} type or null in case when response could not be serialized into {@link T} type
     */
    void onSuccess(int responseCode, T serializedResponse);

    /**
     * General failure callback (connection errors)
     * @param t {@link Throwable} representing a failure reason
     */
    void onFailure(Throwable t);

    /**
     * Failure callback returned by server side
     * @param responseCode Failure response code
     * @param errorBody Raw json response from server side, if present
     */
    void onFailure(int responseCode, String errorBody);
}