package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.models.Charge;

/**
 * The interface Charge status interface.
 */
public interface ChargeStatusInterface {
    /**
     * On card request success.
     *
     * @param response the response
     */
    void onCardRequestSuccess(Charge response);

    /**
     * On card request failure.
     *
     * @param response the response
     */
    void onCardRequestFailure(Charge response);

    /**
     * On card request otp.
     *
     * @param response the response
     */
    void onCardRequestOTP(Charge response);

    /**
     * On card request redirect.
     *
     * @param response the response
     */
    void onCardRequestRedirect(Charge response);
}


