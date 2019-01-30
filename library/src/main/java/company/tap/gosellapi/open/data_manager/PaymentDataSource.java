package company.tap.gosellapi.open.data_manager;

/**
 * this object is just an example so client can follow to send us PaymentDataSource Object that implements PaymentDataSource interface
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;
import company.tap.gosellapi.open.data_manager.PaymentResultDataManager;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.Tax;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.viewmodel.CustomerViewModel;

import static company.tap.gosellapi.internal.api.enums.AmountModificatorType.FIXED;
import static company.tap.gosellapi.internal.api.enums.AmountModificatorType.PERCENTAGE;
import static company.tap.gosellapi.internal.api.enums.measurements.Mass.KILOGRAMS;

public class PaymentDataSource implements company.tap.gosellapi.open.interfaces.PaymentDataSource {
    private @NonNull
    TapCurrency currency;
    private @NonNull
    Customer customer;
    private @Nullable
    BigDecimal amount;
    private @Nullable
    ArrayList<PaymentItem> items;
    private @Nullable
    TransactionMode transactionMode;
    private @Nullable
    ArrayList<Tax> taxes;
    private @Nullable
    ArrayList<Shipping> shipping;
    private @Nullable
    String postURL;
    private @Nullable
    String paymentDescription;
    private @Nullable
    HashMap<String, String> paymentMetadata;
    private @Nullable
    Reference paymentReference;
    private @Nullable
    String paymentStatementDescriptor;
    private @Nullable
    boolean requires3DSecure;
    private @Nullable
    Receipt receiptSettings;
    private @Nullable
    AuthorizeAction authorizeAction;
    private @NonNull
    Context context;

    private @NonNull
    String trx_curr = "kwd";
    private @NonNull
    String trx_mode = "fullscreen";

    @Override
    public @NonNull
    TapCurrency getCurrency() {
        return currency;
    }

    @Override
    public @NonNull
    Customer getCustomer() {
        return customer;
    }

    @Override
    public @Nullable
    BigDecimal getAmount() {
        return amount;
    }

    @Override
    public @Nullable
    ArrayList<PaymentItem> getItems() {
        return items;
    }

    @Override
    public @Nullable
    TransactionMode getTransactionMode() {
        return transactionMode;
    }

    @Override
    public @Nullable
    ArrayList<Tax> getTaxes() {
        return taxes;
    }

    @Override
    public @Nullable
    ArrayList<Shipping> getShipping() {
        return shipping;
    }

    @Override
    public @Nullable
    String getPostURL() {
        return postURL;
    }

    @Override
    public @Nullable
    String getPaymentDescription() {
        return paymentDescription;
    }

    @Override
    public @Nullable
    HashMap<String, String> getPaymentMetadata() {
        return paymentMetadata;
    }

    @Override
    public @Nullable
    Reference getPaymentReference() {
        return paymentReference;
    }

    @Override
    public @Nullable
    String getPaymentStatementDescriptor() {
        return paymentStatementDescriptor;
    }

    @Override
    public @Nullable
    boolean getRequires3DSecure() {
        return requires3DSecure;
    }

    @Override
    public @Nullable
    Receipt getReceiptSettings() {
        return receiptSettings;
    }

    @Override
    public @Nullable
    AuthorizeAction getAuthorizeAction() {
        return authorizeAction;
    }


    public PaymentDataSource(Context applicationContext) {
        this.context = applicationContext;
        readSampleAppSettingsFromSharedPreferences();
        generateDefaultPaymentData();
    }

    private void readSampleAppSettingsFromSharedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        trx_curr = pref.getString(context.getString(R.string.key_sdk_transaction_currency), "");
        if(trx_curr!=null  && !"".equalsIgnoreCase(trx_curr.trim()) )
              this.currency = new TapCurrency(trx_curr);
        else
            this.currency = new TapCurrency("kwd");

        trx_mode = pref.getString(context.getString(R.string.key_sdk_transaction_mode), "");

        if ("PURCHASE".equalsIgnoreCase(trx_mode))
            this.transactionMode = TransactionMode.PURCHASE;

        else if ("AUTHORIZE_CAPTURE".equalsIgnoreCase(trx_mode))
            this.transactionMode = TransactionMode.AUTHORIZE_CAPTURE;

        else if ("SAVE_CARD".equalsIgnoreCase(trx_mode))
            this.transactionMode = TransactionMode.SAVE_CARD;


        Gson gson = new Gson();
        String response = pref.getString("customers", "");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>() {
                }.getType());

        PaymentResultDataManager paymentResultDataManager = PaymentResultDataManager.getInstance();
        // check if customer id is in pref.
        System.out.println("preparing data source with customer ref :" + paymentResultDataManager.getCustomerRef(context));



        if (customersList != null) {
            this.customer =
                    new Customer.CustomerBuilder(paymentResultDataManager.getCustomerRef(context)).
                            firstName(customersList.get(0).getName()).
                            middleName(customersList.get(0).getMiddleName()).
                            lastName(customersList.get(0).getLastName()).
                            email(customersList.get(0).getEmail()).
                            phone(new PhoneNumber(customersList.get(0).getSdn(),
                                    customersList.get(0).getMobile()
                            )).
                            metadata("meta").
                            build();
        } else {
            this.customer = new Customer.CustomerBuilder(paymentResultDataManager.getCustomerRef(context)).
                    firstName("Name").
                    middleName("MiddleName").
                    lastName("Surname").
                    email("hello@tap.company").
                    phone(new PhoneNumber("965", "65562630")).
                    metadata("meta").
                    build();
        }
    }

    private void generateDefaultPaymentData() {

        this.amount = null;
        this.items = new ArrayList<>();

        this.items.add(
                new PaymentItem.PaymentItemBuilder("", new Quantity(KILOGRAMS, BigDecimal.ONE), BigDecimal.ONE)
                        .description("Description for test item #1")
                        .discount(new AmountModificator(FIXED, BigDecimal.ZERO))
                        .taxes(null)
                        .build());

//        Tax
        this.taxes = new ArrayList<Tax>();
        this.taxes.
                add(new Tax("Test tax #1",
                        "Test tax #1 description",
                        new AmountModificator(FIXED, BigDecimal.TEN)));

//        Shipping
        this.shipping = new ArrayList<Shipping>();
        this.shipping.
                add(new Shipping("Test shipping #1",
                        "Test shipping description #1",
                        BigDecimal.ONE));

//        Base URL
        this.postURL = "https://tap.company";
//
        this.paymentDescription = "Test payment description.";
//      Metadata
        this.paymentMetadata = new HashMap<>();
        this.paymentMetadata.put("metadata_key_1", "metadata value 1");
//      Payment reference
        this.paymentReference = new Reference(
                "acquirer_1",
                "gateway_1",
                "payment_1",
                "track_1",
                "transaction_1",
                "order_1");
//      Payment statement descriptor
        this.paymentStatementDescriptor = "Test payment statement descriptor.";
        this.requires3DSecure = true;
//      Receipt
        this.receiptSettings = new Receipt(true, true);
//      Authorization
        this.authorizeAction = new AuthorizeAction(AuthorizeActionType.VOID, 10);
    }

}
