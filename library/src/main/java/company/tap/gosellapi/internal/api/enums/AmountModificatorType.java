package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Amount modificator type.
 */
public enum AmountModificatorType {

    /**
     * Percentage amount modificator type.
     */
    @SerializedName("P") PERCENTAGE,
    /**
     * Fixed amount modificator type.
     */
    @SerializedName("F") FIXED
}
