package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Url status.
 */
public enum URLStatus {

    /**
     * Pending url status.
     */
    @SerializedName("PENDING") PENDING,
    /**
     * Success url status.
     */
    @SerializedName("SUCCEES") SUCCESS,
    /**
     * Failed url status.
     */
    @SerializedName("FAILED") FAILED
}
