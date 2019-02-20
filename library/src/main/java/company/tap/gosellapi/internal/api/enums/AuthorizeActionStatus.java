package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authorize action status.
 */
public enum AuthorizeActionStatus {

    /**
     * Pending authorize action status.
     */
    @SerializedName("PENDING") PENDING,
    /**
     * Scheduled authorize action status.
     */
    @SerializedName("SCHEDULED") SCHEDULED,
    /**
     * Captured authorize action status.
     */
    @SerializedName("CAPTURED") CAPTURED,
    /**
     * Failed authorize action status.
     */
    @SerializedName("FAILED") FAILED,
    /**
     * Declined authorize action status.
     */
    @SerializedName("DECLINED") DECLINED,
    /**
     * Void authorize action status.
     */
    @SerializedName("VOID") VOID
}
