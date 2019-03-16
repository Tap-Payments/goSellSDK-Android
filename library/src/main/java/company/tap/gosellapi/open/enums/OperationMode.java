package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Enum Operation Mode
 */
public enum OperationMode {

    /**
     * Sandbox is for testing purposes
     */
    @SerializedName("SAND_BOX")              SAND_BOX,

    /**
     *  Production is for live
     */
    @SerializedName("PRODUCTION")            PRODUCTION

}
