package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import company.tap.gosellapi.internal.api.enums.AmountModificatorType;

public class AmountModificator {

    @SerializedName("type")
    @Expose
    private AmountModificatorType type;

    @SerializedName("value")
    @Expose
    private BigDecimal value;

    public AmountModificator(AmountModificatorType type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    public AmountModificatorType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getNormalizedValue() {

        if (this.type != AmountModificatorType.PERCENTAGE) {
            return this.value;
        }
//why
        return this.value.multiply(BigDecimal.valueOf(0.01));
    }
}
