package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authentication type.
 */
public enum AuthenticationType {

    /**
     * Otp authentication type.
     */
    @SerializedName("OTP")          OTP,
    /**
     * Biometrics authentication type.
     */
    @SerializedName("BIOMETRICS")   BIOMETRICS
}
