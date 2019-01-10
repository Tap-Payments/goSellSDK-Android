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
import company.tap.gosellapi.internal.api.enums.AuthenticationType;
import company.tap.gosellapi.internal.api.enums.ExtraFeesStatus;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.models.CreateTokenSavedCard;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.requests.CreateOTPVerificationRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithExistingCardDataRequest;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.internal.utils.Utils;
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
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.AmountCalculator;

final class PaymentProcessManager {

  PaymentDataManager.WebPaymentURLDecision decisionForWebPaymentURL(String url) {

    boolean urlIsReturnURL = url.startsWith(Constants.RETURN_URL);
    boolean shouldLoad = !urlIsReturnURL;
    boolean redirectionFinished = urlIsReturnURL;
    boolean shouldCloseWebPaymentScreen = false;

    if (getCurrentPaymentViewModel().getPaymentOption() instanceof PaymentOption)
      shouldCloseWebPaymentScreen = redirectionFinished && ((PaymentOption) getCurrentPaymentViewModel()
          .getPaymentOption()).getPaymentType() == PaymentType.CARD;

    if (getCurrentPaymentViewModel().getPaymentOption() instanceof CardCredentialsViewModelData)
      shouldCloseWebPaymentScreen = redirectionFinished;

    if(getCurrentPaymentViewModel().getPaymentOption() instanceof RecentSectionViewModel)
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


  public void checkSavedCardPaymentExtraFees(SavedCard savedCard,
                                             PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener) {
    PaymentOption paymentOption = findSavedCardPaymentOption(savedCard);
    checkPaymentExtraFees(paymentOption, paymentOptionsDataListener,PaymentType.SavedCard);

  }

 public void  checkPaymentExtraFees(
                         @NonNull final PaymentOption paymentOption,
                         PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener,
                         PaymentType paymentType) {
   BigDecimal feesAmount = calculateExtraFeesAmount(paymentOption);
   fireExtraFeesDecision(feesAmount, paymentOptionsDataListener, paymentType);
  }

  private void showExtraFees(AmountedCurrency amount,
                             AmountedCurrency extraFeesAmount,
                             PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener,
                             PaymentType paymentType
                             ) {
    showExtraFeesAlert(amount, extraFeesAmount, new DialogManager.DialogResult() {
      @Override
      public void dialogClosed(boolean positiveButtonClicked) {
        if (positiveButtonClicked) {
          if(paymentType==PaymentType.WEB){
            paymentOptionsDataListener
                .fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus.ACCEPT_EXTRA_FEES);
          }
        else if(paymentType==PaymentType.CARD){
            paymentOptionsDataListener
                .fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus.ACCEPT_EXTRA_FEES);
          }
        else if(paymentType==PaymentType.SavedCard){
            paymentOptionsDataListener
                .fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus.ACCEPT_EXTRA_FEES);
          }
        } else {

          if(paymentType==PaymentType.WEB){
            paymentOptionsDataListener
                .fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus.REFUSE_EXTRA_FEES);
          }
          else if(paymentType==PaymentType.CARD){
            paymentOptionsDataListener
                .fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus.REFUSE_EXTRA_FEES);
          }
          else if(paymentType==PaymentType.SavedCard){
            paymentOptionsDataListener
                .fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus.REFUSE_EXTRA_FEES);
          }
        }
      }
    });
  }

  private PaymentOption findSavedCardPaymentOption(@NonNull SavedCard savedCard) {
    PaymentOption paymentOption = PaymentDataManager.getInstance().getPaymentOptionsDataManager()
        .findPaymentOption(savedCard.getPaymentOptionIdentifier());
    if (paymentOption != null)
      System.out.println("saved card payment name : " + paymentOption.getName());
    return paymentOption;
  }

  private void fireExtraFeesDecision(BigDecimal feesAmount,
                                     PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener,
                                     PaymentType paymentType) {
    if (feesAmount.compareTo(BigDecimal.ZERO) == 1)
    {
      IPaymentDataProvider provider = getDataProvider();
      AmountedCurrency amount = provider.getSelectedCurrency();
      AmountedCurrency extraFeesAmount = new AmountedCurrency(amount.getCurrency(), feesAmount);
      showExtraFees(amount,extraFeesAmount,paymentOptionsDataListener,paymentType);
    }

    else
    {
      if (paymentType == PaymentType.WEB)
        paymentOptionsDataListener
            .fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus.NO_EXTRA_FEES);
      else if (paymentType == PaymentType.CARD)
        paymentOptionsDataListener.fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus.NO_EXTRA_FEES);
      else if (paymentType == PaymentType.SavedCard) {
        paymentOptionsDataListener.fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus.NO_EXTRA_FEES);
      }
    }

  }



  public BigDecimal calculateExtraFeesAmount(PaymentOption paymentOption) {
    if (paymentOption != null) {
      IPaymentDataProvider provider = getDataProvider();
      AmountedCurrency amount = provider.getSelectedCurrency();
      ArrayList<ExtraFee> extraFees = paymentOption.getExtraFees();
      if (extraFees == null)
        extraFees = new ArrayList<>();
      ArrayList<AmountedCurrency> supportedCurrencies = provider.getSupportedCurrencies();
      BigDecimal feesAmount = AmountCalculator
          .calculateExtraFeesAmount(extraFees, supportedCurrencies, amount);
      return feesAmount;
    } else
      return BigDecimal.ZERO;
  }

  public String calculateTotalAmount(BigDecimal feesAmount) {
    IPaymentDataProvider provider = getDataProvider();
    AmountedCurrency amount = provider.getSelectedCurrency();
    AmountedCurrency extraFeesAmount = new AmountedCurrency(amount.getCurrency(), feesAmount);
    AmountedCurrency totalAmount = new AmountedCurrency(amount.getCurrency(),
        amount.getAmount().add(extraFeesAmount.getAmount()), amount.getSymbol());
    String totalAmountText = Utils.getFormattedCurrency(totalAmount);
    return totalAmountText;
  }

  void startPaymentProcess(@NonNull final PaymentOptionViewModel paymentOptionModel) {
    forceStartPaymentProcess(paymentOptionModel);
  }

  void startSavedCardPaymentProcess(@NonNull final SavedCard paymentOptionModel,
                                    RecentSectionViewModel recentSectionViewModel){
    forceStartSavedCardPaymentProcess(paymentOptionModel,recentSectionViewModel);
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
    System.out.println(" showExtraFeesAlert .... ");
    AmountedCurrency totalAmount = new AmountedCurrency(amount.getCurrency(),
        amount.getAmount().add(extraFeesAmount.getAmount()), amount.getSymbol());

//    String extraFeesText = CurrencyFormatter.format(extraFeesAmount);
//    String totalAmountText = CurrencyFormatter.format(totalAmount);
    String extraFeesText = Utils.getFormattedCurrency(extraFeesAmount);
    String totalAmountText = Utils.getFormattedCurrency(totalAmount);

    String title = "Confirm extra charges";
    String message = String.format(
        "You will be charged an additional fee of %s for this type of payment, totaling an amount of %s",
        extraFeesText, totalAmountText);

    DialogManager.getInstance().showDialog(title, message, "Confirm", "Cancel", callback);
  }

  private void forceStartPaymentProcess(@NonNull PaymentOptionViewModel paymentOptionModel) {
    System.out.println(
        "paymentOptionModel instance of WebPaymentViewModel :" + (paymentOptionModel instanceof WebPaymentViewModel));
    System.out.println(
        "paymentOptionModel instance of CardCredentialsViewModel :" + (paymentOptionModel instanceof CardCredentialsViewModel));
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
    startPaymentProcessWithCard(card,
        paymentOptionModel.getSelectedCardPaymentOption(),
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

    GoSellAPI.getInstance().createTokenWithEncryptedCard(request, new APIRequestCallback<Token>() {

      @Override
      public void onSuccess(int responseCode, Token serializedResponse) {
        System.out
            .println("startPaymentProcessWithCard >> serializedResponse: " + serializedResponse);
        System.out.println(
            "startPaymentProcessWithCard >> serializedResponse: first six :" + serializedResponse
                .getCard().getFirstSix());
        SourceRequest source = new SourceRequest(serializedResponse);
        callChargeOrAuthorizeAPI(source, paymentOption, serializedResponse.getCard().getFirstSix(),
            saveCard);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {
        System.out.println("GoSellAPI.createToken : " + errorDetails.getErrorBody());
      }
    });
  }


  /////////////////////////////////////////////////////////  Saved Card Payment process ////////////////////////////

  private void forceStartSavedCardPaymentProcess(@NonNull SavedCard savedCard,
                                                 RecentSectionViewModel recentSectionViewModel) {
    setCurrentPaymentViewModel(recentSectionViewModel);
    PaymentOption paymentOption =  findPaymentOption(savedCard);
    CreateTokenSavedCard createTokenSavedCard = new CreateTokenSavedCard(savedCard.getId(),dataProvider.getCustomer().getIdentifier());
    startPaymentProcessWithSavedCardPaymentModel(createTokenSavedCard,paymentOption);
  }



  private void startPaymentProcessWithSavedCardPaymentModel(
      @NonNull CreateTokenSavedCard createTokenSavedCard,PaymentOption paymentOption) {
    CreateTokenWithExistingCardDataRequest request = new  CreateTokenWithExistingCardDataRequest.Builder(createTokenSavedCard).build();
    callSavedCardTokenAPI(request, paymentOption, false);
  }


  private void callSavedCardTokenAPI(@NonNull CreateTokenWithExistingCardDataRequest request,
                            @NonNull final PaymentOption paymentOption,
                            @Nullable final boolean saveCard) {

    GoSellAPI.getInstance().createTokenWithExistingCard(request, new APIRequestCallback<Token>() {

      @Override
      public void onSuccess(int responseCode, Token serializedResponse) {
        System.out.println("startPaymentProcessWithSavedCard >> serializedResponse: " + serializedResponse);
        SourceRequest source = new SourceRequest(serializedResponse);
        callChargeOrAuthorizeAPI(source, paymentOption, serializedResponse.getCard().getFirstSix(), saveCard);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {
        System.out.println("GoSellAPI.callSavedCardTokenAPI : " + errorDetails.getErrorBody());
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
    System.out.println("transactionMode : " + transactionMode);
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
    System.out.println("handleChargeOrAuthorizeResponse >>  chargeOrAuthorize : "+chargeOrAuthorize);
    System.out.println("handleChargeOrAuthorizeResponse >>  error : "+error);
    if (chargeOrAuthorize != null) {

      if (chargeOrAuthorize instanceof Authorize) {

        getProcessListener().didReceiveAuthorize((Authorize) chargeOrAuthorize);
      } else {

        getProcessListener().didReceiveCharge(chargeOrAuthorize);
      }
    } else {
      getProcessListener().didReceiveError(error);
    }
  }


  <T extends Charge> void retrieveChargeOrAuthorizeAPI(T chargeOrAuthorize) {
    APIRequestCallback<T> callBack = new APIRequestCallback<T>() {
      @Override
      public void onSuccess(int responseCode, T serializedResponse) {
        System.out.println(" retrieveChargeOrAuthorizeAPI >>> " + responseCode);
        System.out.println(" retrieveChargeOrAuthorizeAPI >>> " + serializedResponse.getStatus());
        System.out.println(
            " retrieveChargeOrAuthorizeAPI >>> " + serializedResponse.getResponse().getMessage());
        handleChargeOrAuthorizeResponse((Charge) serializedResponse, null);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {

      }
    };
    if (chargeOrAuthorize instanceof Charge)
      GoSellAPI.getInstance()
          .retrieveCharge(chargeOrAuthorize.getId(), (APIRequestCallback<Charge>) callBack);
    else
      GoSellAPI.getInstance()
          .retrieveAuthorize(chargeOrAuthorize.getId(), (APIRequestCallback<Authorize>) callBack);
  }


  public PaymentOption findPaymentOption(SavedCard savedCard) {
    return findSavedCardPaymentOption(savedCard);
  }

  <T extends Charge> void confirmOTPCode(@NonNull T chargeOrAuthorize,
                                         String otpCode) {
    CreateOTPVerificationRequest createOTPVerificationRequest = new CreateOTPVerificationRequest.Builder(AuthenticationType.OTP,otpCode).build();
    APIRequestCallback<T> callBack = new APIRequestCallback<T>() {
      @Override
      public void onSuccess(int responseCode, T serializedResponse) {
        System.out.println(" confirmOTPCode >>> " + responseCode);
        System.out.println(" confirmOTPCode >>> " + serializedResponse.getStatus());
        System.out.println(" confirmOTPCode >>> " + serializedResponse.getResponse().getMessage());
        handleChargeOrAuthorizeResponse((Charge) serializedResponse, null);
      }

      @Override
      public void onFailure(GoSellError errorDetails) {
        System.out.println(" confirmOTPCode >>> error : "+ errorDetails.getErrorBody());
        //otpListener.otpCodeAuthenticated();
        handleChargeOrAuthorizeResponse(null,errorDetails);
      }
    };

    if (chargeOrAuthorize instanceof Charge)
      GoSellAPI.getInstance()
          .authenticate(chargeOrAuthorize.getId(),createOTPVerificationRequest, (APIRequestCallback<Charge>) callBack);
//    else
//      GoSellAPI.getInstance()
//          .retrieveAuthorize(chargeOrAuthorize.getId(), (APIRequestCallback<Authorize>) callBack);

  }

  <T extends Charge> void resendOTPCode(@NonNull T chargeOrAuthorize) {

      APIRequestCallback<T> callBack = new APIRequestCallback<T>() {
        @Override
        public void onSuccess(int responseCode, T serializedResponse) {
          System.out.println(" resendOTPCode >>> " + responseCode);
          System.out.println(" resendOTPCode >>> " + serializedResponse.getStatus());
          System.out.println(" resendOTPCode >>> " + serializedResponse.getResponse().getMessage());
          System.out.println(" resendOTPCode >>> " + serializedResponse.getAuthenticate().getValue());
          handleChargeOrAuthorizeResponse((Charge) serializedResponse, null);
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
          System.out.println(" confirmOTPCode >>> error : "+ errorDetails.getErrorBody());
          handleChargeOrAuthorizeResponse(null,errorDetails);
        }
      };

      if (chargeOrAuthorize instanceof Charge)
        GoSellAPI.getInstance()
            .request_authenticate(chargeOrAuthorize.getId(), (APIRequestCallback<Charge>) callBack);
//    else
//      GoSellAPI.getInstance()
//          .retrieveAuthorize(chargeOrAuthorize.getId(), (APIRequestCallback<Authorize>) callBack);

  }
}
