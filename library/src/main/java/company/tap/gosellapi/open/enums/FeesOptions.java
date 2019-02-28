package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Fees Options selected by merchant
 */
public enum FeesOptions {
    /**
     *  Calculate Fees and add it to the total amount
     */
    @SerializedName("FEES_EXTRA") FEES_EXTRA,

    /**
     * Fees already included in total amount
     */
    @SerializedName("FEES_INCLUDED") FEES_INCLUDED
}
