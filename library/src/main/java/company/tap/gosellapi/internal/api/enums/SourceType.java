package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Source type.
 */
public enum SourceType {

    /**
     * Card present source type.
     */
    @SerializedName("CARD_PRESENT")     CARD_PRESENT,
    /**
     * Card not present source type.
     */
    @SerializedName("CARD_NOT_PRESENT") CARD_NOT_PRESENT
}
