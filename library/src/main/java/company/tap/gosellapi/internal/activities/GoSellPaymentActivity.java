package company.tap.gosellapi.internal.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.ChargeStatus;
import company.tap.gosellapi.internal.api.enums.ExtraFeesStatus;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authenticate;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;
import company.tap.gosellapi.internal.custom_views.DatePicker;
import company.tap.gosellapi.internal.custom_views.OTPFullScreenDialog;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import company.tap.gosellapi.internal.interfaces.ICardDeleteListener;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.TransactionMode;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * The type Go sell payment activity.
 */
public class GoSellPaymentActivity extends BaseActivity implements PaymentOptionsDataManager.PaymentOptionsDataListener, IPaymentProcessListener, OTPFullScreenDialog.ConfirmOTP,
        ICardDeleteListener
{
    private static final int SCAN_REQUEST_CODE = 123;
    private static final int CURRENCIES_REQUEST_CODE = 124;
    private static final int WEB_PAYMENT_REQUEST_CODE = 125;

    private PaymentOptionsDataManager dataSource;
    private FragmentManager fragmentManager;

    private PayButtonView payButton;

    private CardCredentialsViewModel cardCredentialsViewModel;
    private RecentSectionViewModel recentSectionViewModel;
    private boolean saveCardChecked;
    private Charge chargeOrAuthorizeOrSaveCard;
    private SavedCard savedCard;
    private WebPaymentViewModel webPaymentViewModel;

    private AppearanceMode apperanceMode ;

    static int mAppHeight;
    static int currentOrientation = -1;

    private boolean keyboardVisible=false;
    private boolean startPaymentFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in_top, android.R.anim.fade_out);

        apperanceMode = ThemeObject.getInstance().getAppearanceMode();

        if (apperanceMode == AppearanceMode.WINDOWED_MODE) {
            setContentView(R.layout.gosellapi_activity_main_windowed);
        } else {
            setContentView(R.layout.gosellapi_activity_main);
        }


        fragmentManager = getSupportFragmentManager();
        /**
         *  PaymentOptionsDataManager >> is the main actor who decide layout content
         */
        dataSource = PaymentDataManager.getInstance().getPaymentOptionsDataManager(this);

        final FrameLayout fragmentContainer = findViewById(R.id.paymentActivityFragmentContainer);

        //Register a callback to be invoked when the global layout state or the visibility of views within the view tree changes
        fragmentContainer.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        dataSource.setAvailableHeight(fragmentContainer.getHeight());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            fragmentContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        else
                            fragmentContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    }

                });

        initViews();
        SDKSession.getListener().sessionHasStarted();

        saveCardChecked = false;
        setKeyboardVisibilityListener();
    }

    private void initViews() {
        GoSellPaymentOptionsFragment paymentOptionsFragment = GoSellPaymentOptionsFragment
                .newInstance(dataSource);

        fragmentManager
                .beginTransaction()
                .replace(R.id.paymentActivityFragmentContainer, paymentOptionsFragment, "CARD")
                .commit();

        setupHeader();

        payButton = findViewById(R.id.payButtonId);
        payButton.setEnabled(false);
        payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());

        payButton.setOnClickListener(v -> {
            payButton.setEnabled(false);
            if(payButton.getLoadingView()!=null)payButton.getLoadingView().start();

            if(keyboardVisible) {
                startPaymentFlag=true;
              Utils.hideKeyboard(GoSellPaymentActivity.this);
            }
            else{
                startPaymentProcess();
            }
        });

        setupPayButton();
    }


    @Override
    public void onBackPressed() {
        SDKSession.getListener().sessionCancelled();
        super.onBackPressed();
    }

    private void setupHeader() {
        android.support.v7.widget.Toolbar  toolbar = findViewById(R.id.toolbar);
        TextView cancel_payment_text = findViewById(R.id.cancel_payment_icon);

        LinearLayout cancel_payment_ll = findViewById(R.id.cancel_payment);

        cancel_payment_ll.setOnClickListener(v -> onBackPressed());

        ////////////////////////////////////////////////////////////////////////////////////////////
        ImageView businessIcon = findViewById(R.id.businessIcon);
        TextView businessName = findViewById(R.id.businessName);
        String header_title = "";

        if (isTransactionModeSaveCard() || isTransactionModeTokenizeCard()) {
            header_title = getString(R.string.textview_disclaimer_save_card_header_title);

            LinearLayout businessIconNameContainer = findViewById(R.id.businessIconNameContainer);
            businessIconNameContainer.removeView(businessIcon);
            businessIconNameContainer.removeView(businessName);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.leftMargin = 0;
            ll.bottomMargin = 5;
            ll.topMargin = 18;
            ll.gravity = Gravity.CENTER_VERTICAL;
            businessName.setLayoutParams(ll);
            businessIconNameContainer.addView(businessName);

            cancel_payment_ll.removeView(cancel_payment_text);
            LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll2.leftMargin = 0;
            ll2.bottomMargin = 5;
            ll2.topMargin = 18;
            ll2.gravity = Gravity.CENTER_VERTICAL;
            cancel_payment_text.setLayoutParams(ll2);
            cancel_payment_ll.addView(cancel_payment_text);


        } else {
            String logoPath = PaymentDataManager.getInstance().getSDKSettings().getData().getMerchant().getLogo();
            Glide.with(this).load(logoPath).apply(RequestOptions.circleCropTransform()).into(businessIcon);
            header_title = PaymentDataManager.getInstance().getSDKSettings().getData().getMerchant().getName();
        }
        businessName.setText(header_title);


        if(ThemeObject.getInstance().getHeaderFont()!=null)
        businessName.setTypeface(ThemeObject.getInstance().getHeaderFont());
        if(ThemeObject.getInstance().getHeaderTextColor()!=0 )
        businessName.setTextColor(ThemeObject.getInstance().getHeaderTextColor());
        if(ThemeObject.getInstance().getHeaderTextSize()!=0)
        businessName.setTextSize(ThemeObject.getInstance().getHeaderTextSize());
        if(ThemeObject.getInstance().getHeaderBackgroundColor()!=0)
        toolbar.setBackgroundColor(ThemeObject.getInstance().getHeaderBackgroundColor());
    }

    private void setupPayButton() {
        payButton.getPayButton().setTextSize(ThemeObject.getInstance().getPayButtonTextSize());
        payButton.getSecurityIconView().setVisibility(ThemeObject.getInstance().isPayButtSecurityIconVisible()?View.VISIBLE:View.INVISIBLE);
        payButton.getLoadingView().setVisibility(ThemeObject.getInstance().isPayButtLoaderVisible()?View.VISIBLE:View.INVISIBLE);

        if (isTransactionModeSaveCard()) {
            setupSaveCardMode();
        } else {
            setupChargeOrAuthorizeMode();
        }
    }

    private void setupChargeOrAuthorizeMode() {
        payButton.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());

        if(ThemeObject.getInstance().getPayButtonFont()!=null)
        payButton.getPayButton().setTypeface(ThemeObject.getInstance().getPayButtonFont());

        payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());

        payButton.getPayButton().setText(String
                .format("%s %s %s", getResources().getString(R.string.pay),
                        dataSource.getSelectedCurrency().getSymbol(),
                        dataSource.getSelectedCurrency().getAmount()));
    }

    private void setupSaveCardMode() {
        payButton.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());

        if(ThemeObject.getInstance().getPayButtonFont()!=null)
           payButton.getPayButton().setTypeface(ThemeObject.getInstance().getPayButtonFont());

        if(ThemeObject.getInstance().getPayButtonDisabledTitleColor()!=0)
        payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());

        payButton.getPayButton().setText(getResources().getString(R.string.save_card));
    }


    private boolean isTransactionModeSaveCard() {
        return PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() == TransactionMode.SAVE_CARD;
    }

    private boolean isTransactionModeTokenizeCard() {
        return PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() == TransactionMode.TOKENIZE_CARD;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    private void setSelectedCard(SavedCard recentItem) {
        this.savedCard = recentItem;
    }

    private SavedCard getSavedCard() {
        return this.savedCard;
    }

    //  ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void startCurrencySelection(ArrayList<AmountedCurrency> currencies,
                                       AmountedCurrency selectedCurrency) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        intent.putExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_DATA, currencies);
        intent.putExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY,
                selectedCurrency);

        startActivityForResult(intent, CURRENCIES_REQUEST_CODE);

        // custom animation like swapping from left to right
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    /**
     * start web payment activity
     */

    @Override
    public void startWebPayment(WebPaymentViewModel model) {
        this.webPaymentViewModel = model;
        PaymentDataManager.getInstance().checkWebPaymentExtraFees(model, this);
    }

    private void startWebPaymentProcess() {
        Intent intent = new Intent(this, WebPaymentActivity.class);
        ActivityDataExchanger.getInstance().setWebPaymentViewModel(webPaymentViewModel);
        startActivityForResult(intent, WEB_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void startScanCard() {
        Intent scanCard = new Intent(this, CardIOActivity.class);
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true);
        scanCard.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
        scanCard.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
        scanCard.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);

        startActivityForResult(scanCard, SCAN_REQUEST_CODE);
    }

    private void startSavedCardPaymentProcess() {
//        Log.d("GoSellPaymentActivity"," getSavedCard().getPaymentOptionIdentifier() : " + getSavedCard().getPaymentOptionIdentifier());
        PaymentDataManager.getInstance().checkSavedCardPaymentExtraFees(getSavedCard(), this);

    }

    private void startCardPaymentProcess(CardCredentialsViewModel paymentOptionViewModel) {
        if(PaymentDataManager.getInstance().getExternalDataSource().getTransactionMode()==TransactionMode.TOKENIZE_CARD)
            initCardTokenization();
        else
        PaymentDataManager.getInstance().checkCardPaymentExtraFees(paymentOptionViewModel, this);
    }

    private void initSavedCardPaymentProcess() {
        PaymentDataManager.getInstance()
                .initiateSavedCardPayment(getSavedCard(), recentSectionViewModel, this);
    }

    private void initCardPaymentProcess() {
        PaymentDataManager.getInstance().initiatePayment(cardCredentialsViewModel, this);
    }

    private void initCardTokenization(){
            PaymentDataManager.getInstance().initCardTokenizationPayment(cardCredentialsViewModel,GoSellPaymentActivity.this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void updatePayButtonWithExtraFees(PaymentOption paymentOption) {

        updatePayButtonWithFees(paymentOption);

    }

    @Override
    public void savedCardClickedForDeletion(@NonNull String cardId) {

        if(PaymentDataManager.getInstance().getExternalDataSource().getCustomer()==null)
            return;
        payButton.setEnabled(false);
        payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());

        LoadingScreenManager.getInstance().showLoadingScreen(this);
         PaymentDataManager.getInstance().deleteCard(
                 PaymentDataManager.getInstance().getExternalDataSource().getCustomer().getIdentifier(),
                 cardId,
                 this);
    }

    @Override
    public void disablePayButton() {
      payButton.setEnabled(false);
      payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());
    }


    private void updatePayButtonWithFees(PaymentOption paymentOption) {

        BigDecimal feesAmount = PaymentDataManager.getInstance()
                .calculateCardExtraFees(paymentOption);

//        Log.d("GoSellPaymentActivity"," update pay button with : fees : " + feesAmount);

        if (!isTransactionModeSaveCard())
            payButton.getPayButton().setText(
                    String.format("%s %s", getResources().getString(R.string.pay),
                            PaymentDataManager.getInstance()
                                    .calculateTotalAmount(feesAmount)));
    }

    @Override
    public void updatePayButtonWithSavedCardExtraFees(SavedCard recentItem,
                                                      RecentSectionViewModel _recentSectionViewModel) {
        this.recentSectionViewModel = _recentSectionViewModel;
        setSelectedCard(recentItem);

        if (recentItem != null) {
            payButton.setEnabled(true);
            payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor());
            PaymentOption paymentOption = PaymentDataManager.getInstance()
                    .findSavedCardPaymentOption(recentItem);

            updatePayButtonWithFees(paymentOption);
        }
    }

    @Override
    public void cardExpirationDateClicked(CardCredentialsViewModel model) {

        String selectedMonth = null;
        String selectedYear = null;


        String modelExpirationMonth = model.getExpirationMonth();
        if (modelExpirationMonth != null && !modelExpirationMonth.isEmpty()) {

            selectedMonth = modelExpirationMonth;
        }

        String modelExpirationYear = model.getExpirationYear();
        if (modelExpirationYear != null && !modelExpirationYear.isEmpty()) {

            selectedYear = modelExpirationYear;
        }

        DatePicker.showInContext(this, selectedMonth, selectedYear,
                (month, year) -> dataSource.cardExpirationDateSelected(month, year));
    }


    @Override
    public void cardDetailsFilled(boolean isFilled,
                                  CardCredentialsViewModel _cardCredentialsViewModel) {
        setSelectedCard(null);
        cardCredentialsViewModel = _cardCredentialsViewModel;

        if (!isFilled && payButton.getLoadingView() != null && payButton.getLoadingView().isShown()) {
            payButton.getLoadingView().setForceStop(true);
        }

        if(isFilled)
            payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor());
        else
            payButton.getPayButton().setTextColor(ThemeObject.getInstance().getPayButtonDisabledTitleColor());

        payButton.setEnabled(isFilled);
    }


    @Override
    public void addressOnCardClicked() {
        BINLookupResponse binLookupResponse = PaymentDataManager.getInstance().getBinLookupResponse();
        if (binLookupResponse == null) return;
        Intent intent = new Intent(this, GoSellCardAddressActivity.class);
        intent.putExtra(GoSellCardAddressActivity.INTENT_EXTRA_KEY_COUNTRY,
                binLookupResponse.getCountry());
        startActivity(intent);
    }

    @Override
    public void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition) {
        saveCardChecked = isChecked;
    }

    @Override
    public void binNumberEntered(String binNumber) {
//        Log.d("GoSellPaymentActivity"," binNumberEntered >>> binNumber:" + binNumber);
        GoSellAPI.getInstance()
                .retrieveBINLookupBINLookup(binNumber, new APIRequestCallback<BINLookupResponse>() {
                    @Override
                    public void onSuccess(int responseCode, BINLookupResponse serializedResponse) {
                        dataSource.showAddressOnCardCell(true);
                        PaymentDataManager.getInstance().setBinLookupResponse(serializedResponse);
                        dataSource.setCurrentBINData(serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        dataSource.showAddressOnCardCell(false);
                        PaymentDataManager.getInstance().setBinLookupResponse(null);
                        dataSource.setCurrentBINData(null);
                    }
                });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus choice) {
        switch (choice) {
            case NO_EXTRA_FEES:
            case ACCEPT_EXTRA_FEES:
                startWebPaymentProcess();
                break;
            case REFUSE_EXTRA_FEES:
                LoadingScreenManager.getInstance().closeLoadingScreen();
                if(payButton!=null && payButton.getLoadingView()!=null)payButton.getLoadingView().setForceStop(true);
                payButton.setEnabled(true);
                break;
        }
    }

    @Override
    public void fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice) {
        if(payButton!=null && payButton.getLoadingView()!=null)payButton.getLoadingView().setForceStop(true);
        switch (userChoice) {
            case ACCEPT_EXTRA_FEES:
            case NO_EXTRA_FEES:
                initCardPaymentProcess();
                break;
            case REFUSE_EXTRA_FEES:
                LoadingScreenManager.getInstance().closeLoadingScreen();
                payButton.setEnabled(true);
                break;
        }
    }

    @Override
    public void fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice) {
        if(payButton!=null && payButton.getLoadingView()!=null)payButton.getLoadingView().setForceStop(true);
        switch (userChoice) {
            case ACCEPT_EXTRA_FEES:
            case NO_EXTRA_FEES:
                initSavedCardPaymentProcess();
                break;
            case REFUSE_EXTRA_FEES:
                LoadingScreenManager.getInstance().closeLoadingScreen();
                payButton.setEnabled(true);
                break;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private void openOTPScreen(Charge charge) {
        Log.d("GoSellPaymentActivity","openOTPScreen called .........");
        stopPayButtonLoadingView();
        if (charge.getAuthenticate() != null) {
            String phoneNumber = charge.getAuthenticate().getValue();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            android.support.v4.app.DialogFragment dialogFragment = new OTPFullScreenDialog();
            Bundle b = new Bundle();
            b.putString("phoneNumber", phoneNumber);
            dialogFragment.setArguments(b);
            ft.add(dialogFragment, OTPFullScreenDialog.TAG);
            ft.commitAllowingStateLoss();
        } else {
            closePaymentActivity();
        }
    }

    @Override
    public void confirmOTP() {
        LoadingScreenManager.getInstance().showLoadingScreen(GoSellPaymentActivity.this);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public void resendOTP() {
        LoadingScreenManager.getInstance().showLoadingScreen(GoSellPaymentActivity.this);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                    CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                    dataSource.cardScanned(scanResult);
                }
                break;

            case CURRENCIES_REQUEST_CODE:
                if(data==null)break;
                AmountedCurrency userChoiceCurrency = (AmountedCurrency) data
                        .getSerializableExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY);
                if (userChoiceCurrency != null) {
                    stopPayButtonLoadingView();
                    payButton.getPayButton().setText(
                            String.format("%s %s%s", getResources().getString(R.string.pay), userChoiceCurrency
                                    .getSymbol(), userChoiceCurrency.getAmount()));
                    updateDisplayedCards(userChoiceCurrency);
                }
                break;

            case WEB_PAYMENT_REQUEST_CODE:
                 if(data==null)break;
                if (resultCode == RESULT_OK)
                {
                    Log.d("GoSellPaymentActivity","data coming after closing WebPaymentActivity :"+data.getSerializableExtra("charge"));
                    if(data.getSerializableExtra("charge")!=null){
                        Charge charge = (Charge) data.getSerializableExtra("charge");
                        if(charge!=null) {
                            fireWebPaymentCallBack(charge);
                        }else
                        {
                            closePaymentActivity();
                            SDKSession.getListener().sdkError(null);
                        }
                    }
                    else {
                        closePaymentActivity();
                        SDKSession.getListener().sdkError(null);
                    }
                }
                else if(resultCode == RESULT_CANCELED) {
                    Log.d("GoSellPaymentActivity","data coming after closing WebPaymentActivity :"+data.getSerializableExtra("error"));
                    if(data.getSerializableExtra("error")!=null){
                        GoSellError goSellError = (GoSellError) data.getSerializableExtra("error");
                        if(goSellError!=null) {
                            closePaymentActivity();
                            SDKSession.getListener().sdkError(goSellError);
                        }else
                        {
                            closePaymentActivity();
                            SDKSession.getListener().sdkError(null);
                        }
                    }else {
                        closePaymentActivity();
                        SDKSession.getListener().sdkError(null);
                    }
                }
                break;

        }
    }

    private void fireWebPaymentCallBack(Charge charge){
        switch (charge.getStatus())
        {
            case CAPTURED:
            case AUTHORIZED:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().paymentSucceed(charge);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling fireWebPaymentCallBack >>> method paymentSucceed(charge)");
                    closePaymentActivity();
                }
                break;
            case FAILED:
            case ABANDONED:
            case CANCELLED:
            case DECLINED:
            case RESTRICTED:
            case UNKNOWN:
            case TIMEDOUT:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().paymentFailed(charge);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling fireWebPaymentCallBack >>> method paymentFailed(charge)");
                    closePaymentActivity();
                }
                break;
        }
    }

    /**
     * @param amountedCurrency this method will be called after user changes currency
     */
    private void updateDisplayedCards(AmountedCurrency amountedCurrency) {
        Log.d("GoSellPaymentActivity","new currency ... " + amountedCurrency.getCurrency());
        // filter views
        dataSource.currencySelectedByUser(amountedCurrency);
        // refresh layout [ filter view models according to new currency - reload views ]
        initViews();
        // update currency section
        dataSource.updateCurrencySection();
    }


    private void closePaymentActivity() {
        clearPaymentProcessListeners();
        finishActivity();
    }


    private void finishActivity() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        LoadingScreenManager.getInstance().closeLoadingScreen();
        closeActivity();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void didReceiveCharge(Charge charge) {
//        Log.d("GoSellPaymentActivity"," Cards >> didReceiveCharge * * * " + charge);
        if (charge == null) return;
        Log.d("GoSellPaymentActivity"," Cards >> didReceiveCharge * * * " + charge.getStatus());

        switch (charge.getStatus()) {
            case INITIATED:
                Authenticate authenticate = charge.getAuthenticate();
                if (authenticate != null && authenticate.getStatus() == AuthenticationStatus.INITIATED) {
                    switch (authenticate.getType()) {
                        case BIOMETRICS:

                            break;

                        case OTP:
                            Log.d("GoSellPaymentActivity"," coming charge type is ...  caller didReceiveCharge");
                            PaymentDataManager.getInstance().setChargeOrAuthorize(charge);
                            openOTPScreen(charge);
                            break;
                    }
                }
                break;
            case CAPTURED:
            case AUTHORIZED:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().paymentSucceed(charge);
                }catch (Exception e){
                    closePaymentActivity();
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method paymentSucceed(charge)");
                }
                break;
            case FAILED:
            case ABANDONED:
            case CANCELLED:
            case DECLINED:
            case RESTRICTED:
            case UNKNOWN:
            case TIMEDOUT:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().paymentFailed(charge);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method paymentFailed(charge)");
                    closePaymentActivity();
                }
                break;
        }
        obtainPaymentURLFromChargeOrAuthorizeOrSaveCard(charge);

    }


    @Override
    public void didReceiveSaveCard(SaveCard saveCard) {
        Log.d("GoSellPaymentActivity"," Cards >> didReceiveSaveCard * * * " + saveCard);
        if (saveCard == null) return;
        Log.d("GoSellPaymentActivity"," Cards >> didReceiveSaveCard * * * status :" + saveCard.getStatus());

        switch (saveCard.getStatus()) {
            case INITIATED:
                Authenticate authenticate = saveCard.getAuthenticate();
                if (authenticate != null && authenticate.getStatus() == AuthenticationStatus.INITIATED) {
                    switch (authenticate.getType()) {
                        case BIOMETRICS:

                            break;

                        case OTP:
                            Log.d("GoSellPaymentActivity"," start otp for save card mode........");
                            PaymentDataManager.getInstance().setChargeOrAuthorize(saveCard);
                            openOTPScreen(saveCard);
                            break;
                    }
                }
                break;
            case CAPTURED:
            case AUTHORIZED:
            case VALID:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().cardSaved(saveCard);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method cardSaved(saveCard)");
                    closePaymentActivity();
                }
                break;
            case INVALID:
            case FAILED:
            case ABANDONED:
            case CANCELLED:
            case DECLINED:
            case RESTRICTED:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().cardSavingFailed(saveCard);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method cardSavingFailed(saveCard)");
                    closePaymentActivity();
                }
                break;
        }
        obtainPaymentURLFromChargeOrAuthorizeOrSaveCard(saveCard);
    }

    @Override
    public void didCardSavedBefore() {
        Log.d("GoSellPaymentActivity"," card already saved before ....");
        stopPayButtonLoadingView();

    }

    @Override
    public void fireCardTokenizationProcessCompleted(Token token) {
        closePaymentActivity();
        SDKSession.getListener().cardTokenizedSuccessfully(token);
    }


    private void obtainPaymentURLFromChargeOrAuthorizeOrSaveCard(Charge chargeOrAuthorizeOrSaveCard) {
        Log.d("GoSellPaymentActivity","GoSellPaymentActivity..chargeOrAuthorizeOrSaveCard :" + chargeOrAuthorizeOrSaveCard.getStatus());

        if (chargeOrAuthorizeOrSaveCard.getStatus() != ChargeStatus.INITIATED) {
            return;
        }

        Authenticate authentication = chargeOrAuthorizeOrSaveCard.getAuthenticate();
        if (authentication != null)
            Log.d("GoSellPaymentActivity"," GoSellPaymentActivity>authentication : " + authentication.getStatus());
        if (authentication != null && authentication.getStatus() == AuthenticationStatus.INITIATED) {
            return;
        }

        String url = chargeOrAuthorizeOrSaveCard.getTransaction().getUrl();
//        Log.d("GoSellPaymentActivity","GoSellPaymentActivity >> Transaction().getUrl() :" + url);
//        Log.d("GoSellPaymentActivity","GoSellPaymentActivity >> chargeOrAuthorize :" + chargeOrAuthorizeOrSaveCard.getId());


        if (url != null) {
            // save charge id
            setChargeOrAuthorizeOrSaveCard(chargeOrAuthorizeOrSaveCard);
            LoadingScreenManager.getInstance().closeLoadingScreen();
            showWebView(chargeOrAuthorizeOrSaveCard.getTransaction().getUrl());
        }
    }

    private void showWebView(String url) {
        RelativeLayout popup_window = new RelativeLayout(this);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT);
        popup_window.setLayoutParams(fl);
        WebView w = new WebView(this);
        w.setScrollContainer(false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        w.setLayoutParams(params);
        w.setWebViewClient(new CardPaymentWebViewClient());
        WebSettings settings = w.getSettings();
        settings.setJavaScriptEnabled(true);
        popup_window.addView(w);
        setContentView(popup_window);
        w.loadUrl(url);
    }

    @Override
    public void didCardDeleted(DeleteCardResponse deleteCardResponse) {
            LoadingScreenManager.getInstance().closeLoadingScreen();
           if(dataSource!=null) dataSource.updateSavedCards(deleteCardResponse.getId());
    }

    @Override
    public void didDeleteCardReceiveError(GoSellError errorDetails) {
        LoadingScreenManager.getInstance().closeLoadingScreen();
        PaymentOptionsDataManager dataOptionsManager =  PaymentDataManager.getInstance().getPaymentOptionsDataManager(this);
        if(dataOptionsManager!=null)dataOptionsManager.cancelItemClicked();

        if(errorDetails!=null)
            Log.d("GoSellPaymentActivity","didDeleteCardReceiveError: response >>> "+errorDetails.getErrorBody());

        this.showDialog(getResources().getString(R.string.error_deleting_card_title),getResources().getString(R.string.error_deleting_card_msg),false);
    }


    /**
     * The type Card payment web view client.
     */
    public class CardPaymentWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.d("GoSellPaymentActivity","shouldOverrideUrlLoading :" + url);
            PaymentDataManager.WebPaymentURLDecision decision = PaymentDataManager.getInstance()
                    .decisionForWebPaymentURL(url);

            boolean shouldOverride = !decision.shouldLoad();
