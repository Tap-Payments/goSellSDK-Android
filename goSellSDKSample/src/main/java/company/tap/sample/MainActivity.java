package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Item;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.custom_views.GoSellPayLayout;

public class MainActivity extends AppCompatActivity implements GoSellPayLayout.GoSellPaymentInfoRequester {
    PaymentInfo paymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fill test request data
        ArrayList<Item> items =  new ArrayList<Item>() {
            {
                add(new Item("itemFirst", 2, "piece", 53.25));
                add(new Item("itemSecond", 15, "piece", 0.11));
                add(new Item("itemThird", 7.3, "kg", 1_200.00));
            }
        };

        paymentInfo = new PaymentInfo(items, "KWD", 220);

        GoSellPayLayout payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentInfoRequester(this);
    }

    @Override
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
}
