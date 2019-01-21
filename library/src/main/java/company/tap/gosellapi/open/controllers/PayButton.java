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
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.interfaces.PaymentDataSource;

public class PayButton implements View.OnClickListener {

  private PayButtonView payButtonView;
  private PaymentDataSource paymentDataSource;
  private Activity activity;
  private int SDK_REQUEST_CODE;

  public PayButton() {
  }

  public void setButtonView(View buttonView, Activity activity, int SDK_REQUEST_CODE) {
    this.SDK_REQUEST_CODE = SDK_REQUEST_CODE;
    if (buttonView instanceof PayButtonView) {
      this.payButtonView = (PayButtonView) buttonView;
      this.payButtonView.getPayButton().setOnClickListener(this);
      this.payButtonView.getSecurityIconView().setOnClickListener(this);
    }
    this.activity = activity;

  }

  private void setPaymentDataSource(PaymentDataSource paymentDataSource) {
    PaymentDataManager.getInstance().setExternalDataSource(paymentDataSource);
    this.paymentDataSource = paymentDataSource;
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();

    if (i == payButtonView.getLayoutId() || i == R.id.pay_button_id) {
      setPaymentDataSource(new company.tap.gosellapi.open.data_manager.PaymentDataSource(activity));
      getPaymentOptions();
    } else if (i == R.id.pay_security_icon_id) {
    }
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

    ActivityDataExchanger activityDataExchanger = ActivityDataExchanger.getInstance();
    activityDataExchanger.saveClientActivity(activity.getClass());
    Intent intent = new Intent(payButtonView.getContext(), GoSellPaymentActivity.class);
    activity.startActivityForResult(intent,SDK_REQUEST_CODE );

  }
}
