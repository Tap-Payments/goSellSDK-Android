package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Tax {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("amount")
    @Expose
    private AmountModificator amount;

    public Tax(String name, @Nullable String description, AmountModificator amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    public AmountModificator getAmount() {
        return amount;
    }
}
