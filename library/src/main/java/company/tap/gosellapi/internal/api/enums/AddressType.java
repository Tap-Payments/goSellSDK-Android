package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Address type.
 */
public enum AddressType {

    /**
     * Residential address type.
     */
    @SerializedName("RESIDENTIAL")  RESIDENTIAL,
    /**
     * Commercial address type.
     */
    @SerializedName("COMMERCIAL")   COMMERCIAL
}
