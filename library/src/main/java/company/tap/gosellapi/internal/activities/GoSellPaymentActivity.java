package company.tap.gosellapi.internal.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.custom_views.DatePicker;
import company.tap.gosellapi.internal.custom_views.OTPFullScreenDialog;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.delegate.PaymentProcessDelegate;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class GoSellPaymentActivity extends BaseActivity implements PaymentOptionsDataManager.PaymentOptionsDataListener, IPaymentProcessListener, OTPFullScreenDialog.ConfirmOTP{
  private static final int SCAN_REQUEST_CODE = 123;
  private static final int CURRENCIES_REQUEST_CODE = 124;
  private static final int WEB_PAYMENT_REQUEST_CODE = 125;

  private PaymentOptionsDataManager dataSource;
  private FragmentManager fragmentManager;

  private PayButtonView payButton;

  private CardCredentialsViewModel cardCredentialsViewModel;
  private RecentSectionViewModel recentSectionViewModel;
  private boolean saveCardChecked;
  private Charge chargeOrAuthorize;
  private SavedCard savedCard;
  private WebPaymentViewModel webPaymentViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.slide_in_top, android.R.anim.fade_out);
    setContentView(R.layout.gosellapi_activity_main);
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
  }



  private void initViews() {
    GoSellPaymentOptionsFragment paymentOptionsFragment = GoSellPaymentOptionsFragment
        .newInstance(dataSource);
    fragmentManager
        .beginTransaction()
        .replace(R.id.paymentActivityFragmentContainer, paymentOptionsFragment, "CARD")
        .commit();


    // setup toolbar
    ImageView businessIcon = findViewById(R.id.businessIcon);
    String logoPath = PaymentDataManager.getInstance().getSDKSettings().getData().getMerchant()
        .getLogo();
    Glide.with(this).load(logoPath).apply(RequestOptions.circleCropTransform()).into(businessIcon);

    String businessNameString = PaymentDataManager.getInstance().getSDKSettings().getData()
        .getMerchant().getName();
    TextView businessName = findViewById(R.id.businessName);
    businessName.setText(businessNameString);

    payButton = findViewById(R.id.payButtonId);
    payButton.setEnabled(false);

    payButton.getPayButton().setText(String
        .format("%s %s%s", getResources().getString(R.string.pay),
            dataSource.getSelectedCurrency().getSymbol(),
            dataSource.getSelectedCurrency().getAmount()));

    payButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Utils.hideKeyboard(GoSellPaymentActivity.this);
        if (getSavedCard() != null) {
          startSavedCardPaymentProcess();
        } else {
          startCardPaymentProcess(cardCredentialsViewModel);
        }
      }
    });

    // cancel icon
    LinearLayout cancel_payment = findViewById(R.id.cancel_payment);
    cancel_payment.setOnClickListener(v -> onBackPressed());
  }


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

    // custom animation like swapping
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
    System.out.println(" getSavedCard().getPaymentOptionIdentifier() : " + getSavedCard()
        .getPaymentOptionIdentifier());
    PaymentDataManager.getInstance().checkSavedCardPaymentExtraFees(getSavedCard(), this);

  }

  private void startCardPaymentProcess(CardCredentialsViewModel paymentOptionViewModel) {
    PaymentDataManager.getInstance().checkCardPaymentExtraFees(paymentOptionViewModel, this);
  }

  private void initSavedCardPaymentProcess() {
    // start tokenize card
    payButton.getLoadingView().start();
    PaymentDataManager.getInstance()
        .initiateSavedCardPayment(getSavedCard(), recentSectionViewModel, this);
  }

  private void initCardPaymentProcess() {
    payButton.getLoadingView().start();
    PaymentDataManager.getInstance().initiatePayment(cardCredentialsViewModel, this);

  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void updatePayButtonWithExtraFees(PaymentOption paymentOption) {

    BigDecimal feesAmount = PaymentDataManager.getInstance().calculateCardExtraFees(paymentOption);
    System.out.println(" update pay button with : fees : " + feesAmount);
    System.out.println(" update pay button with : total :" + PaymentDataManager.getInstance()
        .calculateTotalAmount(feesAmount));


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
      PaymentOption paymentOption = PaymentDataManager.getInstance()
          .findSavedCardPaymentOption(recentItem);

      BigDecimal feesAmount = PaymentDataManager.getInstance()
          .calculateCardExtraFees(paymentOption);

      System.out.println(" update pay button with : fees : " + feesAmount);
      System.out.println(" update pay button with : total :" + PaymentDataManager.getInstance()
          .calculateTotalAmount(feesAmount));


      payButton.getPayButton().setText(
          String.format("%s %s", getResources().getString(R.string.pay),
              PaymentDataManager.getInstance()
                  .calculateTotalAmount(feesAmount)));
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

//    if (isFilled) {
//      //hideSoftKeyBoard();
//    }
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
    System.out.println(" binNumberEntered >>> binNumber:" + binNumber);
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
        break;
    }
  }

  @Override
  public void fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice) {
    switch (userChoice) {
      case ACCEPT_EXTRA_FEES:
      case NO_EXTRA_FEES:
        initCardPaymentProcess();
        break;
      case REFUSE_EXTRA_FEES:
        break;
    }
  }

  @Override
  public void fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice) {
    switch (userChoice) {
      case ACCEPT_EXTRA_FEES:
      case NO_EXTRA_FEES:
        initSavedCardPaymentProcess();
        break;
      case REFUSE_EXTRA_FEES:
        break;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  private void openOTPScreen(Charge charge) {
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
    }else {
      closePaymentActivity(null);
    }
  }

  @Override
  public void confirmOTP() {
    LoadingScreenManager.getInstance().showLoadingScreen(GoSellPaymentActivity.this);

    Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
    if(fragment != null)
      getSupportFragmentManager().beginTransaction().remove(fragment).commit();
  }

  @Override
  public void resendOTP() {
    LoadingScreenManager.getInstance().showLoadingScreen(GoSellPaymentActivity.this);
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
    if(fragment != null)
      getSupportFragmentManager().beginTransaction().remove(fragment).commit();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
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
        if (resultCode == RESULT_OK) finish();
        break;

    }
  }

  /**
   * @param amountedCurrency
   *  this method will be called after user changes currency
   */
  private void updateDisplayedCards(AmountedCurrency amountedCurrency){
    System.out.println("new currency ... "+amountedCurrency.getCurrency() );
    // filter views
    dataSource.currencySelectedByUser(amountedCurrency);
    // refresh layout [ filter view models according to new currency - reload views ]
    initViews();
    // update currency section
    dataSource.updateCurrencySection();
  }



  private void closePaymentActivity(Charge charge) {
    setPaymentResult(charge);
    finishActivityWithResultCodeOK();
  }

  private void closePaymentActivityWithError(GoSellError error) {
    PaymentProcessDelegate.getInstance().setPaymentResult(null);
    PaymentProcessDelegate.getInstance().setPaymentError(error);
    finishActivityWithResultCodeOK();
  }

  private void finishActivityWithResultCodeOK() {
    System.out.println("finishActivityWithResultCodeOK ....  ");
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPFullScreenDialog.TAG);
    if(fragment != null)
      getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    LoadingScreenManager.getInstance().closeLoadingScreen();
    showDialog();
  }

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void didReceiveCharge(Charge charge) {
    System.out.println(" Cards >> didReceiveCharge * * * " + charge);
    if (charge == null) return;
    System.out.println(" Cards >> didReceiveCharge * * * " + charge.getStatus());

      switch (charge.getStatus()) {
        case INITIATED:
          Authenticate authenticate = charge.getAuthenticate();
          if (authenticate != null && authenticate.getStatus() == AuthenticationStatus.INITIATED) {
            switch (authenticate.getType()) {
              case BIOMETRICS:

                break;

              case OTP:
                PaymentDataManager.getInstance().setChargeOrAuthorize(charge);
                openOTPScreen(charge);
                break;
            }
          }
          break;
        case CAPTURED:
        case AUTHORIZED:
        case FAILED:
        case ABANDONED:
        case CANCELLED:
        case DECLINED:
        case RESTRICTED:
          closePaymentActivity(charge);
          break;
      }
      obtainPaymentURLFromChargeOrAuthorize(charge);

  }

  private void obtainPaymentURLFromChargeOrAuthorize(Charge chargeOrAuthorize) {
    System.out.println("GoSellPaymentActivity..charge_Authorize :" + chargeOrAuthorize.getStatus());

    if (chargeOrAuthorize.getStatus() != ChargeStatus.INITIATED) {
      return;
    }

    Authenticate authentication = chargeOrAuthorize.getAuthenticate();
    if (authentication != null)
      System.out.println(" GoSellPaymentActivity>authentication : " + authentication.getStatus());
    if (authentication != null && authentication.getStatus() == AuthenticationStatus.INITIATED) {
      return;
    }

    String url = chargeOrAuthorize.getTransaction().getUrl();
    System.out.println("GoSellPaymentActivity >> Transaction().getUrl() :" + url);
    System.out.println("GoSellPaymentActivity >> chargeOrAuthorize :" + chargeOrAuthorize.getId());


    if (url != null) {
      // save charge id
      setChargeOrAuthorize(chargeOrAuthorize);
      LoadingScreenManager.getInstance().closeLoadingScreen();
      showWebView(chargeOrAuthorize.getTransaction().getUrl());
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


  public class CardPaymentWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      System.out.println(" on page started : " + url);
      super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      System.out.println("shouldOverrideUrlLoading :" + url);
      PaymentDataManager.WebPaymentURLDecision decision = PaymentDataManager.getInstance()
          .decisionForWebPaymentURL(url);

      boolean shouldOverride = !decision.shouldLoad();
      System.out.println(" shouldOverrideUrlLoading : decision : " + shouldOverride);
      if (shouldOverride) { // if decision is true and response has TAP_ID
        // call backend to get charge response >> based of charge object type [Authorize - Charge] call retrieveCharge / retrieveAuthorize
        PaymentDataManager.getInstance().retrieveChargeOrAuthorizeAPI(getChargeOrAuthorize());
      }
      return shouldOverride;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      System.out.println("onPageFinished :" + url);
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void didReceiveAuthorize(Authorize authorize) {
    System.out.println(" Cards >> didReceiveAuthorize * * * " + authorize);
    if (authorize == null) return;
    System.out.println(" Cards >> didReceiveCharge * * * " + authorize.getStatus());

    switch (authorize.getStatus()) {
      case INITIATED:
        Authenticate authenticate = authorize.getAuthenticate();
        if (authenticate != null && authenticate.getStatus() == AuthenticationStatus.INITIATED) {
          switch (authenticate.getType()) {
            case BIOMETRICS:

              break;

            case OTP:
              PaymentDataManager.getInstance().setChargeOrAuthorize(authorize);
              openOTPScreen(authorize);
              break;
          }
        }
        break;
      case CAPTURED:
      case AUTHORIZED:
      case FAILED:
      case ABANDONED:
      case CANCELLED:
      case DECLINED:
      case RESTRICTED:
        closePaymentActivity(authorize);
        break;
    }
    obtainPaymentURLFromChargeOrAuthorize(authorize);
  }

  @Override
  public void didReceiveError(GoSellError error) {
    closePaymentActivityWithError(error);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////

  private void setChargeOrAuthorize(Charge chargeOrAuthorize) {
    this.chargeOrAuthorize = chargeOrAuthorize;
  }


  private Charge getChargeOrAuthorize() {
    return chargeOrAuthorize;
  }

  private void setPaymentResult(Charge chargeOrAuthorize) {
    PaymentProcessDelegate.getInstance().setCustomerID(chargeOrAuthorize, getApplicationContext());
    PaymentProcessDelegate.getInstance().setPaymentResult(chargeOrAuthorize);
  }


  private void stopPayButtonLoadingView() {
    if (payButton.getLoadingView() != null) {
      if (payButton.getLoadingView().isShown())
        payButton.getLoadingView().setForceStop(true);
    }
  }




  private void showDialog(){
    // show success bar
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int width = displayMetrics.widthPixels;
    PopupWindow popupWindow;
    try {
      LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      if(inflater!=null){
        View layout = inflater.inflate(R.layout.charge_status_layout, findViewById(R.id.popup_element));
        popupWindow = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        ImageView status_icon = layout.findViewById(R.id.status_icon);
        TextView statusText = layout.findViewById(R.id.status_text);
        TextView chargeText = layout.findViewById(R.id.charge_id_txt);

        String msg = "";

        if(chargeOrAuthorize!=null){
          System.out.println("chargeOrAuthorise.getStatus() : "+chargeOrAuthorize.getStatus());

          switch (chargeOrAuthorize.getStatus()){
            case CAPTURED:
            case AUTHORIZED:
              msg = getString(R.string.payment_status_alert_successful);
              status_icon.setImageResource(R.drawable.ic_checkmark_normal);
              break;
            case FAILED:
              msg = getString(R.string.payment_status_alert_failed);
              status_icon.setImageResource(R.drawable.icon_failed);
              break;
            case DECLINED:
              msg = getString(R.string.payment_status_alert_declined);
              status_icon.setImageResource(R.drawable.icon_failed);
              break;
          }
          chargeText.setText(chargeOrAuthorize.getId());
        }else{
          msg = getString(R.string.payment_status_alert_failed);
          status_icon.setImageResource(R.drawable.icon_failed);
          chargeText.setText("");
        }

        statusText.setText(msg);


        LinearLayout close_icon_ll = layout.findViewById(R.id.close_icon_ll);
        close_icon_ll.setOnClickListener(v -> {
          popupWindow.dismiss();closeActivity();
        });

        popupWindow.showAtLocation(layout, Gravity.TOP, 0, 50);
        popupWindow.setAnimationStyle(R.style.Animation);

        setupTimer(popupWindow);

      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void setupTimer(PopupWindow popupWindow){
    // Hide after some seconds
    final Handler handler  = new Handler();
    final Runnable runnable = () -> {
      if (popupWindow.isShowing()) {

        popupWindow.dismiss();
        closeActivity();
      }
    };

    popupWindow.setOnDismissListener(() -> handler.removeCallbacks(runnable));

    handler.postDelayed(runnable, 4000);
  }

private void closeActivity(){
  setResult(RESULT_OK);
  finish();
}

//  private void hideSoftKeyBoard(View view) {
//    try {
//      InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    } catch (Exception e) {
//      e.printStackTrace();
//      System.out.println("keyboard : " + e.getLocalizedMessage());
//    }
//  }

}


