package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Source request.
 */
public class SourceRequest {

    @SerializedName("id")
    @Expose
    @NonNull private String identifier;

    /**
     * Instantiates a new Source request.
     *
     * @param identifier the identifier
     */
    public SourceRequest(@NonNull String identifier) {

        this.identifier = identifier;
    }

    /**
     * Instantiates a new Source request.
     *
     * @param token the token
     */
    public SourceRequest(@NonNull Token token) {

        this(token.getId());
    }
}
