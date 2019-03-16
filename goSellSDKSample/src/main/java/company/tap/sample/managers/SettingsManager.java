package company.tap.sample.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;
import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.OperationMode;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destination;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;
import company.tap.sample.R;
import company.tap.sample.constants.SettingsKeys;
import company.tap.sample.viewmodels.CustomerViewModel;

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

    public void saveCustomer(String name,String middle,String last, String email,String sdn, String mobile, Context ctx) {

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);

        Gson gson = new Gson();

        String response = preferences.getString("customer" , "");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        if(customersList==null) customersList = new ArrayList<>();


        customersList.add(new CustomerViewModel(null,name,middle,last,email,sdn,mobile));


        String data =  gson.toJson(customersList);

        writeCustomersToPreferences(data,preferences);
    }

    public void editCustomer(CustomerViewModel oldCustomer, CustomerViewModel newCustomer, Context ctx){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);

        Gson gson = new Gson();

        String response = preferences.getString("customer","");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        if(customersList!=null){
           String customerRef =customersList.get(0).getRef();
            customersList.clear();

            customersList.add(new CustomerViewModel(
                    customerRef,
                    newCustomer.getName(),
                    newCustomer.getMiddleName(),
                    newCustomer.getLastName(),
                    newCustomer.getEmail(),
                    newCustomer.getSdn(),
                    newCustomer.getMobile()));

            String data =  gson.toJson(customersList);

            writeCustomersToPreferences(data,preferences);

        }else {
            saveCustomer(newCustomer.getName(),
                    newCustomer.getMiddleName(),
                    newCustomer.getLastName(),
                    newCustomer.getEmail(),
                    newCustomer.getSdn(),
                    newCustomer.getMobile(),ctx);
        }
    }

    private void writeCustomersToPreferences(String data, SharedPreferences preferences){
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString("customer",data);
        editor.commit();
    }

    public List<CustomerViewModel> getRegisteredCustomers(Context ctx) {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);
        Gson gson = new Gson();

        String response = preferences.getString("customer","");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        return (customersList!=null)?customersList:new ArrayList<>();
    }

    //////////////////////////////////////   Get Payment Settings ////////////////////////////////

    public Customer getCustomer(){

        Customer customer;

        Gson gson = new Gson();
        String response = pref.getString("customer", "");
        System.out.println(" get customer: "+response);
        ArrayList<company.tap.gosellapi.open.viewmodel.CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<company.tap.gosellapi.open.viewmodel.CustomerViewModel>>() {
                }.getType());

        // check if customer id is in pref.



        if (customersList != null) {
            System.out.println("preparing data source with customer ref :" + customersList.get(0).getRef());
            customer =
                    new Customer.CustomerBuilder(customersList.get(0).getRef()).
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
            System.out.println(" paymentResultDataManager.getCustomerRef(context) null");
            customer = new Customer.CustomerBuilder(null).
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

    public Receipt getReceipt(){
        return new Receipt(true, true);
    }

    public AuthorizeAction getAuthorizeAction(){
        return new AuthorizeAction(AuthorizeActionType.VOID, 10);
    }

    public ArrayList<Destination> getDestination() {
        ArrayList<Destination> destinations = new ArrayList<Destination>();
        destinations.
                add(new Destination(
                        "1014",
                        new BigDecimal(10),
                        new TapCurrency("kwd"),
                        "please deduct 10 kd for this account",
                        ""

                ));
        return destinations;
    }


    ////////////////////////////////////////////////// Specific Settings ////////////////////////////

    /**
     * Session Data Source
     */

    public OperationMode getSDKOperationMode(String key){
        String op_mode = pref.getString(key,OperationMode.SAND_BOX.name());

        if(op_mode.equals(OperationMode.SAND_BOX.name())) return OperationMode.SAND_BOX;

        return OperationMode.PRODUCTION;
    }

    /**
     * get Transaction mode
     * @param key
     * @return
     */
    public TransactionMode getTransactionsMode(String key){

        String trx_mode = pref.getString(key, TransactionMode.PURCHASE.name());

        if (trx_mode.equalsIgnoreCase(TransactionMode.PURCHASE.name()))
            return TransactionMode.PURCHASE;

        if (trx_mode.equalsIgnoreCase(TransactionMode.AUTHORIZE_CAPTURE.name()))
            return TransactionMode.AUTHORIZE_CAPTURE;

        return TransactionMode.SAVE_CARD;

    }


    /**
     * get transaction currency
     * @param key
     * @return
     */

    public TapCurrency getTransactionCurrency(String key){

        String trx_curr = pref.getString(key, "kwd");

        Log.d("Settings Manager","trx_curr :"+trx_curr.trim());

        if(trx_curr!=null  && !"".equalsIgnoreCase(trx_curr.trim()))
            return new TapCurrency(trx_curr);
        else
           return new TapCurrency("kwd");
    }

    /**
     * Get Appearance Mode [FULLSCREEN_MODE - WINDOWED_MODE]
     * @param key
     * @return
     */
    public AppearanceMode getAppearanceMode(String key)
    {
        String mode =  pref.getString(key,AppearanceMode.FULLSCREEN_MODE.name());
        if(mode.equals(AppearanceMode.WINDOWED_MODE.name())) return AppearanceMode.WINDOWED_MODE;

        return AppearanceMode.FULLSCREEN_MODE;
    }

    //////////////////////////////////////////////////  General ////////////////////////////////


    /**
     * Get Font name saved in session or return default
     * @param key
     * @param defaultFont
     * @return
     */
    public String getFont(String key,String defaultFont){
        System.out.println("pref: "+ pref.getString(key,defaultFont));
        return pref.getString(key,defaultFont);
    }

    /**
     * Get Color saved in session or return default
     * @param key
     * @param defaultColor
     * @return
     */
    public int getColor(String key,int defaultColor){
        String color = pref.getString(key, "");
       return extractColorCode(color,defaultColor);
    }


    /**
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key,boolean defaultValue){
        return pref.getBoolean(key,defaultValue);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key,String defaultValue ){
        return pref.getString(key,defaultValue);
    }

    public int getInt(String key,int defaultValue ){
       return pref.getInt(key,defaultValue) ;
    }


    //////////////////////////////////////////////////  PayButton /////////////////////////////////
    public int getTapButtonEnabledBackgroundColor(String key){
        String color = pref.getString(key, "");
        return extractEnabledBackgroundColorCode(color,company.tap.gosellapi.R.color.vibrant_green_pressed);
    }


    public int getTapButtonDisabledBackgroundColor(String key){
        String color = pref.getString(key, "");
        return extractDisabledBackgroundColorCode(color);
    }

    private int extractEnabledBackgroundColorCode(String color,int defaultColor) {
        if(color.trim().equalsIgnoreCase("")) return defaultColor;
        return Color.parseColor(color.split("_")[1]);
    }

    private int extractDisabledBackgroundColorCode(String color) {
        if(color.trim().equalsIgnoreCase("")) return company.tap.gosellapi.R.color.silver_light;
        return Color.parseColor(color.split("_")[1]);
    }

    public String getTapButtonFont(String key){
        String font = pref.getString(key, "");
        return font;
    }

    public int getTapButtonDisabledTitleColor(String key,int defaultColor){
        String color = pref.getString(key, "");
        return  extractColorCode(color,defaultColor);
    }

////////////////////////////////////////////  UTILS  //////////////////////////////////////////////

    public int getTapButtonEnabledTitleColor(String key,int defaultColor){
        String color = pref.getString(key, "");
        return extractColorCode(color,defaultColor);
    }

    private int extractColorCode(String color,int defaultColor) {
        if(color.trim().equalsIgnoreCase("")) return defaultColor;
        return Color.parseColor(color.split("_")[1]);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////


    private static class SingleInstanceAdmin{
        static SettingsManager instance = new SettingsManager();
    }
}
