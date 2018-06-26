package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Item;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.ShippingCell;
import company.tap.gosellapi.internal.custom_views.GoSellPayLayout;

public class MainActivity extends AppCompatActivity implements GoSellPayLayout.GoSellPaymentInfoRequester {
    PaymentInfo paymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PhoneNumber phoneNumber = new PhoneNumber("380", "93164393");

        paymentInfo = new PaymentInfo("KWD",
                new CustomerInfo("Customer",  "Customerenko" ,"so@me.mail", phoneNumber),
                new ArrayList<Item>() {
                    {
                        add(new Item("itemFirst", 2, "piece", 53.25));
                        add(new Item("itemSecond", 15, "piece", 0.11));
                        add(new Item("itemThird", 7.3, "kg", 1_200.00));
                    }
                },
                new ArrayList<ShippingCell>() {
                    {
                        add(new ShippingCell("firstShip", "descrShip1", 5.0));
                        add(new ShippingCell("secondShip", "descrShip2", 2.3));
                        add(new ShippingCell("thirdShip", "descrShip3", 1.05));
                    }
                });

        GoSellPayLayout payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentInfoRequester(this);
    }

    @Override
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
}
