package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authorize action type.
 */
public enum AuthorizeActionType {

    /**
     * Capture authorize action type.
     */
    @SerializedName("CAPTURE") CAPTURE,
    /**
     * Void authorize action type.
     */
    @SerializedName("VOID") VOID
}
