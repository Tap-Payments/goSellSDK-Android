package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private String id;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
