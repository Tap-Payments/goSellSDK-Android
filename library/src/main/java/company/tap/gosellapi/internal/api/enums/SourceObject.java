package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Source object.
 */
public enum SourceObject {

    /**
     * Card source object.
     */
    @SerializedName("CARD")     CARD,
    /**
     * Token source object.
     */
    @SerializedName("TOKEN")    TOKEN,
    /**
     * Source source object.
     */
    @SerializedName("SOURCE")   SOURCE
}
