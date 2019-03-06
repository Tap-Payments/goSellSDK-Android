package company.tap.gosellapi.open.delegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;

public interface SessionDelegate {

        void paymentSucceed(@NonNull Charge charge);
        void paymentFailed(@Nullable Charge charge);

        void authorizationSucceed(@NonNull Authorize authorize);
        void authorizationFailed(Authorize authorize);

        void cardSaved(@NonNull Charge charge);
        void cardSavingFailed(@NonNull Charge charge);


        void sdkError(@Nullable GoSellError goSellError);

        void sessionIsStarting();
        void sessionHasStarted();
        void sessionCancelled();
}
