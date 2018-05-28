package company.tap.gosellapi.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to create Card
 */

public final class CreateCardRequest {
    @SerializedName("source")
    @Expose
    private String source;

    /**
     * Public constructor
     * @param source identifier.
     */
    public CreateCardRequest(String source) {
        this.source = source;
    }
}
