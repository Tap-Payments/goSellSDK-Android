package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.models.Charge;

public interface CardRequestInterface {
    void onCardRequestSuccess(Charge response);
    void onCardRequestFailure(Charge response);
    void onCardRequestOTP(Charge response);
    void onCardRequestRedirect(Charge response);
}


