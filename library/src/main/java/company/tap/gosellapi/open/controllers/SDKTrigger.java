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
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;

public class SDKTrigger implements View.OnClickListener {

  private PayButtonView payButtonView;
  private PaymentDataSource paymentDataSource;
  private Activity activityListener;
  private int SDK_REQUEST_CODE;

  public SDKTrigger() {}

  public void setButtonView(View buttonView, Activity activity, int SDK_REQUEST_CODE) {

    this.SDK_REQUEST_CODE = SDK_REQUEST_CODE;

    if (buttonView instanceof PayButtonView) {
      this.payButtonView = (PayButtonView) buttonView;
      this.payButtonView.getPayButton().setOnClickListener(this);
      this.payButtonView.getSecurityIconView().setOnClickListener(this);
    }
    this.activityListener = activity;
  }

  public void setPayButtonBackgroundSelector(int selector){
    payButtonView.setBackgroundSelector(selector);
  }

  public void setupBackgroundWithColorList(int enabledBackgroundColor,int disabledBackgroundColor){
    payButtonView.setupBackgroundWithColorList(enabledBackgroundColor,disabledBackgroundColor);
  }

  public void setupPayButtonFontTypeFace(Typeface typeface){
    payButtonView.setupFontTypeFace(typeface);
  }

  public void setupTextColor(int enabledTextColor,int disabledTextColor){
    payButtonView.setupTextColor(enabledTextColor,disabledTextColor);
  }


  public void setPayButtonTitle(String title){
    payButtonView.getPayButton().setText(title);
  }

  public void instantiatePaymentDataSource() {

    this.paymentDataSource  = company.tap.gosellapi.open.data_manager.PaymentDataSource.getInstance();

    SettingsManager settingsManager = SettingsManager.getInstance();

    settingsManager.setPref(activityListener);

  }


  public void persistPaymentDataSource(){
    PaymentDataManager.getInstance().setExternalDataSource(paymentDataSource);
  }



  public void setTransactionCurrency(TapCurrency tapCurrency){
    paymentDataSource.setTransactionCurrency(tapCurrency);
  }


  public void setTransactionMode(TransactionMode transactionMode){
    paymentDataSource.setTransactionMode(transactionMode);
  }


  public void setCustomer(Customer customer){
    paymentDataSource.setCustomer(customer);
  }

  public void setPaymentItems(ArrayList<PaymentItem> paymentItems){
    paymentDataSource.setPaymentItems(paymentItems);
  }

  public void setTaxes(ArrayList<Tax> taxes){
    paymentDataSource.setTaxes(taxes);
  }

  public void setShipping(ArrayList<Shipping> shipping){
    paymentDataSource.setShipping(shipping);
  }

  public void setPostURL(String postURL){
    paymentDataSource.setPostURL(postURL);
  }

  public void setPaymentDescription(String paymentDescription){
    paymentDataSource.setPaymentDescription(paymentDescription);
  }

  public void setPaymentMetadata(HashMap<String,String> paymentMetadata){
    paymentDataSource.setPaymentMetadata(paymentMetadata);
  }


  public void setPaymentReference(Reference paymentReference){
    paymentDataSource.setPaymentReference(paymentReference);
  }

  public void setPaymentStatementDescriptor(String setPaymentStatementDescriptor){
    paymentDataSource.setPaymentStatementDescriptor(setPaymentStatementDescriptor);
  }


  public void isRequires3DSecure(boolean is3DSecure){
    paymentDataSource.isRequires3DSecure(is3DSecure);
  }

  public void setReceiptSettings(Receipt receipt){
    paymentDataSource.setReceiptSettings(receipt);
  }



  public void setAuthorizeAction(AuthorizeAction authorizeAction){
    paymentDataSource.setAuthorizeAction(authorizeAction);
  }


  @Override
  public void onClick(View v) {
    int i = v.getId();

    if (i == payButtonView.getLayoutId() || i == R.id.pay_button_id) {
      getPaymentOptions();
    } else if (i != R.id.pay_security_icon_id) {
      return;
    }

  }


  public void start(){
    getPaymentOptions();
  }

  private void getPaymentOptions() {
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

  private void startMainActivity() {
    Intent intent = new Intent(payButtonView.getContext(), GoSellPaymentActivity.class);
    activityListener.startActivityForResult(intent,SDK_REQUEST_CODE );
  }
}
