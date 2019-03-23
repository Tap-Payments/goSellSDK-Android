package company.tap.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.tap.gosellapi.GoSellSDK;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;
import company.tap.sample.R;
import company.tap.sample.constants.SettingsKeys;
import company.tap.sample.managers.SettingsManager;
import company.tap.sample.viewmodels.CustomerViewModel;


public class MainActivity extends AppCompatActivity implements SessionDelegate {
    private final int SDK_REQUEST_CODE = 1001;
    private SDKSession sdkSession;
    private PayButtonView payButtonView;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsManager = SettingsManager.getInstance();
        settingsManager.setPref(this);

        configureApp();

        configureSDKThemeObject(); // here you can configure your app theme.

        configureSDKSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(settingsManager==null)
        {
            settingsManager = SettingsManager.getInstance();
            settingsManager.setPref(this);
        }
    }


    private void configureApp(){
        GoSellSDK.init(this, "sk_test_kovrMB0mupFJXfNZWx6Etg5y","company.tap.goSellSDKExample");  // to be replaced by merchant
        GoSellSDK.setLocale(ThemeObject.getInstance().getSdkLanguage());
    }
    /**
     * Configure SDK Theme
     */
    private void configureSDKThemeObject() {

        ThemeObject.getInstance()
        .setSdkLanguage("EN")
        .setAppearanceMode(AppearanceMode.WINDOWED_MODE)

        .setHeaderFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"))
        .setHeaderTextColor(getResources().getColor(R.color.black1))
        .setHeaderTextSize(17)
        .setHeaderBackgroundColor(getResources().getColor(R.color.french_gray_new))


        .setCardInputFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"))
        .setCardInputTextColor(getResources().getColor(R.color.black))
        .setCardInputInvalidTextColor(getResources().getColor(R.color.red))
        .setCardInputPlaceholderTextColor(getResources().getColor(R.color.black))


        .setSaveCardSwitchOffThumbTint(getResources().getColor(R.color.gray))
        .setSaveCardSwitchOnThumbTint(getResources().getColor(R.color.vibrant_green))
        .setSaveCardSwitchOffTrackTint(getResources().getColor(R.color.gray))
        .setSaveCardSwitchOnTrackTint(getResources().getColor(R.color.white))

        .setScanIconDrawable(getResources().getDrawable(R.drawable.btn_card_scanner_normal))

        .setPayButtonResourceId(R.drawable.btn_pay_selector)  //btn_pay_merchant_selector
        .setPayButtonFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"))

        .setPayButtonDisabledTitleColor(getResources().getColor(R.color.black))
        .setPayButtonEnabledTitleColor(getResources().getColor(R.color.white))
        .setPayButtonTextSize(14)
        .setPayButtonLoaderVisible(true)
        .setPayButtonSecurityIconVisible(true)
        ;

    }


    /**
     * Configure SDK Session
     */
    private void configureSDKSession() {

        // Instantiate SDK Session
        if(sdkSession==null) sdkSession = new SDKSession();   //** Required **

        // pass your activity as a session delegate to listen to SDK internal payment process follow
        sdkSession.addSessionDelegate(this);    //** Required **

        // initiate PaymentDataSource
        sdkSession.instantiatePaymentDataSource();    //** Required **

        // set transaction currency associated to your account
        sdkSession.setTransactionCurrency(new TapCurrency("KWD"));    //** Required **

        // set transaction mode [TransactionMode.PURCHASE - TransactionMode.AUTHORIZE_CAPTURE - TransactionMode.SAVE_CARD - TransactionMode.TOKENIZE_CARD ]
        sdkSession.setTransactionMode(TransactionMode.TOKENIZE_CARD);    //** Required **

        // Using static CustomerBuilder method available inside TAP Customer Class you can populate TAP Customer object and pass it to SDK
        sdkSession.setCustomer(getCustomer());    //** Required **

        // Set Total Amount. The Total amount will be recalculated according to provided Taxes and Shipping
        sdkSession.setAmount(new BigDecimal(40));  //** Required **

        // Set Payment Items array list
        sdkSession.setPaymentItems(new ArrayList<PaymentItem>());// ** Optional ** you can pass empty array list

        // Set Taxes array list
        sdkSession.setTaxes(new ArrayList<Tax>());// ** Optional ** you can pass empty array list

        // Set Shipping array list
        sdkSession.setShipping(new ArrayList<>());// ** Optional ** you can pass empty array list

        // Post URL
        sdkSession.setPostURL(""); // ** Optional **

        // Payment Description
        sdkSession.setPaymentDescription(""); //** Optional **

        // Payment Extra Info
        sdkSession.setPaymentMetadata(new HashMap<>());// ** Optional ** you can pass empty array hash map

        // Payment Reference
        sdkSession.setPaymentReference(null); // ** Optional ** you can pass null

        // Payment Statement Descriptor
        sdkSession.setPaymentStatementDescriptor(""); // ** Optional **

        // Enable or Disable 3DSecure
        sdkSession.isRequires3DSecure(true);

        //Set Receipt Settings [SMS - Email ]
        sdkSession.setReceiptSettings(null); // ** Optional ** you can pass Receipt object or null

        // Set Authorize Action
        sdkSession.setAuthorizeAction(null); // ** Optional ** you can pass AuthorizeAction object or null

        sdkSession.setDestination(null); // ** Optional ** you can pass Destinations object or null

/**
 * If you included Tap Pay Button then configure it first
 */
        initPayButton();

        /**
         * Use this method where ever you want to show TAP SDK Main Screen.
         * This method must be called after you configured SDK as above
         * This method will be used in case of you are not using TAP PayButton in your activity.
         */

//        sdkSession.start(this);
    }

    private Customer getCustomer() {
        return new Customer.CustomerBuilder(null).email("abc@abc.com").firstName("firstname")
                .lastName("lastname").metadata("").phone(new PhoneNumber("965","65562630"))
                .middleName("middlename").build();
    }

    /**
     * Include pay button in merchant page
     */
    private void initPayButton() {

        payButtonView = findViewById(R.id.payButtonId);

        payButtonView.setupFontTypeFace(ThemeObject.getInstance().getPayButtonFont());

        payButtonView.setupTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor(),
                ThemeObject.getInstance().getPayButtonDisabledTitleColor());
