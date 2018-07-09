package company.tap.gosellapi.internal.data_managers;

import android.app.ActionBar;
import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import company.tap.gosellapi.R;

public class PaymentResultToastManager {

    private PaymentResultToastManager() {
    }

    private static class SingletonHolder {
        private static final PaymentResultToastManager INSTANCE = new PaymentResultToastManager();
    }

    public static PaymentResultToastManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void showPaymentResult(Context context, String message) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View toastLayout = inflater.inflate(R.layout.gosellapi_toast_success, null);

        TextView statusTextView = toastLayout.findViewById(R.id.statusTextView);
        statusTextView.setText(message);

        final Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }
}
