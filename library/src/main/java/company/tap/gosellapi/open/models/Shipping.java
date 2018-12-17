package company.tap.gosellapi.open.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public final class Shipping {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("amount")
    @Expose
    private BigDecimal amount;

    public Shipping(String name, @Nullable String description, BigDecimal amount) {

        this.name           = name;
        this.description    = description;
        this.amount         = amount;
    }

    public BigDecimal getAmount() {

        return amount;
    }
}