//
        payButtonView.getPayButton().setTextSize(ThemeObject.getInstance().getPayButtonTextSize());
//
        payButtonView.getSecurityIconView().setVisibility(ThemeObject.getInstance().isPayButtSecurityIconVisible()?View.VISIBLE:View.INVISIBLE);

        payButtonView.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());

        TransactionMode trx_mode = settingsManager.getTransactionsMode(SettingsKeys.TAP_TRANSACTION_MODE);

        if (TransactionMode.SAVE_CARD == trx_mode) {
            payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.save_card));
        } else if(TransactionMode.TOKENIZE_CARD == trx_mode){
            payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.tokenize));
        }else {
            payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.pay));
        }
        sdkSession.setButtonView(payButtonView, this, SDK_REQUEST_CODE);
    }


    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    private void showDialog(String chargeID, String msg,int icon)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        PopupWindow popupWindow;
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {

                View layout = inflater.inflate(company.tap.gosellapi.R.layout.charge_status_layout, findViewById(
                        company.tap.gosellapi.R.id.popup_element));

                popupWindow = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                ImageView status_icon = layout.findViewById(company.tap.gosellapi.R.id.status_icon);
                TextView statusText = layout.findViewById(company.tap.gosellapi.R.id.status_text);
                TextView chargeText = layout.findViewById(company.tap.gosellapi.R.id.charge_id_txt);
                status_icon.setImageResource(icon);
                chargeText.setText(chargeID);
                statusText.setText((msg!=null&& msg.length()>30)?msg.substring(0,29):msg);


                LinearLayout close_icon_ll = layout.findViewById(company.tap.gosellapi.R.id.close_icon_ll);
                close_icon_ll.setOnClickListener(v -> {
                });

                popupWindow.showAtLocation(layout, Gravity.TOP, 0, 50);
                popupWindow.getContentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_show));

                setupTimer(popupWindow);
            }
        }catch(Exception e){
                e.printStackTrace();
            }
            }

    private void setupTimer(PopupWindow popupWindow) {
        // Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        };

        popupWindow.setOnDismissListener(() -> handler.removeCallbacks(runnable));

        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void paymentSucceed(@NonNull Charge charge) {

        System.out.println("Payment Succeeded : "+ charge.getStatus());
        System.out.println("Payment Succeeded : "+ charge.getDescription());
        System.out.println("Payment Succeeded : "+ charge.getResponse().getMessage());
//        System.out.println("Payment Succeeded : "+ charge.getDestinations().getDestination().size());
        saveCustomerRefInSession(charge);
        configureSDKSession();
        showDialog(charge.getId(),charge.getResponse().getMessage(),company.tap.gosellapi.R.drawable.ic_checkmark_normal);
    }

    private void saveCustomerRefInSession(Charge charge) {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String response = preferences.getString("customer" , "");


        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        if(customersList!=null) {
            customersList.clear();
            customersList.add(new CustomerViewModel(
                    charge.getCustomer().getIdentifier(),
                            charge.getCustomer().getFirstName(),
                            charge.getCustomer().getMiddleName(),
                            charge.getCustomer().getLastName(),
                            charge.getCustomer().getEmail(),
                            charge.getCustomer().getPhone().getCountryCode(),
                            charge.getCustomer().getPhone().getNumber()));

            String data = gson.toJson(customersList);

            writeCustomersToPreferences(data, preferences);
        }
    }

    private void writeCustomersToPreferences(String data, SharedPreferences preferences){
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString("customer",data);
        editor.commit();
    }

    @Override
    public void paymentFailed(@Nullable Charge charge) {
        System.out.println("Payment Failed : "+ charge.getStatus());
        System.out.println("Payment Failed : "+ charge.getDescription());
        System.out.println("Payment Failed : "+ charge.getResponse().getMessage());
        showDialog(charge.getId(),charge.getResponse().getMessage(),company.tap.gosellapi.R.drawable.icon_failed);
    }

    @Override
    public void authorizationSucceed(@NonNull Authorize authorize) {
        System.out.println("Authorize Succeeded : "+ authorize.getStatus());
        System.out.println("Authorize Succeeded : "+ authorize.getDescription());
        System.out.println("Authorize Succeeded : "+ authorize.getResponse().getMessage());
        saveCustomerRefInSession(authorize);
        configureSDKSession();
        showDialog(authorize.getId(),authorize.getResponse().getMessage(),company.tap.gosellapi.R.drawable.ic_checkmark_normal);
    }

    @Override
    public void authorizationFailed(Authorize authorize) {
        System.out.println("Authorize Failed : "+ authorize.getStatus());
        System.out.println("Authorize Failed : "+ authorize.getDescription());
        System.out.println("Authorize Failed : "+ authorize.getResponse().getMessage());
        showDialog(authorize.getId(),authorize.getResponse().getMessage(),company.tap.gosellapi.R.drawable.icon_failed);
    }


    @Override
    public void cardSaved(@NonNull Charge charge) {
        System.out.println("Card Saved Succeeded : "+ charge.getStatus());
        System.out.println("Card Saved Succeeded : "+ charge.getDescription());
        System.out.println("Card Saved Succeeded : "+ charge.getResponse().getMessage());
        saveCustomerRefInSession(charge);
        showDialog(charge.getId(),charge.getStatus().toString(),company.tap.gosellapi.R.drawable.ic_checkmark_normal);
    }

    @Override
    public void cardSavingFailed(@NonNull Charge charge) {
        System.out.println("Card Saved Failed : "+ charge.getStatus());
        System.out.println("Card Saved Failed : "+ charge.getDescription());
        System.out.println("Card Saved Failed : "+ charge.getResponse().getMessage());
        showDialog(charge.getId(),charge.getStatus().toString(),company.tap.gosellapi.R.drawable.icon_failed);
    }

    @Override
    public void cardTokenizedSuccessfully(@NonNull String token) {
        System.out.println("Card Tokenized Succeeded : "+ token);
        showDialog(token,"",company.tap.gosellapi.R.drawable.ic_checkmark_normal);
    }


    @Override
    public void sdkError(@Nullable GoSellError goSellError) {
        if(goSellError!=null) {
            System.out.println("SDK Process Error : " + goSellError.getErrorBody());
            System.out.println("SDK Process Error : " + goSellError.getErrorMessage());
            System.out.println("SDK Process Error : " + goSellError.getErrorCode());
            showDialog(goSellError.getErrorCode() + "", goSellError.getErrorMessage(), company.tap.gosellapi.R.drawable.icon_failed);
        }
    }


    @Override
    public void sessionIsStarting() {
        System.out.println(" Session Is Starting.....");
    }

    @Override
    public void sessionHasStarted() {
        System.out.println(" Session Has Started .......");
    }


    @Override
    public void sessionCancelled() {
        Log.d("MainActivity","Session Cancelled.........");
    }

    @Override
    public void sessionFailedToStart() {
        Log.d("MainActivity","Session Failed to start.........");
    }
}
