package company.tap.gosellapi.internal.data_managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.internal.api.models.ExtraFee;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.AmountCalculator;
import company.tap.gosellapi.internal.utils.CurrencyFormatter;

final class PaymentProcessManager {

  PaymentDataManager.WebPaymentURLDecision decisionForWebPaymentURL(String url) {

    boolean urlIsReturnURL = url.startsWith(Constants.RETURN_URL);
    boolean shouldLoad = !urlIsReturnURL;
    boolean redirectionFinished = urlIsReturnURL;
    boolean shouldCloseWebPaymentScreen = false;

    if(getCurrentPaymentViewModel().getPaymentOption() instanceof PaymentOption)
      shouldCloseWebPaymentScreen = redirectionFinished && ((PaymentOption)getCurrentPaymentViewModel().getPaymentOption()).getPaymentType() == PaymentType.CARD;

    if(getCurrentPaymentViewModel().getPaymentOption() instanceof CardCredentialsViewModelData)
      shouldCloseWebPaymentScreen = redirectionFinished;

    System.out.println(" shouldOverrideUrlLoading : shouldCloseWebPaymentScreen :" + shouldCloseWebPaymentScreen);
    @Nullable String tapID = null;

    Uri uri = Uri.parse(url);
    if (uri.getQueryParameterNames().contains(
        Constants.TAP_ID)) {  // if ReturnURL contains TAP_ID which means web flow finished then get TAP_ID and stop reloading web view with any urls

      tapID = uri.getQueryParameter(Constants.TAP_ID);
    }

    return PaymentDataManager.getInstance().new WebPaymentURLDecision(shouldLoad,
        shouldCloseWebPaymentScreen, redirectionFinished, tapID);
  }



  void startPaymentProcess(@NonNull final PaymentOptionViewModel paymentOptionModel) {

    IPaymentDataProvider provider = getDataProvider();

    AmountedCurrency amount = provider.getSelectedCurrency();
    System.out.println(" PaymentProcess >>> amount:" + amount.getCurrency() + " amount value :" + amount.getAmount().toString());
    // when it should be extra fees ????
    ArrayList<ExtraFee> extraFees;

    if( paymentOptionModel.getPaymentOption() instanceof PaymentOption)
     extraFees =((PaymentOption) paymentOptionModel.getPaymentOption()).getExtraFees();
    else
      extraFees = ((CardCredentialsViewModelData) paymentOptionModel.getPaymentOption()).getPaymentOptions().get(0).getExtraFees();

    if (extraFees == null) {
      extraFees = new ArrayList<>();
    }
    ArrayList<AmountedCurrency> supportedCurrencies = provider.getSupportedCurrencies();
    BigDecimal feesAmount = AmountCalculator
        .calculateExtraFeesAmount(extraFees, supportedCurrencies, amount);
    System.out.println("feesAmount.compareTo(BigDecimal.ZERO) : " + feesAmount.compareTo(BigDecimal.ZERO));
    if (feesAmount.compareTo(BigDecimal.ZERO) == 1) {

      AmountedCurrency extraFeesAmount = new AmountedCurrency(amount.getCurrency(), feesAmount);
      System.out.println("extraFeesAmount  : " + extraFeesAmount.getAmount());
      System.out.println("extraFeesAmount  : " + extraFeesAmount.toString());
      showExtraFeesAlert(amount, extraFeesAmount, new DialogManager.DialogResult() {
        @Override
        public void dialogClosed(boolean positiveButtonClicked) {

          if (positiveButtonClicked) {

            forceStartPaymentProcess(paymentOptionModel);
          }
        }
      });
    } else {

      forceStartPaymentProcess(paymentOptionModel);
    }
  }

  PaymentProcessManager(@NonNull IPaymentDataProvider dataProvider,
                        @NonNull IPaymentProcessListener listener) {

    this.dataProvider = dataProvider;
    this.processListener = listener;
  }

  @NonNull
  IPaymentDataProvider getDataProvider() {

    return dataProvider;
  }

  @NonNull
  IPaymentProcessListener getProcessListener() {

    return processListener;
  }

  @Nullable private PaymentOptionViewModel currentPaymentViewModel;

  public void setCurrentPaymentViewModel(
      @Nullable PaymentOptionViewModel currentPaymentViewModel) {
    this.currentPaymentViewModel = currentPaymentViewModel;
  }


  @Nullable
  public PaymentOptionViewModel getCurrentPaymentViewModel() {
    return currentPaymentViewModel;
  }

  private IPaymentDataProvider dataProvider;
  private IPaymentProcessListener processListener;

