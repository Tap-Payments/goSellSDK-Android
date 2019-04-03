package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Merchant implements Serializable {
    @SerializedName("id")
    @Expose
    @Nullable private String id;

    /**
     * constructor
     * @param id
     */
    public Merchant(@Nullable String id){
        this.id = id;
    }

    /**
     * get Merchant ID
     * @return id
     */
    @Nullable
    public String getId() {
        return id;
    }
}
