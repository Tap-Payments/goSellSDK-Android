package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Otp status.
 */
public enum OTPStatus {

    /**
     * Success otp status.
     */
    @SerializedName("SUCCESS")          SUCCESS,
    /**
     * Limit exceeded otp status.
     */
    @SerializedName("LIMIT_EXCEEDED")   LIMIT_EXCEEDED,
    /**
     * Failed otp status.
     */
    @SerializedName("FAILED")           FAILED
}
