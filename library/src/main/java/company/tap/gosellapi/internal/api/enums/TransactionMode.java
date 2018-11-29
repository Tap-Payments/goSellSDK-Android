package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum TransactionMode {

    @SerializedName("PURCHASE")             PURCHASE,
    @SerializedName("AUTHORIZE_CAPTURE")    AUTHORIZE_CAPTURE
}
