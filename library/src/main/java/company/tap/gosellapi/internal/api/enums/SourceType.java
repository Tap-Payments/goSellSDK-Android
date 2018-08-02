package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum SourceType {

    @SerializedName("CARD_PRESENT")     CARD_PRESENT,
    @SerializedName("CARD_NOT_PRESENT") CARD_NOT_PRESENT
}
