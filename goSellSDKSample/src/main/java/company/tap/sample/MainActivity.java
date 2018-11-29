package company.tap.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;
import company.tap.gosellapi.internal.api.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Customer;
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
        this.customer = new Customer(null, "Name", "Middlename", "Surname", "hello@tap.company", new PhoneNumber("965", "00000000"));
        this.amount = null;
        this.items = new ArrayList<>();
        this.items.add(new PaymentItem("Test item #1", "Description for test item #1", new Quantity(KILOGRAMS, BigDecimal.ONE), BigDecimal.ONE, new AmountModificator(PERCENTAGE, BigDecimal.ONE), null));
        this.transactionMode = TransactionMode.PURCHASE;
        this.taxes = new ArrayList<Tax>();
        this.taxes.add(new Tax("Test tax #1", "Test tax #1 description", new AmountModificator(FIXED, BigDecimal.TEN)));
        this.shipping = new ArrayList<Shipping>();
        this.shipping.add(new Shipping("Test shipping #1", "Test shipping description #1", BigDecimal.ONE));
        this.postURL = "https://tap.company";
        this.paymentDescription = "Test payment description.";
        this.paymentMetadata = new HashMap<>();
        this.paymentMetadata.put("metadata_key_1", "metadata value 1");
        this.paymentReference = new Reference("acquirer_1","gateway_1","payment_1","track_1","transaction_1","order_1");
        this.paymentStatementDescriptor = "Test payment statement descriptor.";
        this.requires3DSecure = true;
        this.receiptSettings = new Receipt(true,true);
        this.authorizeAction = new AuthorizeAction(AuthorizeActionType.VOID, 10);
    }

    private @NonNull String currency;
    private @NonNull Customer customer;
    private @Nullable BigDecimal amount;
    private @Nullable ArrayList<PaymentItem> items;
    private @Nullable TransactionMode transactionMode;
    private @Nullable ArrayList<Tax> taxes;
    private @Nullable ArrayList<Shipping> shipping;
    private @Nullable String postURL;
    private @Nullable String paymentDescription;
    private @Nullable HashMap<String, String> paymentMetadata;
    private @Nullable Reference paymentReference;
    private @Nullable String paymentStatementDescriptor;
    private @Nullable boolean requires3DSecure;
    private @Nullable Receipt receiptSettings;
    private @Nullable AuthorizeAction authorizeAction;

    @Override
    public @NonNull String getCurrency() {
        return currency;
    }

    @Override
    public @NonNull Customer getCustomer() {
        return customer;
    }

    @Override
    public @Nullable BigDecimal getAmount() { return amount; }

    @Override
    public @Nullable ArrayList<PaymentItem> getItems() {
        return items;
    }

    @Override
    public @Nullable TransactionMode getTransactionMode() { return transactionMode; }

    @Override
    public @Nullable ArrayList<Tax> getTaxes() {
        return taxes;
    }

    @Override
    public @Nullable ArrayList<Shipping> getShipping() {
        return shipping;
    }

    @Override
    public @Nullable String getPostURL() {
        return postURL;
    }

    @Override
    public @Nullable String getPaymentDescription() {
        return paymentDescription;
    }

    @Override
    public @Nullable HashMap<String, String> getPaymentMetadata() {
        return paymentMetadata;
    }

    @Override
    public @Nullable Reference getPaymentReference() {
        return paymentReference;
    }

    @Override
    public @Nullable String getPaymentStatementDescriptor() {
        return paymentStatementDescriptor;
    }

    @Override
    public @Nullable boolean getRequires3DSecure() {
        return requires3DSecure;
    }

    @Override
    public @Nullable Receipt getReceiptSettings() {
        return receiptSettings;
    }

    @Override
    public @Nullable AuthorizeAction getAuthorizeAction() { return authorizeAction; }
}
