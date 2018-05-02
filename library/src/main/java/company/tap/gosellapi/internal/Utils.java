package company.tap.gosellapi.internal;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
}
