package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Token type.
 */
public enum TokenType {

    /**
     * Card token type.
     */
    @SerializedName("CARD")         CARD,
    /**
     * Saved card token type.
     */
    @SerializedName("SAVED_CARD")   SAVED_CARD
}
