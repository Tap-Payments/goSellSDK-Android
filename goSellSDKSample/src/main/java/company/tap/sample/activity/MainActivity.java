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

import java.util.ArrayList;
import java.util.List;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.TransactionMode;
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

        configureSDKThemeObject();

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

        configureSDKThemeObject();

        configureSDKSession();


    }

    /**
     * Configure SDK Theme
     */
    private void configureSDKThemeObject() {

        ThemeObject.getInstance()
        .setSdkLanguage(settingsManager.getString(SettingsKeys.TAP_SDK_LANGUAGE_KEY,"EN"))
//        .setAppearanceMode(settingsManager.getAppearanceMode(SettingsKeys.TAP_APPEARANCE_MODE))
        .setAppearanceMode(AppearanceMode.WINDOWED_MODE)

        .setHeaderFont(getTypeface(SettingsKeys.TAP_APPEARANCE_HEADER_FONT))
        .setHeaderTextColor(settingsManager.getColor(SettingsKeys.TAP_APPEARANCE_HEADER_TEXT_COLOR,getResources().getColor(R.color.black)))
//        .setHeaderTextColor(R.color.black1)
        .setHeaderTextSize(17)
        .setHeaderBackgroundColor(settingsManager.getColor(SettingsKeys.TAP_APPEARANCE_HEADER_BACKGROUND_COLOR,getResources().getColor(R.color.french_gray_new)))



        .setCardInputFont(getTypeface(SettingsKeys.TAP_CARD_INPUT_FONT))
        .setCardInputTextColor(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_TEXT_COLOR,getResources().getColor(R.color.black)))
        .setCardInputInvalidTextColor(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_INVALID_TEXT_COLOR,getResources().getColor(R.color.red)))
        .setCardInputPlaceholderTextColor(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_PLACEHOLDER_TEXT_COLOR,getResources().getColor(R.color.black)))


        .setSaveCardSwitchOffThumbTint(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_SWITCH_OFF_THUMB_TINT_COLOR,getResources().getColor(R.color.gray)))
        .setSaveCardSwitchOnThumbTint(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_SWITCH_ON_THUMB_TINT_COLOR,getResources().getColor(R.color.vibrant_green)))
        .setSaveCardSwitchOffTrackTint(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_SWITCH_OFF_TRACK_TINT_COLOR,getResources().getColor(R.color.gray)))
        .setSaveCardSwitchOnTrackTint(settingsManager.getColor(SettingsKeys.TAP_CARD_INPUT_SWITCH_ON_TRACK_TINT_COLOR,getResources().getColor(R.color.white)))

        .setScanIconDrawable(getResources().getDrawable(R.drawable.btn_card_scanner_normal))

//        .setPayButtonResourceId(R.drawable.btn_pay_selector)  //btn_pay_merchant_selector
//        .setPayButtonFont(getTypeface(SettingsKeys.TAP_BUTTON_FONT_KEY))

        .setPayButtonDisabledTitleColor(settingsManager.getColor(SettingsKeys.TAP_BUTTON_DISABLED_TITLE_COLOR_KEY,getResources().getColor(R.color.black)))
        .setPayButtonEnabledTitleColor(settingsManager.getColor(SettingsKeys.TAP_BUTTON_ENABLED_TITLE_COLOR_KEY,getResources().getColor(R.color.White)))

//        .setPayButtonTextSize(17)
//        .setPayButtonLoaderVisible(settingsManager.getBoolean(SettingsKeys.TAP_BUTTON_LOADER_VISIBLE,true))
//        .setPayButtonSecurityIconVisible(settingsManager.getBoolean(SettingsKeys.TAP_BUTTON_SECURITY_VISIBLE,true))
        ;

    }


    /**
     * Configure SDK Session
     */
    private void configureSDKSession() {

        if(sdkSession==null)sdkSession = new SDKSession();

         sdkSession.addSessionDelegate(this);

        // initiate PaymentDataSource
        sdkSession.instantiatePaymentDataSource();

        // setup Payment Data Source
        sdkSession.setTransactionCurrency(settingsManager.getTransactionCurrency(SettingsKeys.TAP_TRANSACTION_CURRENCY));

        System.out.println("settingsManager.getTransactionsMode(SettingsKeys.TAP_TRANSACTION_MODE) >> " +
                settingsManager.getTransactionsMode(SettingsKeys.TAP_TRANSACTION_MODE));

        sdkSession.setTransactionMode(settingsManager.getTransactionsMode(SettingsKeys.TAP_TRANSACTION_MODE));
//        sdkSession.setTransactionMode(TransactionMode.TOKENIZE_CARD);

        sdkSession.setCustomer(settingsManager.getCustomer());

        sdkSession.setPaymentItems(settingsManager.getPaymentItems());

        sdkSession.setTaxes(settingsManager.getTaxes());

        sdkSession.setShipping(settingsManager.getShippingList());

        sdkSession.setPostURL(settingsManager.getPostURL());

        sdkSession.setPaymentDescription(settingsManager.getPaymentDescription());

        sdkSession.setPaymentMetadata(settingsManager.getPaymentMetaData());

        sdkSession.setPaymentReference(settingsManager.getPaymentReference());

        sdkSession.setPaymentStatementDescriptor(settingsManager.getPaymentStatementDescriptor());

        sdkSession.isRequires3DSecure(settingsManager.getBoolean(SettingsKeys.TAP_3DSECURE_MODE,false));

        sdkSession.setReceiptSettings(settingsManager.getReceipt());

        sdkSession.setAuthorizeAction(settingsManager.getAuthorizeAction());

        sdkSession.setDestination(settingsManager.getDestination());

        // start with pay button
        initPayButton();

        //start without PayButton
        //sdkSession.start();
    }

    /**
     * Include pay button in merchant page
     */
    private void initPayButton() {

        payButtonView = findViewById(R.id.payButtonId);

//        payButtonView.setupFontTypeFace(ThemeObject.getInstance().getPayButtonFont());

        payButtonView.setupTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor(),
                ThemeObject.getInstance().getPayButtonDisabledTitleColor());
//
//        payButtonView.getPayButton().setTextSize(ThemeObject.getInstance().getPayButtonTextSize());
//
//        payButtonView.getSecurityIconView().setVisibility(ThemeObject.getInstance().isPayButtSecurityIconVisible()?View.VISIBLE:View.INVISIBLE);

        //payButtonView.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());

        TransactionMode trx_mode = settingsManager.getTransactionsMode(SettingsKeys.TAP_TRANSACTION_MODE);

        if (TransactionMode.SAVE_CARD == trx_mode) {
            payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.save_card));
        } else {
            payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.pay));
        }

        sdkSession.setButtonView(payButtonView, this, SDK_REQUEST_CODE);

//        sdkSession.setPayButtonLoaderVisible();

    }

    private Typeface getTypeface(String tapButtonFontKey) {
        System.out.println(" font: "+tapButtonFontKey);
        return Typeface.createFromAsset(getAssets(),
                settingsManager.getFont(tapButtonFontKey, "fonts/roboto_light.ttf"));
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
    public void cardTokenizedSuccessfully(@NonNull Token token) {
        System.out.println("Card Tokenized Succeeded : "+ token.getId());
        showDialog(token.getId(),"",company.tap.gosellapi.R.drawable.ic_checkmark_normal);
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
}
