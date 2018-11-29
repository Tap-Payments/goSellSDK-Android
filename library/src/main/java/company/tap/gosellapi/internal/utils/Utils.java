package company.tap.gosellapi.internal.utils;

import android.arch.core.util.Function;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.api_service.AppInfo;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;

public class Utils {

    public static class List {

        public interface Filter<T> {

            boolean isIncluded(T object);
        }

        public static <E, T extends java.util.List<E>> T filter(T list, Filter<E> filter) {

            T result;

            try {

                Class listClass = list.getClass();
                result = (T) listClass.newInstance();
            }

            catch (IllegalAccessException e) { return list; }
            catch (InstantiationException e) { return list; }

            for ( E element : list ) {

                if ( filter.isIncluded(element) ) {

                    result.add(element);
                }
            }

            return result;
        }
    }

    public static float convertDpToPixel(float dp){

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

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

    public static String getFormattedCurrency(AmountedCurrency amountedCurrency){

        Locale locale = new Locale(AppInfo.getLocaleString());

        Currency currency;
        try {
            currency = Currency.getInstance(amountedCurrency.getCurrency());
        } catch (IllegalArgumentException ex) {
            return "";
        }
        String symbol = getOptionallyHardcodedSymbol(currency.getSymbol(locale));

        NumberFormat currencyFormat = NumberFormat.getIntegerInstance();
        currencyFormat.setMinimumFractionDigits(currency.getDefaultFractionDigits());
        currencyFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());

        return String.format("%s %s", symbol, currencyFormat.format(amountedCurrency.getAmount()));
    }

    private static String getOptionallyHardcodedSymbol(String symbol) {
        if (symbol.equals("KWD")) {
            return "KD";
        }

        return symbol;
    }

    public static Currency getCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static String getCurrencyName(String currencyCode, Currency currency) {
        String name = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && currency != null) {
            name = currency.getDisplayName();
        }
        return name;
    }

    //for text
    public static void highlightText(Context context, SpannableStringBuilder sb, int index, String textToHighlight) {
        BackgroundColorSpan fcs = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.vibrant_green));
        sb.setSpan(fcs, index, index + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    //for single char
    public static void highlightText(Context context, SpannableStringBuilder sb, int index) {
        BackgroundColorSpan fcs = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.vibrant_green));
        sb.setSpan(fcs, index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
