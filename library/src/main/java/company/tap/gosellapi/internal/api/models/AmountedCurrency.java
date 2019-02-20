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

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {

        this.symbol = symbol;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Instantiates a new Amounted currency.
     *
     * @param currency the currency
     * @param amount   the amount
     */
    public AmountedCurrency(String currency, BigDecimal amount) {

        this(currency, amount, CurrencyFormatter.getLocalizedCurrencySymbol(currency));
    }

    /**
     * Instantiates a new Amounted currency.
     *
     * @param currency the currency
     * @param amount   the amount
     * @param symbol   the symbol
     */
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
