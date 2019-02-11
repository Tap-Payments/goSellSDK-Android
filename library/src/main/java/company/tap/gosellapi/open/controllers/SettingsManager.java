package company.tap.gosellapi.open.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;
import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.open.data_manager.PaymentResultDataManager;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;
import company.tap.gosellapi.open.viewmodel.CustomerViewModel;

import static company.tap.gosellapi.internal.api.enums.AmountModificatorType.FIXED;
import static company.tap.gosellapi.internal.api.enums.measurements.Mass.KILOGRAMS;

public class SettingsManager {

    private SharedPreferences pref ;
    private Context context;

    public void setPref(Context ctx){
        context = ctx;
        if(pref==null) pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SettingsManager getInstance(){
        return SingleInstanceAdmin.instance;
    }



    public TapCurrency getTransactionCurrency(){
        String trx_curr = pref.getString(context.getString(R.string.key_sdk_transaction_currency), "");
        TapCurrency currency;
        if(trx_curr!=null  && !"".equalsIgnoreCase(trx_curr.trim()) )
            currency = new TapCurrency(trx_curr);
        else
            currency = new TapCurrency("kwd");
        return currency;
    }


    public TransactionMode getTransactionsMode(){

        TransactionMode transactionMode = null;

        String trx_mode = pref.getString(context.getString(R.string.key_sdk_transaction_mode), "");

        if ("PURCHASE".equalsIgnoreCase(trx_mode))
           transactionMode = TransactionMode.PURCHASE;

        else if ("AUTHORIZE_CAPTURE".equalsIgnoreCase(trx_mode))
            transactionMode = TransactionMode.AUTHORIZE_CAPTURE;

        else if ("SAVE_CARD".equalsIgnoreCase(trx_mode))
            transactionMode = TransactionMode.SAVE_CARD;

        return transactionMode;
    }


    public Customer getCustomer(){

        Customer customer;

        Gson gson = new Gson();
        String response = pref.getString("customers", "");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>() {
                }.getType());

        PaymentResultDataManager paymentResultDataManager = PaymentResultDataManager.getInstance();
        // check if customer id is in pref.
        System.out.println("preparing data source with customer ref :" + paymentResultDataManager.getCustomerRef(context));



        if (customersList != null) {
           customer =
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
            customer = new Customer.CustomerBuilder(paymentResultDataManager.getCustomerRef(context)).
                    firstName("Name").
                    middleName("MiddleName").
                    lastName("Surname").
                    email("hello@tap.company").
                    phone(new PhoneNumber("965", "65562630")).
                    metadata("meta").
                    build();
        }

        return customer;
    }


    public ArrayList<PaymentItem> getPaymentItems(){
        ArrayList<PaymentItem> items = new ArrayList<>();

        items.add(
                new PaymentItem.PaymentItemBuilder("", new Quantity(KILOGRAMS, BigDecimal.ONE), BigDecimal.ONE)
                        .description("Description for test item #1")
                        .discount(new AmountModificator(FIXED, BigDecimal.ZERO))
                        .taxes(null)
                        .build());

        return  items;
    }


    public ArrayList<Tax> getTaxes(){
        ArrayList<Tax> taxes = new ArrayList<Tax>();
        taxes.
                add(new Tax("Test tax #1",
                        "Test tax #1 description",
                        new AmountModificator(FIXED, BigDecimal.TEN)));
        return taxes;
    }

    public ArrayList<Shipping> getShippingList(){
        ArrayList<Shipping> shipping = new ArrayList<Shipping>();
        shipping.
                add(new Shipping("Test shipping #1",
                        "Test shipping description #1",
                        BigDecimal.ONE));
        return shipping;
    }


    public String getPostURL(){
//        Base URL
        return  "https://tap.company";
    }

    public String getPaymentDescription(){
        return  "Test payment description.";
    }


    public HashMap<String,String> getPaymentMetaData(){
        HashMap<String,String> paymentMetadata = new HashMap<>();
        paymentMetadata.put("metadata_key_1", "metadata value 1");
        return paymentMetadata;
    }

    public Reference getPaymentReference(){
       Reference paymentReference = new Reference(
                "acquirer_1",
                "gateway_1",
                "payment_1",
                "track_1",
                "transaction_1",
                "order_1");
       return paymentReference;
    }


   public String getPaymentStatementDescriptor(){
        return "Test payment statement descriptor.";
   }

   public boolean is3DSecure(){
        return true;
   }


   public Receipt getReceipt(){
        return new Receipt(true, true);
   }

   public AuthorizeAction getAuthorizeAction(){
        return new AuthorizeAction(AuthorizeActionType.VOID, 10);
   }



    private static class SingleInstanceAdmin{
        static SettingsManager instance = new SettingsManager();
    }
}
