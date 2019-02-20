package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Address field input type.
 */
public enum AddressFieldInputType {

    /**
     * Text address field input type.
     */
    @SerializedName("TEXT")     TEXT,
    /**
     * Number address field input type.
     */
    @SerializedName("NUMBER")   NUMBER,
    /**
     * Dropdown address field input type.
     */
    @SerializedName("DROPDOWN") DROPDOWN
}
