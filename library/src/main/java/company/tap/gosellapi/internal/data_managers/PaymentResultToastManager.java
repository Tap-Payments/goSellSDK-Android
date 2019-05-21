package company.tap.gosellapi.internal.data_managers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.ChargeStatus;

/**
 * The type Payment result toast manager.
 */
public class PaymentResultToastManager {

    private PaymentResultToastManager() {
    }

    private static class SingletonHolder {
        private static final PaymentResultToastManager INSTANCE = new PaymentResultToastManager();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PaymentResultToastManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Show payment result.
     *
     * @param context the context
     * @param message the message
     */
    public void showPaymentResult(Context context, ChargeStatus message) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View toastLayout = inflater.inflate(R.layout.gosellapi_toast_success, null);

        TextView statusTextView = toastLayout.findViewById(R.id.statusTextView);
        statusTextView.setText(message.toString());

        final Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }
}
