package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Payment type.
 */
public enum PaymentType {

    /**
     * Card payment type.
     */
    @SerializedName("card") CARD,
    /**
     * Web payment type.
     */
    @SerializedName("web")  WEB,
    /**
     * Saved card payment type.
     */
    @SerializedName("savedCard")  SavedCard
}
