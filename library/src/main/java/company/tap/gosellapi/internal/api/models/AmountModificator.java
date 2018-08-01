package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AmountModificatorType;

public class AmountModificator {

    @SerializedName("type")
    @Expose
    private AmountModificatorType type;

    @SerializedName("value")
    @Expose
    private double value;

    public AmountModificator(AmountModificatorType type, double value) {
        this.type = type;
        this.value = value;
    }

    public AmountModificatorType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public double getNormalizedValue() {

        if (this.type != AmountModificatorType.PERCENTAGE) {

            return this.value;
        }

        return this.value * 0.01f;
    }
}
