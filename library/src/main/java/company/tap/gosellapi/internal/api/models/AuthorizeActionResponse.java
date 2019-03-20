package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthorizeActionStatus;

/**
 * The type Authorize action response.
 */
public final class AuthorizeActionResponse {

    @SerializedName("status")
    @Expose
    @NonNull private AuthorizeActionStatus status;

    /**
     * Gets status.
     *
     * @return the status
     */
    @NonNull
    public AuthorizeActionStatus getStatus() {
        return status;
    }
}
