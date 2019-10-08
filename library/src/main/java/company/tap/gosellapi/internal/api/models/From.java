package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class From {

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("value")
    @Expose
    private BigDecimal value;


    public String getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public From(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }
}
