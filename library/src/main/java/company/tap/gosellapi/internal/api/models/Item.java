package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class Item {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("amount")
    @Expose
    private String amount;

    /**
     * Constructor with all fields
     */
    public Item(String name, String quantity, String amount) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Item {" +
                "\n        name =  '" + name + '\'' +
                "\n        quantity =  '" + quantity + '\'' +
                "\n        amount =  '" + amount + '\'' +
                "\n    }";
    }
}
