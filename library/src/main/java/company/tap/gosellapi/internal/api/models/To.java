package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class To {

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("value")
    @Expose
    public BigDecimal value;

    @SerializedName("quote")
    @Expose
    public String quote;

    @SerializedName("rate")
    @Expose
    public String rate;

    @SerializedName("markup")
    @Expose
    public Markup markup;

    public To(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getQuote() {
        return quote;
    }

    public String getRate() {
        return rate;
    }

    public Markup getMarkup() {
        return markup;
    }
}
