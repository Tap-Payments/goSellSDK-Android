package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationRequirer {
    @SerializedName("PROVIDER") PROVIDER,
    @SerializedName("MERCHANT") MERCHANT,
    @SerializedName("CARDHOLDER") CARDHOLDER,
}
