package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AddressFieldInputType {
    @SerializedName("text") TEXT,
    @SerializedName("number") NUMBER,
    @SerializedName("dropdown") DROPDOWN
}
