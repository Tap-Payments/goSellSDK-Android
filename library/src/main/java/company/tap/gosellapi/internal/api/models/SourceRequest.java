package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceRequest {

    @SerializedName("id")
    @Expose
    @NonNull private String identifier;

    public SourceRequest(@NonNull String identifier) {

        this.identifier = identifier;
    }

    public SourceRequest(@NonNull Token token) {

        this(token.getId());
    }
}
