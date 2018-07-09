package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("code")
    @Expose
    String code;

    @SerializedName("message")
    @Expose
    String message;

    /**
     * @return Response code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Response message.
     */
    public String getMessage() {
        return message;
    }
}
