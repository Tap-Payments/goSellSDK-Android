package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum SourcePaymentType {

    @SerializedName("DEBIT_CARD")       DEBIT_CARD,
    @SerializedName("CREDIT_CARD")      CREDIT_CARD,
    @SerializedName("PREPAID_CARD")     PREPAID_CARD,
    @SerializedName("PREPAID_WALLET")   PREPAID_WALLET
}
