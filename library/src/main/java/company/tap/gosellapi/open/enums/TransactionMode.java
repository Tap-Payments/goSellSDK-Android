package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Transaction mode.
 */
public enum TransactionMode {

    /**
     * Purchase transaction mode.
     */
    @SerializedName("PURCHASE")                     PURCHASE,
    /**
     * Authorize capture transaction mode.
     */
    @SerializedName("AUTHORIZE_CAPTURE")            AUTHORIZE_CAPTURE,
    /**
     * Save card transaction mode.
     */
    @SerializedName("SAVE_CARD")                    SAVE_CARD,
    /**
     * Tokenize card mode.
     */
    @SerializedName("TOKENIZE_CARD")                TOKENIZE_CARD,


}
