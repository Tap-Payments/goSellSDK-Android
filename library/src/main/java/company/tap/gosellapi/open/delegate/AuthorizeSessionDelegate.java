package company.tap.gosellapi.open.delegate;

import android.support.annotation.NonNull;

import company.tap.gosellapi.internal.api.models.Authorize;

public interface AuthorizeSessionDelegate {

    void authorizationSucceed(@NonNull Authorize authorize);
    void authorizationFailed(Authorize authorize);
}
