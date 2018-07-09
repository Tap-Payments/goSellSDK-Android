package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationType {
    @SerializedName("OTP") OTP,
    @SerializedName("BIOMETRICS") BIOMETRICS
}
