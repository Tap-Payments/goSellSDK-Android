package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum ChargeStatus {

    @SerializedName("INITIATED")    INITIATED,
    @SerializedName("IN_PROGRESS")  IN_PROGRESS,
    @SerializedName("ABANDONED")    ABANDONED,
    @SerializedName("CANCELLED")    CANCELLED,
    @SerializedName("FAILED")       FAILED,
    @SerializedName("DECLINED")     DECLINED,
    @SerializedName("RESTRICTED")   RESTRICTED,
    @SerializedName("CAPTURED")     CAPTURED,
    @SerializedName("VOID")         VOID,


    @SerializedName("VALID")        VALID,
    @SerializedName("INVALID")      INVALID,




    @SerializedName("AUTHORIZED")   AUTHORIZED,
}
