package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authentication requirer.
 */
public enum AuthenticationRequirer {

    /**
     * Provider authentication requirer.
     */
    @SerializedName("PROVIDER")     PROVIDER,
    /**
     * Merchant authentication requirer.
     */
    @SerializedName("MERCHANT")     MERCHANT,
    /**
     * Cardholder authentication requirer.
     */
    @SerializedName("CARDHOLDER")   CARDHOLDER,
}
