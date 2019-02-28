package company.tap.gosellapi.open.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.constants.SettingsKeys;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;
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

/**
 * The type Sdk trigger.
 */
public class SDKTrigger implements View.OnClickListener {

  private PayButtonView payButtonView;
  private PaymentDataSource paymentDataSource;
  private Activity activityListener;
  private int SDK_REQUEST_CODE;

  /**
   * Instantiates a new Sdk trigger.
   */
  public SDKTrigger() {}

  /**
   * Sets button view.
   *
   * @param buttonView       the button view
   * @param activity         the activity
   * @param SDK_REQUEST_CODE the sdk request code
   */
  public void setButtonView(View buttonView, Activity activity, int SDK_REQUEST_CODE) {

    this.SDK_REQUEST_CODE = SDK_REQUEST_CODE;

    if (buttonView instanceof PayButtonView) {
      this.payButtonView = (PayButtonView) buttonView;
      this.payButtonView.getPayButton().setOnClickListener(this);
      this.payButtonView.getSecurityIconView().setOnClickListener(this);
    }
    this.activityListener = activity;
  }

  /**
   * Set pay button background selector.
   *
   * @param selector the selector
   */
  public void setPayButtonBackgroundSelector(int selector){
    payButtonView.setBackgroundSelector(selector);
  }

  /**
   * Setup background with color list.
   *
   * @param enabledBackgroundColor  the enabled background color
   * @param disabledBackgroundColor the disabled background color
   */
  public void setupBackgroundWithColorList(int enabledBackgroundColor,int disabledBackgroundColor){
    payButtonView.setupBackgroundWithColorList(enabledBackgroundColor,disabledBackgroundColor);
  }

  /**
   * Setup pay button font type face.
   *
   * @param typeface the typeface
   */
  public void setupPayButtonFontTypeFace(Typeface typeface){
    payButtonView.setupFontTypeFace(typeface);
  }

  /**
   * Setup text color.
   *
   * @param enabledTextColor  the enabled text color
   * @param disabledTextColor the disabled text color
   */
  public void setupTextColor(int enabledTextColor,int disabledTextColor){
    payButtonView.setupTextColor(enabledTextColor,disabledTextColor);
  }


  /**
   * Set pay button title.
   *
   * @param title the title
   */
  public void setPayButtonTitle(String title){
    payButtonView.getPayButton().setText(title);
  }

  /**
   * Instantiate payment data source.
   */
  public void instantiatePaymentDataSource() {

    this.paymentDataSource  = company.tap.gosellapi.open.data_manager.PaymentDataSource.getInstance();

    SettingsManager settingsManager = SettingsManager.getInstance();

    settingsManager.setPref(activityListener);

  }


  private void persistPaymentDataSource(){
    PaymentDataManager.getInstance().setExternalDataSource(paymentDataSource);
  }


  /**
   * set transaction currency
   *
   * @param tapCurrency the tap currency
   */
  public void setTransactionCurrency(TapCurrency tapCurrency){
    paymentDataSource.setTransactionCurrency(tapCurrency);
  }


  /**
   * set transaction mode
   *
   * @param transactionMode the transaction mode
   */
  public void setTransactionMode(TransactionMode transactionMode){
    paymentDataSource.setTransactionMode(transactionMode);
  }

  /**
   * set customer
   *
   * @param customer the customer
   */
  public void setCustomer(Customer customer){
    paymentDataSource.setCustomer(customer);
  }

  /**
   * set payment items
   *
   * @param paymentItems the payment items
   */
  public void setPaymentItems(ArrayList<PaymentItem> paymentItems){
    paymentDataSource.setPaymentItems(paymentItems);
  }

  /**
   * set payment tax
   *
   * @param taxes the taxes
   */
  public void setTaxes(ArrayList<Tax> taxes){
    paymentDataSource.setTaxes(taxes);
  }

