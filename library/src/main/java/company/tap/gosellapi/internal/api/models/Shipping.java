package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("amount")
    @Expose
    private double amount;

    public Shipping(String name, String description, double amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
