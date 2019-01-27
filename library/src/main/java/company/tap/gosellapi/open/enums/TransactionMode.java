package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

public enum TransactionMode {

    @SerializedName("PURCHASE")             PURCHASE,
    @SerializedName("AUTHORIZE_CAPTURE")    AUTHORIZE_CAPTURE,
    @SerializedName("SAVE_CARD")            SAVE_CARD


}
