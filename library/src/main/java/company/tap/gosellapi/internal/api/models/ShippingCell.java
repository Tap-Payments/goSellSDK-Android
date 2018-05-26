package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingCell {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("amount")
    @Expose
    private double amount;

    public ShippingCell(String name, String description, double amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
}
