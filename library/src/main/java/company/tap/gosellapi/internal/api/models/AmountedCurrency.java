package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

import company.tap.gosellapi.internal.utils.CurrencyFormatter;

/**
 * Created by eugene.goltsev on 26.05.2018.
 */

public final class AmountedCurrency implements Serializable, Comparable<AmountedCurrency> {

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("symbol")
    @Expose
    @NonNull private String symbol;

    @SerializedName("amount")
    @Expose
    @NonNull private BigDecimal amount;

    public String getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {

        this.symbol = symbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AmountedCurrency(String currency, BigDecimal amount) {

        this(currency, amount, CurrencyFormatter.getLocalizedCurrencySymbol(currency));
    }

    public AmountedCurrency(String currency, BigDecimal amount, String symbol) {

        this.currency = currency;
        this.amount = amount;
        this.symbol = symbol;
    }

    @Override
    public int compareTo(@NonNull AmountedCurrency o) {

        String thisDisplayName = CurrencyFormatter.getLocalizedCurrencyName(getCurrency());
        String oDisplayName = CurrencyFormatter.getLocalizedCurrencyName(o.getCurrency());

        return thisDisplayName.compareToIgnoreCase(oDisplayName);
    }
}
