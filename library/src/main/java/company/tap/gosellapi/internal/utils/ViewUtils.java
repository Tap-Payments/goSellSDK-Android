package company.tap.gosellapi.internal.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import company.tap.gosellapi.R;


/**
 * The type View utils.
 */
public class ViewUtils {

    /**
     * Dp 2 px int.
     *
     * @param context the context
     * @param dp      the dp
     * @return the int
     */
    public static int dp2px(Context context, float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics()));
    }

    /**
     * Is dark background boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public static boolean isDarkBackground(Activity activity) {
        int color = activity.getResources().getColor(R.color.bt_white);
        try {
            Drawable background = activity.getWindow().getDecorView().getRootView().getBackground();
            if (background instanceof ColorDrawable) {
                color = ((ColorDrawable) background).getColor();
            }
        } catch (Exception ignored) {}

        double luminance = (0.2126 * Color.red(color)) + (0.7152 * Color.green(color)) +
                (0.0722 * Color.blue(color));

        return luminance < 128;
    }
}
