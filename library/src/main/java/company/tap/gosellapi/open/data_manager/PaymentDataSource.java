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

import company.tap.gosellapi.internal.api.models.Merchant;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destination;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;

/**
 * The type Payment data source.
 */
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
    private @NonNull
    boolean allowUserToSaveCard=true;
    private @Nullable
    Receipt receiptSettings;
    private @Nullable
    AuthorizeAction authorizeAction;
    private @Nullable
    Destinations destination;
    private @Nullable
    Merchant merchant;

    private @NonNull
    Context context;

    private @NonNull
    String trx_curr = "kwd";
    private @NonNull
    String trx_mode = "fullscreen";

    //////////////////////// Instantiation Using Singleton  ///////////////////////////////////////

    /**
     * Instantiates a new Payment data source.
     */
    public PaymentDataSource() {
//        readSampleAppSettingsFromSharedPreferences();
        //generateDefaultPaymentData();
    }

    /**
     * Get instance payment data source.
     *
     * @return the payment data source
     */
    public static PaymentDataSource getInstance(){
        return  SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final PaymentDataSource INSTANCE = new PaymentDataSource();
    }


    //////////////////////// Setter's Area  ///////////////////////////////////////

    /**
     * Set transaction currency.
     *
     * @param tapCurrency the tap currency
     */
    public void setTransactionCurrency(@NonNull TapCurrency tapCurrency){
     this.currency = tapCurrency;
    }

    /**
     * Set customer.
     *
     * @param customer the customer
     */
    public void setCustomer(@NonNull Customer customer){
      this.customer = customer;
    }

    /**
     * Set amount.
     *
     * @param amount the amount
     */
    public void setAmount(@Nullable BigDecimal amount){
     this.amount = amount;
    }

    /**
     * Set payment items.
     *
     * @param paymentItems the payment items
     */
    public void setPaymentItems(@Nullable ArrayList<PaymentItem> paymentItems){
     this.items = paymentItems;
    }

    /**
     * Set transaction mode.
     *
     * @param transactionMode the transaction mode
     */
    public void setTransactionMode(@Nullable TransactionMode transactionMode){
     this.transactionMode = transactionMode;
    }

    /**
     * Set taxes.
     *
     * @param taxes the taxes
     */
    public void setTaxes(@Nullable ArrayList<Tax> taxes){
      this.taxes = taxes;
    }

    /**
     * Set shipping.
     *
     * @param shippingList the shipping list
     */
    public void setShipping(@Nullable ArrayList<Shipping> shippingList){
     this.shipping = shippingList;
    }

    /**
     * Set post url.
     *
     * @param postURL the post url
     */
    public void setPostURL(@Nullable String postURL){
      this.postURL = postURL;
    }

    /**
     * Set payment description.
     *
     * @param paymentDescription the payment description
     */
    public void setPaymentDescription(@Nullable String paymentDescription){
      this.paymentDescription = paymentDescription;
    }

    /**
     * Set payment metadata.
     *
     * @param paymentMetadata the payment metadata
     */
    public void setPaymentMetadata(@Nullable HashMap<String,String> paymentMetadata){
     this.paymentMetadata = paymentMetadata;
   }

    /**
     * Set payment reference.
     *
     * @param paymentReference the payment reference
     */
    public void setPaymentReference(@Nullable Reference paymentReference){
     this.paymentReference = paymentReference;
   }

    /**
     * Set payment statement descriptor.
     *
     * @param paymentDescription the payment description
     */
    public void setPaymentStatementDescriptor(@Nullable String paymentDescription){
      this.paymentDescription = paymentDescription;
   }

    /**
     * Is user allowed to save his card
     * @param saveCardStatus
     */
    public void isUserAllowedToSaveCard(boolean saveCardStatus){
        this.allowUserToSaveCard = saveCardStatus;
    }

    /**
     * Is requires 3 d secure.
     *
     * @param requires3DSecure the requires 3 d secure
     */
    public void isRequires3DSecure(@Nullable boolean requires3DSecure){
     this.requires3DSecure = requires3DSecure;
   }



    /**
     * Set receipt settings.
     *
     * @param receipt the receipt
     */
    public void setReceiptSettings(@Nullable Receipt receipt){
     this.receiptSettings = receipt;
   }

    /**
     * Set authorize action.
     *
     * @param authorizeAction the authorize action
     */
    public void setAuthorizeAction(@Nullable AuthorizeAction authorizeAction){
         this.authorizeAction = authorizeAction;
   }

    /**
     * set Destination
     * @return
     */
    public void setDestination(Destinations destination){
        this.destination = destination;
    }

    /**
     * set Merchant
     * @param merchant
     */
    public void setMerchant(Merchant merchant){this.merchant=merchant;}

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
        if(items==null) return new ArrayList<PaymentItem>();
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
        if(taxes==null) return new ArrayList<Tax>();
        return taxes;
    }

    @Override
    public @Nullable
    ArrayList<Shipping> getShipping() {
        if(shipping==null) return new ArrayList<Shipping>();
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
    public @NonNull
    boolean getAllowedToSaveCard(){return allowUserToSaveCard; }
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

    @Override
    public @Nullable
    Destinations getDestination(){
        return  destination;
    }

    @Nullable
    @Override
    public Merchant getMerchant() {
        return merchant;
    }

}
