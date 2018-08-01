package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.Shipping;
import company.tap.gosellapi.internal.api.models.Tax;
import company.tap.gosellapi.internal.custom_views.GoSellPayButtonLayout;
import company.tap.gosellapi.internal.interfaces.GoSellPaymentDataSource;

public class MainActivity extends AppCompatActivity implements GoSellPaymentDataSource {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currency = "KWD";
        customerInfo = new CustomerInfo(null, "Name", "Middlename", "Surname", "hello@tap.company", new PhoneNumber("965", "00000000"));

        GoSellPayButtonLayout payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentDataSource(this);
    }

    private String currency;
    private CustomerInfo customerInfo;
    private ArrayList<PaymentItem> items;
    private ArrayList<Tax> taxes;
    private ArrayList<Shipping> shipping;
    private String postURL;
    private String paymentDescription;
    private HashMap<String, String> paymentMetadata;
    private Reference paymentReference;
    private String paymentStatementDescriptor;
    private boolean requires3DSecure;
    private Receipt receiptSettings;

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    @Override
    public ArrayList<PaymentItem> getItems() {
        return items;
    }

    @Override
    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    @Override
    public ArrayList<Shipping> getShipping() {
        return shipping;
    }

    @Override
    public String getPostURL() {
        return postURL;
    }

    @Override
    public String getPaymentDescription() {
        return paymentDescription;
    }

    @Override
    public HashMap<String, String> getPaymentMetadata() {
        return paymentMetadata;
    }

    @Override
    public Reference getPaymentReference() {
        return paymentReference;
    }

    @Override
    public String getPaymentStatementDescriptor() {
        return paymentStatementDescriptor;
    }

    @Override
    public boolean getRequires3DSecure() {
        return requires3DSecure;
    }

    @Override
    public Receipt getReceiptSettings() {
        return receiptSettings;
    }
}
