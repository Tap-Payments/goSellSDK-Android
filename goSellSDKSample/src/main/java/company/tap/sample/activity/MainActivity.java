package company.tap.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKTrigger;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;
import company.tap.gosellapi.open.delegate.PaymentProcessDelegate;
import company.tap.sample.R;


public class MainActivity extends AppCompatActivity {
    private final int SDK_REQUEST_CODE = 1001;
    private SDKTrigger sdkTrigger;
    private PayButtonView payButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPayButton();
    }


    private void setupPayButton() {
        if (sdkTrigger == null)
            sdkTrigger = new SDKTrigger();

        payButtonView = findViewById(R.id.payButtonId);

        sdkTrigger.setButtonView(payButtonView, this, SDK_REQUEST_CODE);

        sdkTrigger.generatePaymentDataSource();
        //sdkTrigger.start();   //  start without PayButton

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SDK_REQUEST_CODE:
                if (PaymentProcessDelegate.getInstance().getPaymentResult() != null)
                    showDialog();
                break;
        }
    }

    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    private void showDialog() {
        // show success bar
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        PopupWindow popupWindow;
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {

                View layout = inflater.inflate(company.tap.gosellapi.R.layout.charge_status_layout, findViewById(
                        company.tap.gosellapi.R.id.popup_element));

                popupWindow = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                ImageView status_icon = layout.findViewById(company.tap.gosellapi.R.id.status_icon);
                TextView statusText = layout.findViewById(company.tap.gosellapi.R.id.status_text);
                TextView chargeText = layout.findViewById(company.tap.gosellapi.R.id.charge_id_txt);

                String msg = "";
                Charge chargeOrAuthorizeResult = PaymentProcessDelegate.getInstance().getPaymentResult();
                if (chargeOrAuthorizeResult != null) {
                    System.out.println("chargeOrAuthorise.getStatus() : " + chargeOrAuthorizeResult.getStatus());

                    switch (chargeOrAuthorizeResult.getStatus()) {
                        case CAPTURED:
                        case AUTHORIZED:
                        case VALID:
                            msg = getString(company.tap.gosellapi.R.string.payment_status_alert_successful);
                            status_icon.setImageResource(company.tap.gosellapi.R.drawable.ic_checkmark_normal);
                            break;
                        case FAILED:
                            msg = getString(company.tap.gosellapi.R.string.payment_status_alert_failed);
                            status_icon.setImageResource(company.tap.gosellapi.R.drawable.icon_failed);
                            break;
                        case INVALID:
                            msg = getString(company.tap.gosellapi.R.string.payment_status_alert_invalid);
                            status_icon.setImageResource(company.tap.gosellapi.R.drawable.icon_failed);
                            break;
                        case DECLINED:
                            msg = getString(company.tap.gosellapi.R.string.payment_status_alert_declined);
                            status_icon.setImageResource(company.tap.gosellapi.R.drawable.icon_failed);
                            break;
                    }
                    chargeText.setText(chargeOrAuthorizeResult.getId());
                } else {
                    msg = getString(company.tap.gosellapi.R.string.payment_status_alert_failed);
                    status_icon.setImageResource(company.tap.gosellapi.R.drawable.icon_failed);
                    chargeText.setText("");
                }

                statusText.setText(msg);


                LinearLayout close_icon_ll = layout.findViewById(company.tap.gosellapi.R.id.close_icon_ll);
                close_icon_ll.setOnClickListener(v -> {
                });

                popupWindow.showAtLocation(layout, Gravity.TOP, 0, 50);
                popupWindow.getContentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_show));

                setupTimer(popupWindow);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupTimer(PopupWindow popupWindow) {
        // Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        };

        popupWindow.setOnDismissListener(() -> handler.removeCallbacks(runnable));

        handler.postDelayed(runnable, 4000);
    }

}
