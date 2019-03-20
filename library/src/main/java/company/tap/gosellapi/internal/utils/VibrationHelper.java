package company.tap.gosellapi.internal.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Vibrator;

/**
 * The type Vibration helper.
 */
public class VibrationHelper {

    /**
     * Vibrate.
     *
     * @param context  the context
     * @param duration the duration
     */
    public static void vibrate(Context context, int duration) {
        if (hasVibrationPermission(context)) {
            ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(duration);
        }
    }

    /**
     * Has vibration permission boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean hasVibrationPermission(Context context) {
        return (context.getPackageManager().checkPermission(Manifest.permission.VIBRATE,
                context.getPackageName()) == PackageManager.PERMISSION_GRANTED);
    }
}
