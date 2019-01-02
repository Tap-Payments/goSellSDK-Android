package company.tap.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.PayButton;
import company.tap.gosellapi.open.delegate.PaymentProcessDelegate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      System.out.println("MainActivity started  "+ getIntent());
        if(getIntent().hasExtra("charge_id"))
        System.out.println("MainActivity >> charge_id :"+getIntent().getStringExtra("charge_id"));
        PayButton payButton = new PayButton();

        payButton.setPaymentDataSource(new company.tap.sample.PaymentDataSource());
        // call delegate interface to get charge or authorize status
        PayButtonView payButtonView = findViewById(R.id.payButtonId);
        payButton.setButtonView(payButtonView,this);

    }

  @Override
  protected void onResume() {
    super.onResume();
     Charge chargeOrAuthorise = PaymentProcessDelegate.getInstance().getPaymentResult();
     if(chargeOrAuthorise!=null){
       showPaymentResult(chargeOrAuthorise);
     }
  }



  private void showPaymentResult(Charge chargeOrAuthorise){
    // show success bar
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int height = displayMetrics.heightPixels;
    int width = displayMetrics.widthPixels;
    PopupWindow popupWindow;
    try {
// We need to get the instance of the LayoutInflater
      LayoutInflater inflater = (LayoutInflater) this
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View layout = inflater.inflate(R.layout.charge_status_layout,
          (ViewGroup) findViewById(R.id.popup_element));


      popupWindow = new PopupWindow(layout, width, 150, true);

      ImageView closeIcon = layout.findViewById(R.id.close_icon);
      ImageView status_icon = layout.findViewById(R.id.status_icon);
      TextView statusText = layout.findViewById(R.id.status_text);
      TextView chargeText = layout.findViewById(R.id.charge_id_txt);

      System.out.println("chargeOrAuthorise.getStatus() : "+chargeOrAuthorise.getStatus());
      String msg = "";
      switch (chargeOrAuthorise.getStatus()){
        case CAPTURED:
        case AUTHORIZED:
          msg = getString(R.string.payment_status_alert_successful);
          status_icon.setImageResource(R.drawable.ic_checkmark_normal);
          break;
        case FAILED:
          msg = getString(R.string.payment_status_alert_failed);
          status_icon.setImageResource(R.drawable.icon_failed);
        break;
        case DECLINED:
          msg = getString(R.string.payment_status_alert_declined);
          status_icon.setImageResource(R.drawable.icon_failed);
          break;
      }
      statusText.setText(msg);
      chargeText.setText(chargeOrAuthorise.getId());

      closeIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          popupWindow.dismiss();
        }
      });
      popupWindow.showAtLocation(layout, Gravity.TOP, 0, 50);
      popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);


    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
