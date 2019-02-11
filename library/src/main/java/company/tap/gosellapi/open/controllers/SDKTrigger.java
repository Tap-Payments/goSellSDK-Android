package company.tap.gosellapi.open.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;
import company.tap.gosellapi.open.enums.TransactionMode;

public class SDKTrigger implements View.OnClickListener {

  private PayButtonView payButtonView;
  private PaymentDataSource paymentDataSource;
  private Activity activityListener;
  private int SDK_REQUEST_CODE;

  public SDKTrigger() {
  }

  public void setButtonView(View buttonView, Activity activity, int SDK_REQUEST_CODE) {

    this.SDK_REQUEST_CODE = SDK_REQUEST_CODE;

    if (buttonView instanceof PayButtonView) {
      this.payButtonView = (PayButtonView) buttonView;
      this.payButtonView.getPayButton().setOnClickListener(this);
      this.payButtonView.getSecurityIconView().setOnClickListener(this);

      SettingsManager settingsManager = SettingsManager.getInstance();
      settingsManager.setPref(activity);

        TransactionMode trx_mode = settingsManager.getTransactionsMode();
      if (TransactionMode.SAVE_CARD == trx_mode) {
        payButtonView.getPayButton().setText(activity.getString(R.string.save_card));
      } else {
        payButtonView.getPayButton().setText(activity.getString(R.string.pay));
      }
    }
    this.activityListener = activity;
  }

  public void generatePaymentDataSource() {

    this.paymentDataSource  = company.tap.gosellapi.open.data_manager.PaymentDataSource.getInstance();

    SettingsManager settingsManager = SettingsManager.getInstance();

    settingsManager.setPref(activityListener);

    paymentDataSource.setTransactionCurrency(settingsManager.getTransactionCurrency());

    paymentDataSource.setTransactionMode(settingsManager.getTransactionsMode());

    paymentDataSource.setCustomer(settingsManager.getCustomer());

    paymentDataSource.setPaymentItems(settingsManager.getPaymentItems());

    paymentDataSource.setTaxes(settingsManager.getTaxes());

    paymentDataSource.setShipping(settingsManager.getShippingList());

    paymentDataSource.setPostURL(settingsManager.getPostURL());

    paymentDataSource.setPaymentDescription(settingsManager.getPaymentDescription());

    paymentDataSource.setPaymentMetadata(settingsManager.getPaymentMetaData());

    paymentDataSource.setPaymentReference(settingsManager.getPaymentReference());

    paymentDataSource.setPaymentStatementDescriptor(settingsManager.getPaymentStatementDescriptor());

    paymentDataSource.isRequires3DSecure(settingsManager.is3DSecure());

    paymentDataSource.setReceiptSettings(settingsManager.getReceipt());

    paymentDataSource.setAuthorizeAction(settingsManager.getAuthorizeAction());


    PaymentDataManager.getInstance().setExternalDataSource(paymentDataSource);
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();

    if (i == payButtonView.getLayoutId() || i == R.id.pay_button_id) {
      getPaymentOptions();
    } else if (i == R.id.pay_security_icon_id) {
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
