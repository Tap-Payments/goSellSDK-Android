package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import company.tap.gosellapi.open.buttons.PayButtonView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PayButtonView payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentDataSource(new company.tap.sample.PaymentDataSource());
    }

}
