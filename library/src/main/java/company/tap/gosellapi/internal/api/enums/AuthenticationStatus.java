package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authentication status.
 */
public enum AuthenticationStatus {
    /**
     * Initiated authentication status.
     */
    @SerializedName("INITIATED")            INITIATED,
    /**
     * Authenticated authentication status.
     */
    @SerializedName("AUTHENTICATED")        AUTHENTICATED,
    /**
     * Not authenticated authentication status.
     */
    @SerializedName("NOT_AUTHENTICATED")    NOT_AUTHENTICATED,
    /**
     * Abandoned authentication status.
     */
    @SerializedName("ABANDONED")            ABANDONED,
    /**
     * Failed authentication status.
     */
    @SerializedName("FAILED")               FAILED
}
