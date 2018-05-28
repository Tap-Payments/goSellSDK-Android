package company.tap.gosellapi.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.api.model.Source;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to create Card
 */

public final class CreateCardRequest {
    @SerializedName("source")
    @Expose
    private Source source;

    /**
     * Public constructor
     * @param source {@link Source} instance.
     */
    public CreateCardRequest(Source source) {
        this.source = source;
    }
}
