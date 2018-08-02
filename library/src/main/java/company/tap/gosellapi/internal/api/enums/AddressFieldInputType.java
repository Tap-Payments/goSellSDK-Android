package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum AddressFieldInputType {

    @SerializedName("TEXT")     TEXT,
    @SerializedName("NUMBER")   NUMBER,
    @SerializedName("DROPDOWN") DROPDOWN
}
