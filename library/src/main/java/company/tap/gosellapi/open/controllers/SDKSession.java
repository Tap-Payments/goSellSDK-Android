package company.tap.gosellapi.open.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Merchant;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;

/**
 * The type Sdk session.
 */
public class SDKSession implements View.OnClickListener{

  private PayButtonView payButtonView;
  private PaymentDataSource paymentDataSource;
  private Activity activityListener;
  private int SDK_REQUEST_CODE;

  private static SessionDelegate sessionDelegate;
  private Activity context;

  /**
   * Instantiates a new Sdk session.
   */
  public SDKSession() {}


  /**
   * Error Type
   */

  public enum ErrorTypes {
      SDK_NOT_CONFIGURED_WITH_VALID_CONTEXT,
      INTERNET_NOT_AVAILABLE,
      INTERNET_AVAILABLE,
      CONNECTIVITY_MANAGER_ERROR,

  }
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

  public void setPayButtonLoaderVisible() {
    payButtonView.getLoadingView().setVisibility(ThemeObject.getInstance().isPayButtLoaderVisible()?View.VISIBLE:View.INVISIBLE);
  }


  /**
   * Instantiate payment data source.
   */
  public void instantiatePaymentDataSource() {

    this.paymentDataSource  = company.tap.gosellapi.open.data_manager.PaymentDataSource.getInstance();

  }


  private void persistPaymentDataSource(){
    PaymentDataManager.getInstance().setExternalDataSource(paymentDataSource);
  }

  /**
   * set amount
   */
  public void setAmount(BigDecimal amount){
    paymentDataSource.setAmount(amount);
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
   * enable or disable saving card.
   * @param saveCardStatus
   */
  public void isUserAllowedToSaveCard(boolean saveCardStatus){
    paymentDataSource.isUserAllowedToSaveCard(saveCardStatus);
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

  public void setDestination(Destinations destination){
    paymentDataSource.setDestination(destination);
  }

  /**
   * set Merchant ID
   * @param merchantId
   */

  public void setMerchantID(String merchantId){
    if(merchantId!=null && merchantId.trim().length()!=0)
    paymentDataSource.setMerchant(new Merchant(merchantId));
    else
      paymentDataSource.setMerchant(null);
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
  public void start(Activity context){
    this.context = context;
    getPaymentOptions();
  }

  /**
   * call payment methods API
   */
  private void getPaymentOptions() {
    switch (isInternetConnectionAvailable()) {
      case SDK_NOT_CONFIGURED_WITH_VALID_CONTEXT:
        showDialog("SDK Error", "SDK Not Configured Correctly");
        break;
      case CONNECTIVITY_MANAGER_ERROR:
        showDialog("SDK Error", "Device has a problem in Connectivity manager");
        break;
      case INTERNET_NOT_AVAILABLE:
        showDialog("Connection Error", "Internet connection is not available");
        break;
      case INTERNET_AVAILABLE:
        startPayment();
    }
  }

  public void startPayment(){
    persistPaymentDataSource();
    if(payButtonView!=null)
    payButtonView.getLoadingView().start();

    PaymentOptionsRequest request = new PaymentOptionsRequest(

        this.paymentDataSource.getTransactionMode(),
        this.paymentDataSource.getAmount(),
        this.paymentDataSource.getItems(),
        this.paymentDataSource.getShipping(),
        this.paymentDataSource.getTaxes(),
        this.paymentDataSource.getCurrency().getIsoCode(),
        this.paymentDataSource.getCustomer().getIdentifier(),
        (this.paymentDataSource.getMerchant()!=null)?this.paymentDataSource.getMerchant().getId():null
    );

    GoSellAPI.getInstance().getPaymentOptions(request,
        new APIRequestCallback<PaymentOptionsResponse>() {

          @Override
          public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {
            if(payButtonView!=null){
              if(ThemeObject.getInstance().isPayButtLoaderVisible())
                payButtonView.getLoadingView()
                        .setForceStop(true, () -> startMainActivity());
              else
                startMainActivity();
            }else {
              startMainActivity();
            }

          }

          @Override
          public void onFailure(GoSellError errorDetails) {
            if(ThemeObject.getInstance().isPayButtLoaderVisible()) {

              if(payButtonView!=null)
              payButtonView.getLoadingView().setForceStop(true);

              sessionDelegate.sdkError(errorDetails);
            }
            else{
              sessionDelegate.sdkError(errorDetails);
            }

          }
        });
  }



  private ErrorTypes isInternetConnectionAvailable(){
    Context ctx = getSDKContext();
    if(ctx==null) return ErrorTypes.SDK_NOT_CONFIGURED_WITH_VALID_CONTEXT;

    ConnectivityManager connectivityManager =   (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    if(connectivityManager!=null){
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) return ErrorTypes.INTERNET_AVAILABLE;
      else
        return ErrorTypes.INTERNET_NOT_AVAILABLE;
    }
     return ErrorTypes.CONNECTIVITY_MANAGER_ERROR;
  }
  /**
   * launch goSellSDK activity
   */
  private void startMainActivity() {
    if(payButtonView!=null )
      payButtonView.getLoadingView().setForceStop(true);

    if(getListener()!=null)
    getListener().sessionIsStarting();

    if(context!=null) {
      Intent intent = new Intent(context, GoSellPaymentActivity.class);
      context.startActivityForResult(intent, SDK_REQUEST_CODE);
    }else if(payButtonView!=null && payButtonView.getContext()!=null) {
      Intent intent = new Intent(payButtonView.getContext(), GoSellPaymentActivity.class);
      activityListener.startActivityForResult(intent,SDK_REQUEST_CODE );
    }else if (getListener()!=null){
        getListener().sessionFailedToStart();
    }

  }

  public void addSessionDelegate(SessionDelegate _sessionDelegate){
    sessionDelegate = _sessionDelegate;
  }



  public static SessionDelegate getListener(){
    return sessionDelegate;
  }


  private void showDialog(String title,String message){
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getSDKContext());
    dialogBuilder.setTitle(title);
    dialogBuilder.setMessage(message);
    dialogBuilder.setCancelable(false);

    dialogBuilder.setPositiveButton("Ok", (dialog, which) -> {});

    dialogBuilder.show();

  }

  private Context getSDKContext(){
    if(context!=null)return context;
    else if(payButtonView!=null && payButtonView.getContext()!=null)
     return payButtonView.getContext();
    return null;
  }
}
