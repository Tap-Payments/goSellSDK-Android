package company.tap.gosellapi.internal.data_managers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.activities.BaseActivity;

class DialogManager {

    interface DialogResult {

        void dialogClosed(boolean positiveButtonClicked);
    }

    public static DialogManager getInstance() {

        return DialogManager.SingletonHolder.INSTANCE;
    }

    void showDialog(@NonNull String title, @NonNull String message, @NonNull String positiveButtonTitle, @Nullable String negativeButtonTitle, final @Nullable DialogResult callback) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BaseActivity.getCurrent());
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                callCallback(callback, true);
            }
        });

        if ( negativeButtonTitle != null ) {

            dialogBuilder.setNegativeButton(negativeButtonTitle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    callCallback(callback, false);
                }
            });
        }
        dialogBuilder.show();
    }

    private void callCallback(@Nullable DialogResult callback, boolean positiveButtonClicked) {

        if ( callback == null ) { return; }

        callback.dialogClosed(positiveButtonClicked);
    }

    private static class SingletonHolder {

        private static final DialogManager INSTANCE = new DialogManager();
    }

    private DialogManager() {}
}
