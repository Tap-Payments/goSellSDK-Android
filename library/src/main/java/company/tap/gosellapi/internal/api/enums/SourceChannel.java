package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Source channel.
 */
public enum SourceChannel {

    /**
     * Call centre source channel.
     */
    @SerializedName("CALL_CENTRE")      CALL_CENTRE,
    /**
     * Internet source channel.
     */
    @SerializedName("INTERNET")         INTERNET,
    /**
     * Mail order source channel.
     */
    @SerializedName("MAIL_ORDER")       MAIL_ORDER,
    /**
     * Moto source channel.
     */
    @SerializedName("MOTO")             MOTO,
    /**
     * Telephone order source channel.
     */
    @SerializedName("TELEPHONE_ORDER")  TELEPHONE_ORDER,
    /**
     * Voice response source channel.
     */
    @SerializedName("VOICE_RESPONSE")   VOICE_RESPONSE
}
