package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Shipping {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("amount")
    @Expose
    private double amount;

    public Shipping(String name, @Nullable String description, double amount) {

        this.name           = name;
        this.description    = description;
        this.amount         = amount;
    }

    public double getAmount() {

        return amount;
    }
}
