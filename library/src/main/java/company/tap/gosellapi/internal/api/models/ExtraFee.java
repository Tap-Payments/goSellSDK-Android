package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import company.tap.gosellapi.internal.api.enums.AmountModificatorType;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */
public final class ExtraFee extends AmountModificator {

    @SerializedName("currency")
    @Expose
    private String currency;

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Instantiates a new Extra fee.
     *
     * @param type     the type
     * @param value    the value
     * @param currency the currency
     */
    public ExtraFee(AmountModificatorType type, BigDecimal value, String currency) {
        super(type, value);
        this.currency = currency;
    }
}
