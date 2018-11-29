package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthorizeActionType {

    @SerializedName("CAPTURE") CAPTURE,
    @SerializedName("VOID") VOID
}
