package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eugene.goltsev on 26.05.2018.
 */

public final class AmountedCurrency implements Serializable{
    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("amount")
    @Expose
    private double amount;

    public String getCurrency_code() {
        return currency_code;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getAmount() {
        return amount;
    }
}
