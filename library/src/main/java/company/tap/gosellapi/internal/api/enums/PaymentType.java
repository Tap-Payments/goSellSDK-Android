package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum PaymentType {

    @SerializedName("card") CARD,
    @SerializedName("web")  WEB
}
