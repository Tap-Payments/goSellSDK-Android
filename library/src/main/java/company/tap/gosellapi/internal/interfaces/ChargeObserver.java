package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.models.Charge;

public interface ChargeObserver {
    void otpScreenNeedToShown();
    void webScreenNeedToShown();
    void responseSucceed();
    void responseFailed();
    void dataAcquired(Charge response);
}