//            Log.d("GoSellPaymentActivity"," shouldOverrideUrlLoading : decision : " + shouldOverride);
            if (shouldOverride) { // if decision is true and response has TAP_ID
                // call backend to get charge response >> based of charge object type [Authorize - Charge] call retrieveCharge / retrieveAuthorize
                PaymentDataManager.getInstance().retrieveChargeOrAuthorizeOrSaveCardAPI(getChargeOrAuthorize());
            }
            return shouldOverride;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            Log.d("GoSellPaymentActivity","onPageFinished :" + url);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void didReceiveAuthorize(Authorize authorize) {
        Log.d("GoSellPaymentActivity"," Cards >> didReceiveAuthorize * * * " );
        if (authorize == null) return;
        Log.d("GoSellPaymentActivity"," Cards >> didReceiveAuthorize * * * " + authorize.getStatus());

        switch (authorize.getStatus()) {
            case INITIATED:
                Authenticate authenticate = authorize.getAuthenticate();
                if (authenticate != null && authenticate.getStatus() == AuthenticationStatus.INITIATED) {
                    switch (authenticate.getType()) {
                        case BIOMETRICS:

                            break;

                        case OTP:
                            PaymentDataManager.getInstance().setChargeOrAuthorize((Authorize) authorize);
                            openOTPScreen((Authorize) authorize);
                            break;
                    }
                }
                break;
            case CAPTURED:
            case AUTHORIZED:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().authorizationSucceed(authorize);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method authorizationSucceed(authorize)");
                    closePaymentActivity();
                }
                break;
            case FAILED:
            case ABANDONED:
            case CANCELLED:
            case DECLINED:
            case RESTRICTED:
                try
                {
                    closePaymentActivity();
                    SDKSession.getListener().authorizationFailed(authorize);
                }catch (Exception e){
                    Log.d("GoSellPaymentActivity"," Error while calling delegate method authorizationFailed(authorize)");
                    closePaymentActivity();
                }
                break;
        }
        obtainPaymentURLFromChargeOrAuthorizeOrSaveCard(authorize);
    }

    @Override
    public void didReceiveError(GoSellError error) {
        try{
            closePaymentActivity();
            SDKSession.getListener().sdkError(error);
        }catch (Exception e){
            Log.d("GoSellPaymentActivity","Error while try to call delegate method  sdkError(error)");
            closePaymentActivity();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setChargeOrAuthorizeOrSaveCard(Charge chargeOrAuthorizeOrSaveCard) {
        this.chargeOrAuthorizeOrSaveCard = chargeOrAuthorizeOrSaveCard;
    }


    private Charge getChargeOrAuthorize() {
        return chargeOrAuthorizeOrSaveCard;
    }


    private void stopPayButtonLoadingView() {
        LoadingScreenManager.getInstance().closeLoadingScreen();
        if (payButton.getLoadingView() != null) {
                if (payButton.getLoadingView().isShown())
                payButton.getLoadingView().setForceStop(true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearPaymentProcessListeners(){
        if(PaymentDataManager.getInstance()!=null)
            PaymentDataManager.getInstance().clearPaymentProcessListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPayButton();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setupPayButton();
    }


    private void closeActivity() {
        clearPaymentProcessListeners();
        setResult(RESULT_OK);
        finish();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////


    public  void setKeyboardVisibilityListener() {

        final View contentView = findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();

                if (newHeight == mPreviousHeight)
                    return;

                mPreviousHeight = newHeight;

                if (getResources().getConfiguration().orientation != currentOrientation) {
                    currentOrientation = getResources().getConfiguration().orientation;
                    mAppHeight =0;
                }

                if (newHeight >= mAppHeight) {
                    mAppHeight = newHeight;
                }

                if (newHeight != 0) {
                    if (mAppHeight > newHeight) {
                    // Height decreased: keyboard was shown
                        keyboardVisible = true;
                    } else
                    {
                        // Height increased: keyboard was hidden
                        keyboardVisible = false;
                        if(startPaymentFlag)
                        {

                            new CountDownTimer(1, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    startPaymentProcess();
                                }
                            }.start();


                        }
                    }
                }
            }
        });
    }

    private void startPaymentProcess(){
       checkInternetConnectivity();
    }

    private void checkInternetConnectivity(){
        ConnectivityManager connectivityManager =   (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
                LoadingScreenManager.getInstance().showLoadingScreen(this);
                if (getSavedCard() != null) {
                    startSavedCardPaymentProcess();
                } else {
                    startCardPaymentProcess(cardCredentialsViewModel);
                }
            }
            else
                showDialog(getResources().getString(R.string.internet_connectivity_title),getResources().getString(R.string.internet_connectivity_message),true);
        }
        else
        System.out.println(" some error in connectivity manager...");
    }

    private void showDialog(String title,String message, boolean showNegativeButton){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton(getResources().getString(R.string.dismiss), (dialog, which) -> System.out.println(" user dismissed process....."));

       if(showNegativeButton) dialogBuilder.setNegativeButton(getResources().getString(R.string.retry), (dialog, which) -> checkInternetConnectivity());

        dialogBuilder.show();

    }

}


