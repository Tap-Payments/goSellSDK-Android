package company.tap.gosellapi.internal.api.models;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Source object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class Source {
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * Constructor with id field only (token id, card id etc.)
     */
    public Source(String id) {
        this.id = id;
    }

    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Source {" +
                "\n        id =  '" + id + '\'' +
                "\n    }";
    }
}
