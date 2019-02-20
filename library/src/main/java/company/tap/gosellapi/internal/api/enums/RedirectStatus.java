package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Redirect status.
 */
public enum RedirectStatus {

    /**
     * Pending redirect status.
     */
    @SerializedName("PENDING")  PENDING,
    /**
     * Success redirect status.
     */
    @SerializedName("SUCCESS")  SUCCESS,
    /**
     * Failed redirect status.
     */
    @SerializedName("FAILED")   FAILED
}
