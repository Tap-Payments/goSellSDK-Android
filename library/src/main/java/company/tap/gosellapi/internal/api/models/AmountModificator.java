package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import company.tap.gosellapi.internal.api.enums.AmountModificatorType;

/**
 * The type Amount modificator.
 */
public class AmountModificator {

    @SerializedName("type")
    @Expose
    private AmountModificatorType type;

    @SerializedName("value")
    @Expose
    private BigDecimal value;

    /**
     * Instantiates a new Amount modificator.
     *
     * @param type  the type
     * @param value the value
     */
    public AmountModificator(AmountModificatorType type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AmountModificatorType getType() {
        return type;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Gets normalized value.
     *
     * @return the normalized value
     */
    public BigDecimal getNormalizedValue() {

        if (this.type != AmountModificatorType.PERCENTAGE) {
            return this.value;
        }
//why
        return this.value.multiply(BigDecimal.valueOf(0.01));
    }
}
