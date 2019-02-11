package company.tap.gosellapi.open.data_manager;

/**
 * this object is just an example so client can follow to send us PaymentDataSource Object that implements PaymentDataSource interface
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;

public class PaymentDataSource implements company.tap.gosellapi.open.interfaces.PaymentDataSource {

    /////////////////////////////////    Props Area  //////////////////////////////////////////////
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

    //////////////////////// Instantiation Using Singleton  ///////////////////////////////////////

    public PaymentDataSource() {
//        readSampleAppSettingsFromSharedPreferences();
        //generateDefaultPaymentData();
    }

    public static PaymentDataSource getInstance(){
        return  SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final PaymentDataSource INSTANCE = new PaymentDataSource();
    }


    //////////////////////// Setter's Area  ///////////////////////////////////////

    /**
     * @param tapCurrency
     */
    public void setTransactionCurrency(@NonNull TapCurrency tapCurrency){
     this.currency = tapCurrency;
    }

    /**
     * @param customer
     */
    public void setCustomer(@NonNull Customer customer){
      this.customer = customer;
    }

    public void setAmount(@Nullable BigDecimal amount){
     this.amount = amount;
    }

    /**
     * @param paymentItems
     */
    public void setPaymentItems(@Nullable ArrayList<PaymentItem> paymentItems){
     this.items = paymentItems;
    }

    /**
     * @param transactionMode
     */
    public void setTransactionMode(@Nullable TransactionMode transactionMode){
     this.transactionMode = transactionMode;
    }

    /**
     * @param taxes
     */
    public void setTaxes(@Nullable ArrayList<Tax> taxes){
      this.taxes = taxes;
    }

    /**
     * @param shippingList
     */
    public void setShipping(@Nullable ArrayList<Shipping> shippingList){
     this.shipping = shippingList;
    }

    /**
     * @param postURL
     */
    public void setPostURL(@Nullable String postURL){
      this.postURL = postURL;
    }

    /**
     * @param paymentDescription
     */
    public void setPaymentDescription(@Nullable String paymentDescription){
      this.paymentDescription = paymentDescription;
    }

    /**
     * @param paymentMetadata
     */
    public void setPaymentMetadata(@Nullable HashMap<String,String> paymentMetadata){
     this.paymentMetadata = paymentMetadata;
   }

    /**
     * @param paymentReference
     */
    public void setPaymentReference(@Nullable Reference paymentReference){
     this.paymentReference = paymentReference;
   }

    /**
     * @param paymentDescription
     */
    public void setPaymentStatementDescriptor(@Nullable String paymentDescription){
      this.paymentDescription = paymentDescription;
   }

    /**
     * @param requires3DSecure
     */
    public void isRequires3DSecure(@Nullable boolean requires3DSecure){
     this.requires3DSecure = requires3DSecure;
   }

    /**
     * @param receipt
     */
    public void setReceiptSettings(@Nullable Receipt receipt){
     this.receiptSettings = receipt;
   }

    /**
     * @param authorizeAction
     */
    public void setAuthorizeAction(@Nullable AuthorizeAction authorizeAction){
         this.authorizeAction = authorizeAction;
   }

/////////////////   Getter's Area  /////////////////////////////////////////////////////////////////
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

}
