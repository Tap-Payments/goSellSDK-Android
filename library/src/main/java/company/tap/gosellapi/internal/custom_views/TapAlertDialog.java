package company.tap.gosellapi.internal.custom_views;

import android.app.AlertDialog;
import android.content.Context;

public class TapAlertDialog extends AlertDialog {

    public TapAlertDialog(Context context) {
        super(context);
    }

    public TapAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TapAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
