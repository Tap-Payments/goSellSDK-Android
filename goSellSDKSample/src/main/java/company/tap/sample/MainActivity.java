package company.tap.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.measurements.Mass;
import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.Shipping;
import company.tap.gosellapi.internal.api.models.Tax;
import company.tap.gosellapi.internal.custom_views.GoSellPayButtonLayout;
import company.tap.gosellapi.internal.interfaces.GoSellPaymentDataSource;

import static company.tap.gosellapi.internal.api.enums.AmountModificatorType.FIXED;
import static company.tap.gosellapi.internal.api.enums.AmountModificatorType.PERCENTAGE;
import static company.tap.gosellapi.internal.api.enums.measurements.Mass.KILOGRAMS;

public class MainActivity extends AppCompatActivity implements GoSellPaymentDataSource {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.generateDefaultPaymentData();

        GoSellPayButtonLayout payButton = findViewById(R.id.payButtonId);
        payButton.setPaymentDataSource(this);
    }

    private void generateDefaultPaymentData() {

        this.currency = "KWD";
        this.customerInfo = new CustomerInfo(null, "Name", "Middlename", "Surname", "hello@tap.company", new PhoneNumber("965", "00000000"));
        this.items = new ArrayList<PaymentItem>();
        this.items.add(new PaymentItem("Test item #1", "Description for test item #1", new Quantity(KILOGRAMS, 1.0), 1.0f, new AmountModificator(PERCENTAGE, 1.0f), null));
        this.taxes = new ArrayList<Tax>();
        this.taxes.add(new Tax("Test tax #1", "Test tax #1 description", new AmountModificator(FIXED, 2.0f)));
        this.shipping = new ArrayList<Shipping>();
        this.shipping.add(new Shipping("Test shipping #1", "Test shipping description #1", 1.0));
        this.postURL = "https://tap.company";
        this.paymentDescription = "Test payment description.";
        this.paymentMetadata = new HashMap<String, String>();
        this.paymentMetadata.put("metadata_key_1", "metadata value 1");
        this.paymentReference = new Reference("acquirer_1","gateway_1","payment_1","track_1","transaction_1","order_1");
        this.paymentStatementDescriptor = "Test payment statement descriptor.";
        this.requires3DSecure = true;
        this.receiptSettings = new Receipt(true,true);
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
