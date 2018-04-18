package company.tap.gosellapi.internal.api.api_service;

import company.tap.gosellapi.internal.api.responses.BaseResponse;

/**
 * Base callback to process API responses
 * @param <T> {@link Class} implementing {@link BaseResponse} interface
 */
interface APIRequestCallback<T extends BaseResponse> {
    /**
     * Success callback. Request is considered as successful when response code is between 200 and 299
     * @param responseCode Response code, from 200 to 299
     * @param serializedResponse Serialized response of {@link T} type or null in case when response could not be serialized into {@link T} type
     */
    void onSuccess(int responseCode, T serializedResponse);

    /**
     * General failure callback
     * @param errorDetails {@link GoSellError} representing a failure reason
     */
    void onFailure(GoSellError errorDetails);
}