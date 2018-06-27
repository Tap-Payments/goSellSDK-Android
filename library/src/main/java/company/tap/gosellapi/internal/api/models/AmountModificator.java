package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AmountModificatorType;

public class AmountModificator {

    @SerializedName("type")
    @Expose
    AmountModificatorType type;

    @SerializedName("value")
    @Expose
    double value;

    public AmountModificatorType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
