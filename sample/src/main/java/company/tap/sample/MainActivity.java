package company.tap.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Customer;
import company.tap.gosellapi.internal.api.models.Item;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.custom_views.GoSellPayButton;

public class MainActivity extends AppCompatActivity implements GoSellPayButton.GoSellPayButtonPaymentInfoRequester{
    PaymentInfo paymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentInfo = new PaymentInfo("USD",
                new Customer("so@me.mail", "+380000000000", "name"),
                new ArrayList<Item>() {
                    {
                        add(new Item("itemFirst", 2, 53.25));
                        add(new Item("itemSecond", 15, 0.11));
                        add(new Item("itemThird", 7.3, 1_200.00));
                    }
                });

        GoSellPayButton payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentInfoRequester(this);
    }

    @Override
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
}
