package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.PayButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().hasExtra("charge_id"))
        System.out.println("MainActivity >> charge_id :"+getIntent().getStringExtra("charge_id"));
        PayButton payButton = new PayButton();

        payButton.setPaymentDataSource(new company.tap.sample.PaymentDataSource());
        // delgate
        PayButtonView payButtonView = findViewById(R.id.payButtonId);
        payButton.setButtonView(payButtonView,this);

    }

}
