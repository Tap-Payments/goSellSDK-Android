package company.tap.gosellapi.internal.data_managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.internal.api.models.Merchant;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destination;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.open.interfaces.PaymentDataSource;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * The type Payment data manager.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PaymentDataManager {

  @Nullable private PaymentDataSource externalDataSource;
  @NonNull private IPaymentDataProvider dataProvider = new PaymentDataProvider();
  @NonNull private PaymentProcessListener processListener = new PaymentProcessListener();
  @NonNull private CardDeleteListener cardDeletListener = new CardDeleteListener();

  @NonNull private PaymentProcessManager paymentProcessManager = new PaymentProcessManager(
      getPaymentDataProvider(),
          getProcessListener(),
          getCardDeleteListener()
  );

  private SDKSettings SDKSettings;
  private PaymentOptionsRequest paymentOptionsRequest;
  private PaymentOptionsDataManager paymentOptionsDataManager;
  private BINLookupResponse binLookupResponse;
  private Charge chargeOrAuthorize = null;

  private PaymentDataManager() {
  }

    /**
     * Calculate total amount string.
     *
     * @param feesAmount the fees amount
     * @return the string
     */
    public String calculateTotalAmount(BigDecimal feesAmount) {
    return getPaymentProcessManager().calculateTotalAmount(feesAmount);
  }

    /**
     * Check saved card payment extra fees.
     *
     * @param savedCard                  the saved card
     * @param paymentOptionsDataListener the payment options data listener
     */
    public void checkSavedCardPaymentExtraFees(SavedCard savedCard,
                                             PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener) {
    getPaymentProcessManager()
        .checkSavedCardPaymentExtraFees(savedCard, paymentOptionsDataListener);
  }

    /**
     * Find saved card payment option payment option.
     *
     * @param savedCard the saved card
     * @return the payment option
     */
    public PaymentOption findSavedCardPaymentOption(SavedCard savedCard) {
    return getPaymentProcessManager().findPaymentOption(savedCard);
  }

    /**
     * Confirm otp code.
     *
     * @param otpCode the otp code
     */
    public void confirmOTPCode(String otpCode) {
    if(getChargeOrAuthorize()!=null ) {

      if(getChargeOrAuthorize() instanceof  Authorize)
        getPaymentProcessManager().confirmAuthorizeOTPCode((Authorize) getChargeOrAuthorize(), otpCode);

      else
        getPaymentProcessManager().confirmChargeOTPCode(getChargeOrAuthorize(), otpCode);
    }
  }

    /**
     * Sets charge or authorize.
     *
     * @param charge the charge
     */
    public void setChargeOrAuthorize(Charge charge) {
    this.chargeOrAuthorize = charge;
  }

    /**
     * Gets charge or authorize.
     *
     * @return the charge or authorize
     */
    public Charge getChargeOrAuthorize() {
    return chargeOrAuthorize;
  }

    /**
     * Resend otp code.
     */
    public void resendOTPCode() {
    if(getChargeOrAuthorize()!= null) {
      if (getChargeOrAuthorize() instanceof Authorize)
            getPaymentProcessManager().resendAuthorizeOTPCode((Authorize) getChargeOrAuthorize());
      else
            getPaymentProcessManager().resendChargeOTPCode((Charge) getChargeOrAuthorize());
    }
  }

    /**
     * Gets available payment options card brands.
     *
     * @return the available payment options card brands
     */
    public ArrayList<CardBrand> getAvailablePaymentOptionsCardBrands() {
    PaymentOptionsResponse paymentOptionsResponse = getPaymentOptionsDataManager()
        .getPaymentOptionsResponse();
    if (paymentOptionsResponse != null)
      return getAvailableCardBrandsFromPaymentOptions(paymentOptionsResponse.getPaymentOptions());
    else
      return null;
  }

  private ArrayList<CardBrand> getAvailableCardBrandsFromPaymentOptions(
      @NonNull ArrayList<PaymentOption> paymentOptions) {
    ArrayList<CardBrand> cardBrands = new ArrayList<>();
    for (PaymentOption paymentOption : paymentOptions) {
      cardBrands.add(paymentOption.getBrand());
    }
    if (cardBrands.size() == 0) return null;
    return cardBrands;
  }

    /**
     * Fire card saved before listener.
     */
    public void fireCardSavedBeforeListener() {
    getProcessListener().didCardSavedBefore();
  }

  /**
   * Fire Card Tokenization process completed with token
   */

  public void fireCardTokenizationProcessCompleted(Token token){
    getProcessListener().fireCardTokenizationProcessCompleted(token);
  }

  /////////////////////////////////////////    ########### Start of Singleton section ##################

  /**
   * Here we will use inner class to create a singleton object of PaymentDataManager
   * Inner class singleton approach introduced by Bill Pugh >>  singleton approaches :
   * - Eager initialization
   * - Static block initialization
   * - Lazy load initialization
   * - thread safe initialization
   * in this approach create the Singleton class using a inner static helper class
   * When the singleton class is loaded, SingletonCreationAdmin class is not loaded into memory
   * and only when someone calls the getInstance method.
   */
  private static class SingletonCreationAdmin {
    private static final PaymentDataManager INSTANCE = new PaymentDataManager();
  }

    /**
     * Singleton getInstance method
     *
     * @return the instance
     */
    public static PaymentDataManager getInstance() {
    return SingletonCreationAdmin.INSTANCE;
  }
  /////////////////////////////////////////  ########### End of Singleton section ##################

    /**
     * Gets external data source.
     *
     * @return the external data source
     */
    public PaymentDataSource getExternalDataSource() {
    return externalDataSource;
  }

    /**
     * Sets external data source.
     *
     * @param externalDataSource the external data source
     */
    public void setExternalDataSource(PaymentDataSource externalDataSource) {
    this.externalDataSource = externalDataSource;
  }

    /**
     * Gets sdk settings.
     *
     * @return the sdk settings
     */
    public SDKSettings getSDKSettings() {
    return SDKSettings;
  }

    /**
     * Sets sdk settings.
     *
     * @param SDKSettings the sdk settings
     */
    public void setSDKSettings(SDKSettings SDKSettings) {
    this.SDKSettings = SDKSettings;
  }

    /**
     * Gets payment options request.
     *
     * @return the payment options request
     */
    public PaymentOptionsRequest getPaymentOptionsRequest() {
    return paymentOptionsRequest;
  }

    /**
     * Gets bin lookup response.
     *
     * @return the bin lookup response
     */
    public BINLookupResponse getBinLookupResponse() {
    return binLookupResponse;
  }

    /**
     * Decision for web payment url web payment url decision.
     *
     * @param url the url
     * @return the web payment url decision
     */
    public WebPaymentURLDecision decisionForWebPaymentURL(String url) {

    return getPaymentProcessManager().decisionForWebPaymentURL(url);
  }

    /**
     * Sets payment options request.
     *
     * @param paymentOptionsRequest the payment options request
     */
    public void setPaymentOptionsRequest(PaymentOptionsRequest paymentOptionsRequest) {
    this.paymentOptionsRequest = paymentOptionsRequest;
  }

    /**
     * Sets bin lookup response.
     *
     * @param binLookupResponse the bin lookup response
     */
    public void setBinLookupResponse(BINLookupResponse binLookupResponse) {
    this.binLookupResponse = binLookupResponse;

  }

    /**
     * Create payment options data manager.
     *
     * @param paymentOptionsResponse the payment options response
     */
    public void createPaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {
    paymentOptionsDataManager = new PaymentOptionsDataManager(paymentOptionsResponse);
  }

    /**
     * Gets payment options data manager.
     *
     * @param dataListener the data listener
     * @return the payment options data manager
     */
    public PaymentOptionsDataManager getPaymentOptionsDataManager(
      PaymentOptionsDataManager.PaymentOptionsDataListener dataListener) {

    if (paymentOptionsDataManager != null)
      return paymentOptionsDataManager.setListener(dataListener);

    paymentOptionsDataManager = getPaymentOptionsDataManager();

    if (paymentOptionsDataManager == null)
      return null;

    return paymentOptionsDataManager.setListener(dataListener);
  }

    /**
     * Gets payment options data manager.
     *
     * @return the payment options data manager
     */
    public PaymentOptionsDataManager getPaymentOptionsDataManager() {
    return paymentOptionsDataManager;
  }


    /**
     * Retrieve charge or authorize or save card api.
     *
     * @param chargeOrAuthorize the charge or authorize
     */
    public void retrieveChargeOrAuthorizeOrSaveCardAPI(Charge chargeOrAuthorize) {
    getPaymentProcessManager().retrieveChargeOrAuthorizeOrSaveCardAPI(chargeOrAuthorize);
  }


    /**
     * Check web payment extra fees.
     *
     * @param model                      the model
     * @param paymentOptionsDataListener the payment options data listener
     */
    public void checkWebPaymentExtraFees(WebPaymentViewModel model,
                                       PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener) {
    getPaymentProcessManager()
        .checkPaymentExtraFees(model.getPaymentOption(), paymentOptionsDataListener,
            PaymentType.WEB);
  }

    /**
     * Check card payment extra fees.
     *
     * @param model                      the model
     * @param paymentOptionsDataListener the payment options data listener
     */
    public void checkCardPaymentExtraFees(CardCredentialsViewModel model,
                                        PaymentOptionsDataManager.PaymentOptionsDataListener paymentOptionsDataListener) {
    getPaymentProcessManager()
        .checkPaymentExtraFees(model.getSelectedCardPaymentOption(), paymentOptionsDataListener,
            PaymentType.CARD);
  }

    /**
     * Calculate card extra fees big decimal.
     *
     * @param paymentOption the payment option
     * @return the big decimal
     */
    public BigDecimal calculateCardExtraFees(PaymentOption paymentOption) {
    return getPaymentProcessManager().calculateExtraFeesAmount(paymentOption);
  }

    /**
     * Initiate payment.
     *
     * @param model    the model
     * @param listener the listener
     */
    public void initiatePayment(PaymentOptionViewModel model, IPaymentProcessListener listener) {
    getProcessListener().addListener(listener);
    getPaymentProcessManager().startPaymentProcess(model);
  }

  /**
   * Init card tokenization
   */
  public void initCardTokenizationPayment(PaymentOptionViewModel model, IPaymentProcessListener listener){
      getProcessListener().addListener(listener);
      getPaymentProcessManager().startCardTokenization(model);
  }
    /**
     * Initiate saved card payment.
     *
     * @param savedCard              the saved card
     * @param recentSectionViewModel the recent section view model
     * @param listener               the listener
     */
    public void initiateSavedCardPayment(SavedCard savedCard,
                                       RecentSectionViewModel recentSectionViewModel,
                                       IPaymentProcessListener listener) {
    getProcessListener().addListener(listener);
    getPaymentProcessManager().startSavedCardPaymentProcess(savedCard, recentSectionViewModel);
  }

  /**
   * Delete card
   * @param customerID
   * @param cardId
   * @param listener
   */
  public void deleteCard(@NonNull String customerID,@NonNull String cardId,
                         company.tap.gosellapi.internal.interfaces.ICardDeleteListener listener){
      getCardDeleteListener().addListener(listener);
      getPaymentProcessManager().deleteCard(customerID,cardId);
  }

    /**
     * The type Web payment url decision.
     */
    public final class WebPaymentURLDecision {

    @NonNull private boolean shouldLoad;
    @NonNull private boolean shouldCloseWebPaymentScreen;
    @NonNull private boolean redirectionFinished;

    @Nullable private String tapID;

        /**
         * Should load boolean.
         *
         * @return the boolean
         */
        @NonNull
    public boolean shouldLoad() {

      return shouldLoad;
    }

        /**
         * Should close web payment screen boolean.
         *
         * @return the boolean
         */
        @NonNull
    boolean shouldCloseWebPaymentScreen() {

      return shouldCloseWebPaymentScreen;
    }

        /**
         * Redirection finished boolean.
         *
         * @return the boolean
         */
        @NonNull
    boolean redirectionFinished() {

      return redirectionFinished;
    }

        /**
         * Gets tap id.
         *
         * @return the tap id
         */
        @Nullable
    String getTapID() {

      return tapID;
    }

        /**
         * Instantiates a new Web payment url decision.
         *
         * @param shouldLoad                  the should load
         * @param shouldCloseWebPaymentScreen the should close web payment screen
         * @param redirectionFinished         the redirection finished
         * @param tapID                       the tap id
         */
        WebPaymentURLDecision(@NonNull boolean shouldLoad, @NonNull boolean shouldCloseWebPaymentScreen,
                          @NonNull boolean redirectionFinished, @Nullable String tapID) {

      this.shouldLoad = shouldLoad;
      this.shouldCloseWebPaymentScreen = shouldCloseWebPaymentScreen;
      this.redirectionFinished = redirectionFinished;
      this.tapID = tapID;
    }
  }

  private class PaymentDataProvider implements IPaymentDataProvider {

    @NonNull
    @Override
    public AmountedCurrency getSelectedCurrency() {

      return getPaymentOptionsDataManager().getSelectedCurrency();
    }

    @NonNull
    @Override
    public ArrayList<AmountedCurrency> getSupportedCurrencies() {

      return getPaymentOptionsDataManager().getPaymentOptionsResponse().getSupportedCurrencies();
    }

    @NonNull
    @Override
    public String getPaymentOptionsOrderID() {

      return getPaymentOptionsDataManager().getPaymentOptionsResponse().getOrderID();
    }

    @Nullable
    @Override
    public String getPostURL() {

      return getExternalDataSource().getPostURL();
    }

    @NonNull
    @Override
    public Customer getCustomer() {

      return getExternalDataSource().getCustomer();
    }

    @Nullable
    @Override
    public String getPaymentDescription() {

      return getExternalDataSource().getPaymentDescription();
    }

    @Nullable
    @Override
    public HashMap<String, String> getPaymentMetadata() {

      return getExternalDataSource().getPaymentMetadata();
    }

    @Nullable
    @Override
    public Reference getPaymentReference() {

      return getExternalDataSource().getPaymentReference();
    }

    @Nullable
    @Override
    public String getPaymentStatementDescriptor() {

      return getExternalDataSource().getPaymentStatementDescriptor();
    }

    @NonNull
    @Override
    public boolean getRequires3DSecure() {
/**
 * Stop checking SDKsettings in SDK to decide if  the payment will be 3DSecure or not
 * instead always send value configured by the Merchant. 
 */
//      boolean merchantRequires3DSecure = getSDKSettings().getData().getPermissions().contains(Permission.THREEDSECURE_DISABLED);
//      return merchantRequires3DSecure || getExternalDataSource().getRequires3DSecure();
      return  getExternalDataSource().getRequires3DSecure();
    }

    @Nullable
    @Override
    public Receipt getReceiptSettings() {

      return getExternalDataSource().getReceiptSettings();
    }

    @NonNull
    @Override
    public TransactionMode getTransactionMode() {

      TransactionMode transactionMode = getExternalDataSource().getTransactionMode();
      if (transactionMode == null) {
        transactionMode = TransactionMode.PURCHASE;
      }

      return transactionMode;
    }

    @NonNull
    @Override
    public AuthorizeAction getAuthorizeAction() {

      AuthorizeAction authorizeAction = getExternalDataSource().getAuthorizeAction();
      if (authorizeAction == null) {
        authorizeAction = AuthorizeAction.getDefault();
      }

      return authorizeAction;
    }

      @Nullable
      @Override
      public Destinations getDestination(){
          Destinations destinations = getExternalDataSource().getDestination();
          return destinations;
      }

    @Nullable
    @Override
    public Merchant getMerchant() {
      Merchant merchant = getExternalDataSource().getMerchant();
      return merchant;
    }
  }

  private class PaymentProcessListener implements IPaymentProcessListener {

    @Override
    public void didReceiveCharge(Charge charge) {
      for (Iterator i = getListeners().iterator(); i.hasNext();) {
        try{
                IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
                listener.didReceiveCharge(charge);
        }catch (Exception e){
          break;
        }
      }
    }

    @Override
    public void didReceiveAuthorize(Authorize authorize) {

        for (Iterator i = getListeners().iterator(); i.hasNext();) {
          try{
            IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
            listener.didReceiveAuthorize(authorize);
          }catch (Exception e){
            break;
          }
        }
    }

    @Override
    public void didReceiveSaveCard(SaveCard saveCard) {

        for (Iterator i = getListeners().iterator(); i.hasNext();) {
          try{
            IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
            listener.didReceiveSaveCard(saveCard);
          }catch (Exception e){
            break;
          }
        }

    }

    @Override
    public void didReceiveError(GoSellError error) {

        for (Iterator i = getListeners().iterator(); i.hasNext();) {
          try{
            IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
            listener.didReceiveError(error);
          }catch (Exception e){
            break;
          }
        }
    }


    @Override
    public void didCardSavedBefore() {

        for (Iterator i = getListeners().iterator(); i.hasNext();) {
           try{
            IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
            listener.didCardSavedBefore();
           }catch (Exception e){
             break;
           }
        }

    }

    @Override
    public void fireCardTokenizationProcessCompleted(Token token) {

        for (Iterator i = getListeners().iterator(); i.hasNext();) {
          try{
            IPaymentProcessListener listener = (IPaymentProcessListener) i.next();
            listener.fireCardTokenizationProcessCompleted(token);
          }catch (Exception e){
            break;
          }
        }
    }


    private void addListener(IPaymentProcessListener listener) {

      getListeners().add(listener);
    }

    private void removeListener(IPaymentProcessListener listener) {

      getListeners().remove(listener);
    }

    @NonNull private ArrayList<IPaymentProcessListener> listeners;

    private PaymentProcessListener() {

      this.listeners = new ArrayList<>();
    }

    private ArrayList<IPaymentProcessListener> getListeners() {

      return listeners;
    }
  }


  private class CardDeleteListener implements company.tap.gosellapi.internal.interfaces.ICardDeleteListener {

    @NonNull private company.tap.gosellapi.internal.interfaces.ICardDeleteListener listener;

    private void addListener(company.tap.gosellapi.internal.interfaces.ICardDeleteListener _listener) {

     listener = _listener;
    }

    private company.tap.gosellapi.internal.interfaces.ICardDeleteListener getListeners() {

      return listener;
    }

    @Override
    public void didCardDeleted(DeleteCardResponse deleteCardResponse) {
       if(listener!=null && (listener instanceof GoSellPaymentActivity)) {
            listener.didCardDeleted(deleteCardResponse);
        }
      }

  }


  @NonNull
  private IPaymentDataProvider getPaymentDataProvider() {

    return dataProvider;
  }

  @NonNull
  private PaymentProcessListener getProcessListener() {

    return processListener;
  }

  @NonNull
  private CardDeleteListener getCardDeleteListener(){
      return cardDeletListener;
  }


    /**
     * Gets payment process manager.
     *
     * @return the payment process manager
     */
    @NonNull
  public PaymentProcessManager getPaymentProcessManager() {

    return paymentProcessManager;
  }

  /**
   * Expose this method to clear payment process listeners
   * after SDK finishes his work and becoming ready to fire merchant delegate method
   * this avoid having more than one instance of payment process listener and hence call delegate
   * method multiple times.
   */
  public void clearPaymentProcessListeners(){
    if(getProcessListener()!=null)
    {
      if(getProcessListener().getListeners()!=null)
      {
        getProcessListener().getListeners().clear();
      }
    }
  }
}
