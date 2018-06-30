package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum OTPVerificationStatus {
    @SerializedName("SUCCESS") SUCCESS,
    @SerializedName("LIMIT_EXCEEDED") LIMIT_EXCEEDED,
    @SerializedName("INVALID") INVALID
}
