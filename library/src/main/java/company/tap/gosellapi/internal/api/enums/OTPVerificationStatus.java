package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Otp verification status.
 */
public enum OTPVerificationStatus {

    /**
     * Success otp verification status.
     */
    @SerializedName("SUCCESS")          SUCCESS,
    /**
     * Limit exceeded otp verification status.
     */
    @SerializedName("LIMIT_EXCEEDED")   LIMIT_EXCEEDED,
    /**
     * Invalid otp verification status.
     */
    @SerializedName("INVALID")          INVALID
}
