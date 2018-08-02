package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Order {

    @SerializedName("id")
    @Expose
    @Nullable private String id;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
