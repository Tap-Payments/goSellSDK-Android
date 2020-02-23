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
    @SerializedName("minimum_fee")
    @Expose
    private Double minimum_fee;
    @SerializedName("maximum_fee")
    @Expose
    private Double maximum_fee;


    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }
    /**
     * Gets minimum_fee.
     *
     * @return the minimum_fee
     */
    public Double getMinimum_fee() {
        return minimum_fee;
    }
    /**
     * Gets maximum_fee.
     *
     * @return the maximum_fee
     */
    public Double getMaximum_fee() {
        return maximum_fee;
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
