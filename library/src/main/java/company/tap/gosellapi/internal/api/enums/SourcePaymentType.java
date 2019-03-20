package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Source payment type.
 */
public enum SourcePaymentType {

    /**
     * Debit card source payment type.
     */
    @SerializedName("DEBIT_CARD")       DEBIT_CARD,
    /**
     * Credit card source payment type.
     */
    @SerializedName("CREDIT_CARD")      CREDIT_CARD,
    /**
     * Prepaid card source payment type.
     */
    @SerializedName("PREPAID_CARD")     PREPAID_CARD,
    /**
     * Prepaid wallet source payment type.
     */
    @SerializedName("PREPAID_WALLET")   PREPAID_WALLET
}
