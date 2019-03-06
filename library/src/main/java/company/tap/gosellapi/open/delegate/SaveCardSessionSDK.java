package company.tap.gosellapi.open.delegate;

import android.support.annotation.NonNull;

import company.tap.gosellapi.internal.api.models.Charge;

public interface SaveCardSessionSDK {
    void cardSaved(@NonNull Charge charge);
    void cardSavingFailed(@NonNull Charge charge);
}