  private void showExtraFeesAlert(AmountedCurrency amount, AmountedCurrency extraFeesAmount,
                                  DialogManager.DialogResult callback) {

    AmountedCurrency totalAmount = new AmountedCurrency(amount.getCurrency(),
        amount.getAmount().add(extraFeesAmount.getAmount()), amount.getSymbol());

    String extraFeesText = CurrencyFormatter.format(extraFeesAmount);
    String totalAmountText = CurrencyFormatter.format(totalAmount);

    String title = "Confirm extra charges";
    String message = String.format(
        "You will be charged an additional fee of %s for this type of payment, totaling an amount of %s",
        extraFeesText, totalAmountText);

    DialogManager.getInstance().showDialog(title, message, "Confirm", "Cancel", callback);
  }

  private void forceStartPaymentProcess(@NonNull PaymentOptionViewModel paymentOptionModel) {
    System.out.println("paymentOptionModel instance of WebPaymentViewModel :"+ (paymentOptionModel instanceof WebPaymentViewModel));
    System.out.println("paymentOptionModel instance of CardCredentialsViewModel :"+ (paymentOptionModel instanceof CardCredentialsViewModel));
    if (paymentOptionModel instanceof WebPaymentViewModel) {
      setCurrentPaymentViewModel(paymentOptionModel);
      startPaymentProcessWithWebPaymentModel((WebPaymentViewModel) paymentOptionModel);
    } else if (paymentOptionModel instanceof CardCredentialsViewModel) {
      setCurrentPaymentViewModel(paymentOptionModel);
      startPaymentProcessWithCardPaymentModel((CardCredentialsViewModel) paymentOptionModel);
    }
  }

  private void startPaymentProcessWithWebPaymentModel(
      @NonNull WebPaymentViewModel paymentOptionModel) {

    PaymentOption paymentOption = paymentOptionModel.getPaymentOption();
    System.out.println(
        "startPaymentProcessWithWebPaymentModel >>> paymentOption.getSourceId : " + paymentOption
            .getSourceId());
    SourceRequest source = new SourceRequest(paymentOption.getSourceId());

    callChargeOrAuthorizeAPI(source, paymentOption, null, null);
  }

  private void startPaymentProcessWithCardPaymentModel(
      @NonNull CardCredentialsViewModel paymentOptionModel) {

    @Nullable CreateTokenCard card = paymentOptionModel.getCard();
    if (card == null) {
      return;
    }
//    System.out.println(" startPaymentProcessWithCardPaymentModel >>> card : getPaymentOptions size : "+paymentOptionModel.getPaymentOption().getPaymentOptions().size());
//    for(PaymentOption paymentOption: paymentOptionModel.getData().getPaymentOptions()){
//      System.out.println("@@@@@@@@ "+ paymentOption.getName());
//    }
//    for(PaymentOption paymentOption: paymentOptionModel.getPaymentOption().getPaymentOptions()){
//      System.out.println("@@@@@@@@ "+ paymentOption.getName());
//    }

    startPaymentProcessWithCard(card,paymentOptionModel.getPaymentOption().getPaymentOptions().get(0),
        paymentOptionModel.shouldSaveCard());
  }

  private void startPaymentProcessWithCard(@NonNull CreateTokenCard card,
                                           PaymentOption paymentOption, boolean saveCard) {

    CreateTokenWithCardDataRequest request = new CreateTokenWithCardDataRequest(card);

    callTokenAPI(request, paymentOption, saveCard);
  }

