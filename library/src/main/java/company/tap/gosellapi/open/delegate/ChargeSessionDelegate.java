package company.tap.gosellapi.open.delegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.api.models.Charge;

public interface ChargeSessionDelegate {
    void paymentSucceed(@NonNull Charge charge);
    void paymentFailed(@Nullable Charge charge);
}
