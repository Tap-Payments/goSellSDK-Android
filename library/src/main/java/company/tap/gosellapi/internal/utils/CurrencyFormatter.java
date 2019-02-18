package company.tap.gosellapi.internal.utils;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;

public final class CurrencyFormatter {

    public static String format(AmountedCurrency amountedCurrency) {

        return format(amountedCurrency, true);
    }

    public static String format(AmountedCurrency amountedCurrency, boolean displayCurrency) {

        return format(amountedCurrency, Locale.US, displayCurrency);
    }

    public static String format(AmountedCurrency amountedCurrency, Locale locale, boolean displayCurrency) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        Currency currency = Currency.getInstance(amountedCurrency.getCurrency());
        formatter.setCurrency(currency);

        if ( formatter instanceof DecimalFormat ) {

            DecimalFormat decimalFormatter = (DecimalFormat) formatter;

            DecimalFormatSymbols formatSymbols = decimalFormatter.getDecimalFormatSymbols();
            formatSymbols.setCurrencySymbol(displayCurrency ? amountedCurrency.getSymbol() : "");

            decimalFormatter.setDecimalFormatSymbols(formatSymbols);

            return decimalFormatter.format(amountedCurrency.getAmount()).trim();
        }
        else {

            return formatter.format(amountedCurrency.getAmount());
        }
    }

    @NonNull public static String getLocalizedCurrencySymbol(String currencyCode) {

        return getLocalizedCurrencySymbol(currencyCode, Locale.US);
    }

    @NonNull public static String getLocalizedCurrencySymbol(String currencyCode, Locale locale) {

        Currency currency = Currency.getInstance(currencyCode);
        String symbol = currency.getSymbol(locale);
        symbol = optionallyHardcodedCurrencySymbol(symbol);

        return symbol;
    }

    @NonNull public static String getLocalizedCurrencyName(@NonNull String currencyCode) {

        return getLocalizedCurrencyName(currencyCode, Locale.US);
    }

    @NonNull public static String getLocalizedCurrencyName(@NonNull String currencyCode, @NonNull Locale locale) {

        Currency currency = Currency.getInstance(currencyCode);
        if ( currency == null ) { return currencyCode; }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            return currency.getDisplayName(locale);
        }
        else {

            return currencyCode;
        }
    }

    private static String optionallyHardcodedCurrencySymbol(String currencySymbol) {

        String hardcoded = hardcodedCurrencySymbols.get(currencySymbol);
        if ( hardcoded == null ) {

            return currencySymbol;
        }
        else {

            return hardcoded;
        }
    }

    private static final Map<String, String> hardcodedCurrencySymbols = new HashMap<String, String>(){

        {
            put("KWD", "KD");
            put("د.ك.‏", "د.ك");
        }
    };
}
