package company.tap.gosellapi.internal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.api_service.AppInfo;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;

/**
 * The type Utils.
 */
public class Utils {

    /**
     * The type List.
     */
    public static class List {

        /**
         * The interface Filter.
         *
         * @param <T> the type parameter
         */
        public interface Filter<T> {

            /**
             * Is included boolean.
             *
             * @param object the object
             * @return the boolean
             */
            boolean isIncluded(T object);
        }

        /**
         * Filter t.
         *
         * @param <E>    the type parameter
         * @param <T>    the type parameter
         * @param list   the list
         * @param filter the filter
         * @return the t
         */
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

    /**
     * Convert dp to pixel float.
     *
     * @param dp the dp
     * @return the float
     */
    public static float convertDpToPixel(float dp){

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * Sets image tint.
     *
     * @param context    the context
     * @param drawableId the drawable id
     * @param colorId    the color id
     * @return the image tint
     */
    public static Drawable setImageTint(Context context, int drawableId, int colorId) {

        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colorId), PorterDuff.Mode.SRC_IN));
        }

        return drawable;
    }

    /**
     * Show keyboard.
     *
     * @param context       the context
     * @param focusableView the focusable view
     */
    public static void showKeyboard(Context context, View focusableView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(focusableView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Hide keyboard.
     *
     * @param activity the activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Hide keyboard from.
     *
     * @param context the context
     * @param view    the view
     */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Get formatted currency string.
     *
     * @param amountedCurrency the amounted currency
     * @return the string
     */
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

    /**
     * Gets currency.
     *
     * @param currencyCode the currency code
     * @return the currency
     */
    public static Currency getCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Gets currency name.
     *
     * @param currencyCode the currency code
     * @param currency     the currency
     * @return the currency name
     */
    public static String getCurrencyName(String currencyCode, Currency currency,Context context) {
        String name = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && currency != null) {
            name = currency.getDisplayName(context.getResources().getConfiguration().locale);
        }
        return name;
    }

    /**
     * Highlight text.
     *
     * @param context         the context
     * @param sb              the sb
     * @param index           the index
     * @param textToHighlight the text to highlight
     */
//for text
    public static void highlightText(Context context, SpannableStringBuilder sb, int index, String textToHighlight) {
        BackgroundColorSpan fcs = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.vibrant_green));
        sb.setSpan(fcs, index, index + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Highlight text.
     *
     * @param context the context
     * @param sb      the sb
     * @param index   the index
     */
//for single char
    public static void highlightText(Context context, SpannableStringBuilder sb, int index) {
        BackgroundColorSpan fcs = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.vibrant_green));
        sb.setSpan(fcs, index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Highlight phone number.
     *
     * @param context         the context
     * @param sb              the sb
     * @param startIndex      the start index
     * @param textToHighlight the text to highlight
     */
    public static void highlightPhoneNumber(Context context,SpannableStringBuilder sb,int startIndex,String textToHighlight){
        ForegroundColorSpan fcs = new ForegroundColorSpan(ContextCompat.getColor(context,R.color.bt_blue));
        sb.setSpan(fcs,startIndex,startIndex+textToHighlight.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Get window display metrics display metrics.
     *
     * @return the display metrics
     */
    public static DisplayMetrics  getWindowDisplayMetrics(){
        return Resources.getSystem().getDisplayMetrics();
    }
}