  /**
   * set payment shipping
   *
   * @param shipping the shipping
   */
  public void setShipping(ArrayList<Shipping> shipping){
    paymentDataSource.setShipping(shipping);
  }

  /**
   * set post url
   *
   * @param postURL the post url
   */
  public void setPostURL(String postURL){
    paymentDataSource.setPostURL(postURL);
  }

  /**
   * set payment description
   *
   * @param paymentDescription the payment description
   */
  public void setPaymentDescription(String paymentDescription){
    paymentDataSource.setPaymentDescription(paymentDescription);
  }

  /**
   * set payment meta data
   *
   * @param paymentMetadata the payment metadata
   */
  public void setPaymentMetadata(HashMap<String,String> paymentMetadata){
    paymentDataSource.setPaymentMetadata(paymentMetadata);
  }

  /**
   * set payment reference
   *
   * @param paymentReference the payment reference
   */
  public void setPaymentReference(Reference paymentReference){
    paymentDataSource.setPaymentReference(paymentReference);
  }

  /**
   * set payment statement descriptor
   *
   * @param setPaymentStatementDescriptor the set payment statement descriptor
   */
  public void setPaymentStatementDescriptor(String setPaymentStatementDescriptor){
    paymentDataSource.setPaymentStatementDescriptor(setPaymentStatementDescriptor);
  }

  /**
   * enable or disable 3dsecure
   *
   * @param is3DSecure the is 3 d secure
   */
  public void isRequires3DSecure(boolean is3DSecure){
    paymentDataSource.isRequires3DSecure(is3DSecure);
  }

  /**
   * set payment receipt
   *
   * @param receipt the receipt
   */
  public void setReceiptSettings(Receipt receipt){
    paymentDataSource.setReceiptSettings(receipt);
  }


  /**
   * set Authorize Action
   *
   * @param authorizeAction the authorize action
   */
  public void setAuthorizeAction(AuthorizeAction authorizeAction){
    paymentDataSource.setAuthorizeAction(authorizeAction);
  }


  /**
   * set Destination
   */

  public void setDestination(ArrayList<Destination> destination){
    paymentDataSource.setDestination(destination);
  }

  /**
   * Handle pay button click event
   * @param v
   */
  @Override
  public void onClick(View v) {
    int i = v.getId();

    if (i == payButtonView.getLayoutId() || i == R.id.pay_button_id) {
      getPaymentOptions();
    } else if (i != R.id.pay_security_icon_id) {
      return;
    }

  }

  /**
   * start goSellSDK without pay button
   */
  public void start(){
    getPaymentOptions();
  }

  /**
   * call payment methods API
   */
  private void getPaymentOptions() {
    persistPaymentDataSource();
    payButtonView.getLoadingView().start();
    System.out.println(
        " before call request this.paymentDataSource.getCurrency() : " + this.paymentDataSource
            .getCurrency().getIsoCode());
    PaymentOptionsRequest request = new PaymentOptionsRequest(

        this.paymentDataSource.getTransactionMode(),
        this.paymentDataSource.getAmount(),
        this.paymentDataSource.getItems(),
        this.paymentDataSource.getShipping(),
        this.paymentDataSource.getTaxes(),
        this.paymentDataSource.getCurrency().getIsoCode(),
        this.paymentDataSource.getCustomer().getIdentifier()
    );

    GoSellAPI.getInstance().getPaymentOptions(request,
        new APIRequestCallback<PaymentOptionsResponse>() {

          @Override
          public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {
            payButtonView.getLoadingView()
                .setForceStop(true, () -> startMainActivity());
          }

          @Override
          public void onFailure(GoSellError errorDetails) {
            payButtonView.getLoadingView().setForceStop(true);
          }
        });
  }

  /**
   * launch goSellSDK activity
   */
  private void startMainActivity() {
    Intent intent = new Intent(payButtonView.getContext(), GoSellPaymentActivity.class);
    activityListener.startActivityForResult(intent,SDK_REQUEST_CODE );
  }
}
