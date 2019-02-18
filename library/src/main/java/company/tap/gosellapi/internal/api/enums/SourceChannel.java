package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum SourceChannel {

    @SerializedName("CALL_CENTRE")      CALL_CENTRE,
    @SerializedName("INTERNET")         INTERNET,
    @SerializedName("MAIL_ORDER")       MAIL_ORDER,
    @SerializedName("MOTO")             MOTO,
    @SerializedName("TELEPHONE_ORDER")  TELEPHONE_ORDER,
    @SerializedName("VOICE_RESPONSE")   VOICE_RESPONSE
}
