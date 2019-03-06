package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Response.
 */
public final class Response implements Serializable {
    /**
     * The Code.
     */
    @SerializedName("code")
    @Expose
    String code;

    /**
     * The Message.
     */
    @SerializedName("message")
    @Expose
    String message;

    /**
     * Gets code.
     *
     * @return Response code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return Response message.
     */
    public String getMessage() {
        return message;
    }
}
