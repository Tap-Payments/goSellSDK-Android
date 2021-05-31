package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Card type.
 */
public enum CardType {

    /**
     * Credit card type.
     */
    @SerializedName("CREDIT")  CREDIT,
    /**
     * Debit card type.
     */
    @SerializedName("DEBIT")  DEBIT,
    /**
     * All card type.
     */
    @SerializedName("ALL")  ALL



}
