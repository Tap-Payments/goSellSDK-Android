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

/////////////////////////////////////  APIs exposer without UI ////////////////////////////
    /**
     * Tokenize card mode no UI.
     */
    @SerializedName("TOKENIZE_CARD_NO_UI")                TOKENIZE_CARD_NO_UI,

    /**
     * Save card transaction mode no UI.
     */
    @SerializedName("SAVE_CARD_NO_UI")                    SAVE_CARD_NO_UI,




}
