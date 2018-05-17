package company.tap.gosellapi.internal;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import company.tap.gosellapi.internal.api.api_service.AppInfo;

public class Utils {
    public static Drawable setImageTint(Context context, int drawableId, int colorId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colorId), PorterDuff.Mode.SRC_IN));
        }

        return drawable;
    }

    public static void showKeyboard(Context context, View focusableView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(focusableView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static String getFormattedCurrency(String currencyCode, Number sum){
        Locale locale = new Locale(AppInfo.getLocaleString());
        Currency currency;
        try {
            currency = Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException ex) {
            return "";
        }
        String symbol = getOptionallyHardcodedSymbol(currency.getSymbol(locale));

        NumberFormat currencyFormat = NumberFormat.getIntegerInstance();
        currencyFormat.setMinimumFractionDigits(currency.getDefaultFractionDigits());
        currencyFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());

        return String.format("%s %s", symbol, currencyFormat.format(sum));
    }

    private static String getOptionallyHardcodedSymbol(String symbol) {
        if (symbol.equals("KWD")) {
            return "KD";
        }

        return symbol;
    }

    public static String getCurrencySelectionString(String currencyCode) {
        Currency currency;
        try {
            currency = Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException ex) {
            return currencyCode;
        }

        String symbol = currency.getSymbol();
        String name = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            name = currency.getDisplayName();
        }
        return currencyCode
                + (!symbol.isEmpty() && !symbol.equalsIgnoreCase(currencyCode) ? " " + symbol : "")
                + (!name.isEmpty() ? " (" + name + ")" : "");
    }
}
