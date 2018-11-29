package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthorizeActionStatus {

    @SerializedName("PENDING") PENDING,
    @SerializedName("SCHEDULED") SCHEDULED,
    @SerializedName("CAPTURED") CAPTURED,
    @SerializedName("FAILED") FAILED,
    @SerializedName("DECLINED") DECLINED,
    @SerializedName("VOID") VOID
}
