package company.tap.gosellapi.internal.utils;

import android.app.Activity;
import android.util.TypedValue;

/**
 * The type Color utils.
 */
public class ColorUtils {

    /**
     * Gets color.
     *
     * @param activity      the activity
     * @param attr          the attr
     * @param fallbackColor the fallback color
     * @return the color
     */
    public static int getColor(Activity activity, String attr, int fallbackColor) {
        TypedValue color = new TypedValue();
        try {
            int colorId = activity.getResources().getIdentifier(attr, "attr", activity.getPackageName());
            if (activity.getTheme().resolveAttribute(colorId, color, true)) {
                return color.data;
            }
        } catch (Exception ignored) {}

        return activity.getResources().getColor(fallbackColor);
    }
}
