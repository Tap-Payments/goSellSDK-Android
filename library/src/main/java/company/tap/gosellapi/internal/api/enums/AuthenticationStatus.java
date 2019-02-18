package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationStatus {
    @SerializedName("INITIATED")            INITIATED,
    @SerializedName("AUTHENTICATED")        AUTHENTICATED,
    @SerializedName("NOT_AUTHENTICATED")    NOT_AUTHENTICATED,
    @SerializedName("ABANDONED")            ABANDONED,
    @SerializedName("FAILED")               FAILED
}
