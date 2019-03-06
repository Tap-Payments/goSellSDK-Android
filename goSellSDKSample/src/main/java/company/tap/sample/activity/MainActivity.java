package company.tap.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.sample.R;
import company.tap.sample.constants.SettingsKeys;
import company.tap.sample.managers.SettingsManager;


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

        if (sdkSession == null)
            sdkSession = new SDKSession();

        initButton();
        setCardSectionAppearance();
        startSDKSession();
    }

    private void initButton() {
        payButtonView = findViewById(R.id.payButtonId);
        sdkSession.setButtonView(payButtonView, this, SDK_REQUEST_CODE);
        /***
         *  setPayButtonBackgroundSelector
          */
         //sdkSession.setPayButtonBackgroundSelector(ContextCompat.getDrawable(this, company.tap.gosellapi.R.drawable.btn_pay_selector);
        /***
         * setupBackgroundWithColorList
         */
//        sdkSession.setupBackgroundWithColorList(settingsManager.getTapButtonEnabledBackgroundColor(SettingsKeys.TAP_BUTTON_ENABLED_BACKGROUND_COLOR_KEY),
//                settingsManager.getTapButtonDisabledBackgroundColor(SettingsKeys.TAP_BUTTON_DISABLED_BACKGROUND_COLOR_KEY)
//                );

        /***
         * setupFontTypeFace
         */
//        String tapButtonFontFace =   settingsManager.getTapButtonFont(SettingsKeys.TAP_BUTTON_FONT_KEY);
//        Typeface font = null;
//        if(tapButtonFontFace!=null && !tapButtonFontFace.trim().equalsIgnoreCase("")){
//            font = Typeface.createFromAsset(getAssets(), tapButtonFontFace);
//        sdkSession.setupPayButtonFontTypeFace(font);

        /***
         * settextcolor
         */
        sdkSession.setupTextColor(
                settingsManager.getTapButtonEnabledTitleColor(SettingsKeys.TAP_BUTTON_ENABLED_TITLE_COLOR_KEY),
                settingsManager.getTapButtonDisabledTitleColor(SettingsKeys.TAP_BUTTON_DISABLED_TITLE_COLOR_KEY)
        );



    }

    private void setCardSectionAppearance(){

    }


    private void startSDKSession() {

         sdkSession.addListener(this);

        TransactionMode trx_mode = settingsManager.getTransactionsMode();

        if (TransactionMode.SAVE_CARD == trx_mode) {
           sdkSession.setPayButtonTitle(getString(company.tap.gosellapi.R.string.save_card));
        } else {
            sdkSession.setPayButtonTitle(getString(company.tap.gosellapi.R.string.pay));
        }

        // initiate PaymentDataSource
        sdkSession.instantiatePaymentDataSource();

        // setup Payment Data Source
        sdkSession.setTransactionCurrency(settingsManager.getTransactionCurrency());

        sdkSession.setTransactionMode(settingsManager.getTransactionsMode());

        sdkSession.setCustomer(settingsManager.getCustomer());

        sdkSession.setPaymentItems(settingsManager.getPaymentItems());

        sdkSession.setTaxes(settingsManager.getTaxes());

        sdkSession.setShipping(settingsManager.getShippingList());

        sdkSession.setPostURL(settingsManager.getPostURL());

        sdkSession.setPaymentDescription(settingsManager.getPaymentDescription());

        sdkSession.setPaymentMetadata(settingsManager.getPaymentMetaData());

        sdkSession.setPaymentReference(settingsManager.getPaymentReference());

        sdkSession.setPaymentStatementDescriptor(settingsManager.getPaymentStatementDescriptor());

        sdkSession.isRequires3DSecure(settingsManager.is3DSecure());

        sdkSession.setReceiptSettings(settingsManager.getReceipt());

        sdkSession.setAuthorizeAction(settingsManager.getAuthorizeAction());


//        // Persist Payment Data Source
//        sdkSession.persistPaymentDataSource();

        //start without PayButton
        //sdkSession.start();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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
        showDialog(charge.getId(),charge.getResponse().getMessage(),company.tap.gosellapi.R.drawable.ic_checkmark_normal);
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

    }
}
