package company.tap.gosellapi.internal.custom_views;

import android.app.AlertDialog;
import android.content.Context;

/**
 * The type Tap alert dialog.
 */
public class TapAlertDialog extends AlertDialog {

    /**
     * Instantiates a new Tap alert dialog.
     *
     * @param context the context
     */
    public TapAlertDialog(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Tap alert dialog.
     *
     * @param context        the context
     * @param cancelable     the cancelable
     * @param cancelListener the cancel listener
     */
    public TapAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * Instantiates a new Tap alert dialog.
     *
     * @param context    the context
     * @param themeResId the theme res id
     */
    public TapAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
