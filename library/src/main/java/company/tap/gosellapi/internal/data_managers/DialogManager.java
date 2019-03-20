package company.tap.gosellapi.internal.data_managers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.activities.BaseActivity;

/**
 * The type Dialog manager.
 */
class DialogManager {

    /**
     * The interface Dialog result.
     */
    interface DialogResult {

        /**
         * Dialog closed.
         *
         * @param positiveButtonClicked the positive button clicked
         */
        void dialogClosed(boolean positiveButtonClicked);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DialogManager getInstance() {

        return DialogManager.SingletonHolder.INSTANCE;
    }

    /**
     * Show dialog.
     *
     * @param title               the title
     * @param message             the message
     * @param positiveButtonTitle the positive button title
     * @param negativeButtonTitle the negative button title
     * @param callback            the callback
     */
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
