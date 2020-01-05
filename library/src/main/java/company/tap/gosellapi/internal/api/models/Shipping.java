package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type Shipping.
 */
public final class Shipping implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("amount")
    @Expose
    private BigDecimal amount;

    /**
     * Instantiates a new Shipping.
     *
     * @param name        the name
     * @param description the description
     * @param amount      the amount
     */
    public Shipping(String name, @Nullable String description, BigDecimal amount) {

        this.name           = name;
        this.description    = description;
        this.amount         = amount;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {

        return amount;
    }
}
