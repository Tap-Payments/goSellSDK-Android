package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum RedirectStatus {

    @SerializedName("PENDING")  PENDING,
    @SerializedName("SUCCESS")  SUCCESS,
    @SerializedName("FAILED")   FAILED
}