  private void callTokenAPI(@NonNull CreateTokenWithCardDataRequest request,
                            @NonNull final PaymentOption paymentOption,
                            @Nullable final boolean saveCard) {

    GoSellAPI.getInstance().createToken(request, new APIRequestCallback<Token>() {

      @Override
      public void onSuccess(int responseCode, Token serializedResponse) {
        System.out.println("startPaymentProcessWithCard >> serializedResponse: "+ serializedResponse);
        System.out.println("startPaymentProcessWithCard >> serializedResponse: first six :"+ serializedResponse.getCard().getFirstSix());
        SourceRequest source = new SourceRequest(serializedResponse);
        callChargeOrAuthorizeAPI(source, paymentOption, serializedResponse.getCard().getFirstSix(),
            saveCard);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {
        System.out.println("GoSellAPI.createToken : "+errorDetails.getErrorBody());
      }
    });
  }

  private void callChargeOrAuthorizeAPI(@NonNull SourceRequest source,
                                        @NonNull PaymentOption paymentOption,
                                        @Nullable String cardBIN, @Nullable Boolean saveCard) {

    Log.e("OkHttp", "CALL CHARGE API");

    IPaymentDataProvider provider = getDataProvider();

    ArrayList<AmountedCurrency> supportedCurrencies = provider.getSupportedCurrencies();
    String orderID = provider.getPaymentOptionsOrderID();
    System.out.println("orderID : " + orderID);
    System.out.println("saveCard : " + saveCard);
    @Nullable String postURL = provider.getPostURL();
    System.out.println("postURL : " + postURL);
    @Nullable TrackingURL post = postURL == null ? null : new TrackingURL(postURL);

    AmountedCurrency amountedCurrency = provider.getSelectedCurrency();
    Customer customer = provider.getCustomer();
    BigDecimal fee = AmountCalculator
        .calculateExtraFeesAmount(paymentOption.getExtraFees(), supportedCurrencies,
            amountedCurrency);
    Order order = new Order(orderID);
    TrackingURL redirect = new TrackingURL(Constants.RETURN_URL);
    String paymentDescription = provider.getPaymentDescription();
    HashMap<String, String> paymentMetadata = provider.getPaymentMetadata();
    Reference reference = provider.getPaymentReference();
    boolean shouldSaveCard = saveCard == null ? false : saveCard;
    String statementDescriptor = provider.getPaymentStatementDescriptor();
    boolean require3DSecure = provider
        .getRequires3DSecure();// this.dataSource.getRequires3DSecure() || this.chargeRequires3DSecure();
    Receipt receipt = provider.getReceiptSettings();
    TransactionMode transactionMode = provider.getTransactionMode();
    System.out.println("transactionMode : "+transactionMode);
    switch (transactionMode) {

      case PURCHASE:

        CreateChargeRequest chargeRequest = new CreateChargeRequest(

            amountedCurrency.getAmount(),
            amountedCurrency.getCurrency(),
            customer,
            fee,
            order,
            redirect,
            post,
            source,
            paymentDescription,
            paymentMetadata,
            reference,
            shouldSaveCard,
            statementDescriptor,
            require3DSecure,
            receipt
        );

        GoSellAPI.getInstance().createCharge(chargeRequest, new APIRequestCallback<Charge>() {
          @Override
          public void onSuccess(int responseCode, Charge serializedResponse) {

            handleChargeOrAuthorizeResponse(serializedResponse, null);
          }

          @Override
          public void onFailure(GoSellError errorDetails) {

            handleChargeOrAuthorizeResponse(null, errorDetails);
          }
        });

        break;

      case AUTHORIZE_CAPTURE:

        AuthorizeAction authorizeAction = provider.getAuthorizeAction();

        CreateAuthorizeRequest authorizeRequest = new CreateAuthorizeRequest(

            amountedCurrency.getAmount(),
            amountedCurrency.getCurrency(),
            customer,
            fee,
            order,
            redirect,
            post,
            source,
            paymentDescription,
            paymentMetadata,
            reference,
            shouldSaveCard,
            statementDescriptor,
            require3DSecure,
            receipt,
            authorizeAction
        );

        GoSellAPI.getInstance()
            .createAuthorize(authorizeRequest, new APIRequestCallback<Authorize>() {
              @Override
              public void onSuccess(int responseCode, Authorize serializedResponse) {

                handleChargeOrAuthorizeResponse(serializedResponse, null);
              }

              @Override
              public void onFailure(GoSellError errorDetails) {

                handleChargeOrAuthorizeResponse(null, errorDetails);
              }
            });
    }
  }

  private void handleChargeOrAuthorizeResponse(@Nullable Charge chargeOrAuthorize,
                                               @Nullable GoSellError error) {

    if (chargeOrAuthorize != null) {

      if (chargeOrAuthorize instanceof Authorize) {

        getProcessListener().didReceiveAuthorize((Authorize) chargeOrAuthorize);
      } else {

        getProcessListener().didReceiveCharge(chargeOrAuthorize);
      }
    }
  }


   <T extends Charge> void retrieveChargeOrAuthorizeAPI(T chargeOrAuthorize) {
    APIRequestCallback<T> callBack = new APIRequestCallback<T>() {
      @Override
      public void onSuccess(int responseCode, T serializedResponse) {
        System.out.println(" retrieveChargeOrAuthorizeAPI >>> "+ responseCode);
        System.out.println(" retrieveChargeOrAuthorizeAPI >>> "+ serializedResponse.getStatus() );
        System.out.println(" retrieveChargeOrAuthorizeAPI >>> "+ serializedResponse.getResponse().getMessage() );
        handleChargeOrAuthorizeResponse((Charge) serializedResponse,null);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {

      }
    };
    if (chargeOrAuthorize instanceof Charge)
      GoSellAPI.getInstance().retrieveCharge(chargeOrAuthorize.getId(), (APIRequestCallback<Charge>) callBack);
    else
      GoSellAPI.getInstance().retrieveAuthorize(chargeOrAuthorize.getId(), (APIRequestCallback<Authorize>) callBack);
  }

}
