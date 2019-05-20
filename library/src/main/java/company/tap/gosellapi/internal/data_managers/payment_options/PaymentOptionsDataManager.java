package company.tap.gosellapi.internal.data_managers.payment_options;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.enums.ExtraFeesStatus;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.internal.api.interfaces.CurrenciesSupport;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.utils.PaymentOptionsDataManagerUtils;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CurrencyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.EmptyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.GroupViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CurrencyViewModelData;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.internal.utils.CompoundFilter;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.internal.viewholders.GroupViewHolder;
import company.tap.gosellapi.open.enums.TransactionMode;
import io.card.payment.CreditCard;

/**
 * The type Payment options data manager.
 */
public class PaymentOptionsDataManager {

  private int availableHeight;
  private int saveCardHeight;
  private int cardSwitchHeight;
  private AmountedCurrency selectedCurrency;

    /**
     * Gets payment options response.
     *
     * @return the payment options response
     */
    public PaymentOptionsResponse getPaymentOptionsResponse() {
    return paymentOptionsResponse;
  }

    /**
     * Find payment option payment option.
     *
     * @param paymentOptionIdentifier the payment option identifier
     * @return the payment option
     */
    public PaymentOption findPaymentOption(String paymentOptionIdentifier) {
    for(PaymentOption paymentOption: paymentOptionsResponse.getPaymentOptions()){
      if((paymentOptionIdentifier).equals(paymentOption.getId())){
        return paymentOption;
      }
    }
    return null;
  }

    /**
     * Filter view models with amounted currency.
     *
     * @param amountedCurrency the amounted currency
     */
    public void filterViewModelsWithAmountedCurrency(AmountedCurrency amountedCurrency) {
    //getModelsHandler().generateViewModels();
    getModelsHandler().filterViewModels(amountedCurrency.getCurrency());
  }

  public void disablePayButton() {
      listener.disablePayButton();
  }

  /**
     * The interface Payment options data listener.
     */
//outer interface (for fragment, containing recyclerView)
  public interface PaymentOptionsDataListener {

        /**
         * Start currency selection.
         *
         * @param currencies       the currencies
         * @param selectedCurrency the selected currency
         */
        void startCurrencySelection(ArrayList<AmountedCurrency> currencies,
                                AmountedCurrency selectedCurrency);

        /**
         * Update pay button with saved card extra fees.
         *
         * @param recentItem             the recent item
         * @param recentSectionViewModel the recent section view model
         */
        void updatePayButtonWithSavedCardExtraFees(SavedCard recentItem,RecentSectionViewModel recentSectionViewModel);

        /**
         * Start web payment.
         *
         * @param model the model
         */
        void startWebPayment(WebPaymentViewModel model);

        /**
         * Start scan card.
         */
        void startScanCard();

        /**
         * Card details filled.
         *
         * @param isFilled                 the is filled
         * @param cardCredentialsViewModel the card credentials view model
         */
        void cardDetailsFilled(boolean isFilled, CardCredentialsViewModel cardCredentialsViewModel);

        /**
         * Save card switch clicked.
         *
         * @param isChecked             the is checked
         * @param saveCardBlockPosition the save card block position
         */
        void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition);

        /**
         * Address on card clicked.
         */
        void addressOnCardClicked();

        /**
         * Card expiration date clicked.
         *
         * @param model the model
         */
        void cardExpirationDateClicked(CardCredentialsViewModel model);

        /**
         * Bin number entered.
         *
         * @param binNumber the bin number
         */
        void binNumberEntered(String binNumber);

        /**
         * Fire web payment extra fees user decision.
         *
         * @param userChoice the user choice
         */
        void fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

        /**
         * Fire card payment extra fees user decision.
         *
         * @param userChoice the user choice
         */
        void fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

        /**
         * Fire saved card payment extra fees user decision.
         *
         * @param userChoice the user choice
         */
        void fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

        /**
         * Update pay button with extra fees.
         *
         * @param paymentOption the payment option
         */
        void updatePayButtonWithExtraFees(PaymentOption paymentOption);


      /**
       * User clicks on delete card
       * @param cardId to be deleted
       */
      void savedCardClickedForDeletion(String cardId);

    void disablePayButton();
  }

  private PaymentOptionsDataListener listener;

  private PaymentOptionsResponse paymentOptionsResponse;
  private ViewModelsHandler modelsHandler;

  private ArrayList<PaymentOptionViewModel> viewModels;
  private ArrayList<PaymentOptionViewModel> visibleViewModels;
  private String lastFilteredCurrency;


    /**
     * Gets models handler.
     *
     * @return the models handler
     */
    public ViewModelsHandler getModelsHandler() {
    return modelsHandler;
  }

  private ArrayList<PaymentOptionViewModel> getViewModels() {

    return viewModels;
  }

  private ArrayList<PaymentOptionViewModel> getVisibleViewModels() {

    return visibleViewModels;
  }

  private int focusedPosition = Constants.NO_FOCUS;

    /**
     * Instantiates a new Payment options data manager.
     *
     * @param paymentOptionsResponse the payment options response
     */
    public PaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {

    this.paymentOptionsResponse = paymentOptionsResponse;
    this.modelsHandler = new ViewModelsHandler();
    getModelsHandler().generateViewModels();
    getModelsHandler().filterViewModels(getPaymentOptionsResponse().getCurrency());
  }

    /**
     * Gets size.
     *
     * @return the size
     */
//region data for adapter
  public int getSize() {
    return getVisibleViewModels().size();
  }

    /**
     * Gets selected currency.
     *
     * @return the selected currency
     */
    public AmountedCurrency getSelectedCurrency() {

    if (this.selectedCurrency == null) {
      this.selectedCurrency = getTransactionCurrency();
    }
    return selectedCurrency;
  }

  private AmountedCurrency getTransactionCurrency() {

    String currencyCode = paymentOptionsResponse.getCurrency();

    for (AmountedCurrency amountedCurrency : paymentOptionsResponse.getSupportedCurrencies()) {

      if (amountedCurrency.getCurrency().equals(currencyCode)) return amountedCurrency;
    }

    return null;
  }

    /**
     * Sets selected currency.
     *
     * @param selectedCurrency the selected currency
     */
    public void setSelectedCurrency(AmountedCurrency selectedCurrency) {

    this.selectedCurrency = selectedCurrency;

    CurrencyViewModel currencyViewModel = getModelsHandler().findCurrencyModel();
    CurrencyViewModelData currencyViewModelData = currencyViewModel.getData();
    currencyViewModelData.setSelectedCurrency(selectedCurrency);
    currencyViewModel.setData(currencyViewModelData);
  }

    /**
     * Gets item view type.
     *
     * @param position the position
     * @return the item view type
     */
    public int getItemViewType(int position) {

    return getViewModel(position).getType().getViewType();
  }

    /**
     * Gets view model.
     *
     * @param position the position
     * @return the view model
     */
    public PaymentOptionViewModel getViewModel(int position) {

    return getVisibleViewModels().get(position);
  }

    /**
     * Sets listener.
     *
     * @param listener the listener
     * @return the listener
     */
    public PaymentOptionsDataManager setListener(PaymentOptionsDataListener listener) {

    this.listener = listener;
    return this;
  }
  //endregion

    /**
     * Currency holder clicked.
     *
     * @param position the position
     */
//region callback actions from child viewModels
  public void currencyHolderClicked(int position) {
    ArrayList<AmountedCurrency> currencies = getPaymentOptionsResponse().getSupportedCurrencies();

    CurrencyViewModelData currencyViewModelData = ((CurrencyViewModel) viewModels.get(position))
        .getData();
    listener.startCurrencySelection(currencies, currencyViewModelData.getSelectedCurrency());
  }

    /**
     * Recent payment item clicked.
     *
     * @param position               the position
     * @param recentItem             the recent item
     * @param recentSectionViewModel the recent section view model
     */
    public void recentPaymentItemClicked(int position, SavedCard recentItem,RecentSectionViewModel recentSectionViewModel) {
    setRecentPaymentFocusedCard(position);
    CardCredentialsViewModel model = getModelsHandler().findCardPaymentModel();
    listener.updatePayButtonWithSavedCardExtraFees(recentItem,recentSectionViewModel);
  }

  /**
   * this method will be called if user clicks on edit saved card
   * @param groupViewHolderListener
   */
  public void editItemClicked(GroupViewHolder groupViewHolderListener){
    RecentSectionViewModel recentSectionViewModel = getModelsHandler().findSavedCardsModel();
    if (recentSectionViewModel == null) return;

    recentSectionViewModel.shakeAllCards(groupViewHolderListener);

  }

  /**
   * this method will be called if user clicks on cancel saved card
   */
  public void cancelItemClicked(){
    RecentSectionViewModel recentSectionViewModel = getModelsHandler().findSavedCardsModel();
    if (recentSectionViewModel == null) return;

    recentSectionViewModel.stopShakingAllCards();

  }

    /**
     * Web payment system view holder clicked.
     *
     * @param sender   the sender
     * @param position the position
     */
    public void webPaymentSystemViewHolderClicked(WebPaymentViewModel sender, int position) {
    setFocused(position);
    listener.startWebPayment(sender);
  }

    /**
     * Card scanner button clicked.
     */
    public void cardScannerButtonClicked() {
    listener.startScanCard();
  }

    /**
     * Save card switch checked changed.
     *
     * @param isChecked             the is checked
     * @param saveCardBlockPosition the save card block position
     */
    public void saveCardSwitchCheckedChanged(boolean isChecked, int saveCardBlockPosition) {
    if (isChecked) {
      listener.saveCardSwitchClicked(isChecked, saveCardBlockPosition);
    }
  }

    /**
     * Card details filled.
     *
     * @param isFilled                 the is filled
     * @param cardCredentialsViewModel the card credentials view model
     */
    public void cardDetailsFilled(boolean isFilled,
                                CardCredentialsViewModel cardCredentialsViewModel) {

    listener.cardDetailsFilled(isFilled, cardCredentialsViewModel);
  }

    /**
     * Address on card clicked.
     */
    public void addressOnCardClicked() {
    listener.addressOnCardClicked();
  }

    /**
     * Bin number entered.
     *
     * @param binNumber the bin number
     */
    public void binNumberEntered(String binNumber) {

    listener.binNumberEntered(binNumber);

  }

    /**
     * Card expiration date clicked.
     */
    public void cardExpirationDateClicked() {

    CardCredentialsViewModel model = getModelsHandler().findCardPaymentModel();
    if (model != null) {

      listener.cardExpirationDateClicked(model);
    }
  }

  //endregion

  //region update actions (from activity mainly)

    /**
     * Sets available height.
     *
     * @param availableHeight the available height
     */
    public void setAvailableHeight(int availableHeight) {

    this.availableHeight = availableHeight;
    applyEmptyHolderHeight();
  }

    /**
     * Sets card switch height.
     *
     * @param cardSwitchHeight the card switch height
     */
    public void setCardSwitchHeight(int cardSwitchHeight) {

    this.cardSwitchHeight = cardSwitchHeight;
    applyEmptyHolderHeight();
  }

    /**
     * Sets save card height.
     *
     * @param neededHeight the needed height
     */
    public void setSaveCardHeight(int neededHeight) {

    saveCardHeight = neededHeight;
    applyEmptyHolderHeight();
  }

  private void applyEmptyHolderHeight() {

//        if ( availableHeight == 0 || saveCardHeight == 0 || cardSwitchHeight == 0 ) { return; }
//
//        int emptyHolderHeight = availableHeight - saveCardHeight - cardSwitchHeight;
//
//
//
//
//            EmptyViewModel emptyViewModel = getEmptyViewModel();
//            if (emptyViewModel != null) {
//                emptyViewModel.setSpecifiedHeight(emptyHolderHeight > 0 ? emptyHolderHeight : 0);
//            }
  }

    /**
     * Currency selected by user.
     *
     * @param selectedCurrency the selected currency
     */
    public void currencySelectedByUser(AmountedCurrency selectedCurrency) {

    this.selectedCurrency = selectedCurrency;
    filterViewModelsWithAmountedCurrency(selectedCurrency);
  }


    /**
     * Update currency section.
     */
    public void updateCurrencySection(){
    //update currency section
    CurrencyViewModel currencyViewModel = getModelsHandler().findCurrencyModel();
    if (currencyViewModel == null) return;

    CurrencyViewModelData currencyViewModelData = currencyViewModel.getData();
    currencyViewModelData.setSelectedCurrency(selectedCurrency);
    currencyViewModel.updateData();
  }

  /**
   * Update saved Cards after deletion
   * @param cardId
   */
  public void updateSavedCards(String cardId){

    RecentSectionViewModel recentSectionViewModel =  getModelsHandler().findSavedCardsModel();

   if(recentSectionViewModel == null)return;

    if(recentSectionViewModel.getData()== null) return;

    boolean isCardDeleted = false;

    for (Iterator i = recentSectionViewModel.getData().iterator(); i.hasNext();) {
      SavedCard sCard = (SavedCard) i.next();
      if (sCard.getId().equals(cardId)) {
       i.remove();
        isCardDeleted=true;
      }
    }


    if(recentSectionViewModel.getData().size()==0)
      clearPaymentOptionsResponseCards();

      if(isCardDeleted)  recentSectionViewModel.updateData();
  }

  public boolean isCardSavedBefore(@NonNull  String fingerprint){

    RecentSectionViewModel recentSectionViewModel =  getModelsHandler().findSavedCardsModel();

    if(recentSectionViewModel == null)return false;

    if(recentSectionViewModel.getData()== null) return false;
    for(Iterator i = recentSectionViewModel.getData().iterator(); i.hasNext();){
      SavedCard sCard = (SavedCard) i.next();
      if(sCard.getFingerprint().equals(fingerprint)) return true;
    }
    return false;
  }

  private void clearPaymentOptionsResponseCards(){
    getPaymentOptionsResponse().getCards().clear();
    getModelsHandler().filterViewModels(getPaymentOptionsResponse().getCurrency());
  }



  /**
     * Card expiration date selected.
     *
     * @param month the month
     * @param year  the year
     */
    public void cardExpirationDateSelected(String month, String year) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    cardCredentialsViewModel.setExpirationMonth(month);
    cardCredentialsViewModel.setExpirationYear(year);

    cardCredentialsViewModel.updateData();
  }

    /**
     * Card scanned.
     *
     * @param card the card
     */
    public void cardScanned(CreditCard card) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    String cardNumber = card.cardNumber;
    if (cardNumber != null && !cardNumber.isEmpty()) {

      cardCredentialsViewModel.setCardNumber(cardNumber);
    }

    int expirationMonth = card.expiryMonth;
    int expirationYear = card.expiryYear;
    if (expirationMonth != 0 && expirationYear != 0) {

      cardCredentialsViewModel.setExpirationYear(String.valueOf(expirationYear));
      cardCredentialsViewModel.setExpirationMonth(String.valueOf(expirationMonth));
    }

    String cvv = card.cvv;
    if (cvv != null && !cvv.isEmpty()) {

      cardCredentialsViewModel.setCVVnumber(cvv);
    }

    String cardholderName = card.cardholderName;
    if (cardholderName != null && !cardholderName.isEmpty()) {

      cardCredentialsViewModel.setNameOnCard(card.cardholderName);
    }

    cardCredentialsViewModel.updateData();
  }


    /**
     * Update pay button with extra fees.
     *
     * @param paymentOption the payment option
     */
    public void updatePayButtonWithExtraFees(PaymentOption paymentOption) {
         listener.updatePayButtonWithExtraFees(paymentOption);
  }
  
  
  public void checkShakingStatus(){
    RecentSectionViewModel recentSectionViewModel = getModelsHandler().findSavedCardsModel();
    if (recentSectionViewModel == null) return;

    recentSectionViewModel.stopShakingAllCards();
  }


  public void deleteCard(@NonNull String cardId){
    setRecentPaymentFocusedCard(Constants.NO_FOCUS);
      listener.savedCardClickedForDeletion(cardId);
  }
    /**
     * Show address on card cell.
     *
     * @param isShow the is show
     */
    public void showAddressOnCardCell(boolean isShow) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    cardCredentialsViewModel.showAddressOnCardCell(isShow);
    //cardCredentialsViewModel.updateData();
  }

    /**
     * Set current bin data.
     *
     * @param currentBINData the current bin data
     */
    public void setCurrentBINData(
    BINLookupResponse currentBINData){
    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    cardCredentialsViewModel.setCurrentBINData(currentBINData);

  }

  //endregion

  //region focus interaction between holders
  private void setFocused(int position) {
    if (focusedPosition != Constants.NO_FOCUS) {
      viewModels.get(focusedPosition).setViewFocused(false);
    }

    focusedPosition = position;
    viewModels.get(focusedPosition).setViewFocused(true);
  }

    /**
     * Sets recent payment focused card.
     *
     * @param position the position
     */
    public void setRecentPaymentFocusedCard(int position) {
    focusedPosition = position;
  }

    /**
     * Clear focus.
     */
    public void clearFocus() {
    if (focusedPosition != Constants.NO_FOCUS) {
      viewModels.get(focusedPosition).setViewFocused(false);
    }
    focusedPosition = Constants.NO_FOCUS;
  }

    /**
     * Is position in focus boolean.
     *
     * @param position the position
     * @return the boolean
     */
    public boolean isPositionInFocus(int position) {
    return position == focusedPosition;
  }
  //endregion

    /**
     * Save state.
     */
//save/restore state
  public void saveState() {

    for (PaymentOptionViewModel viewModel : viewModels) {
      viewModel.saveState();
    }
  }

  private final class ViewModelsHandler {

    private void generateViewModels() {

      ArrayList<PaymentOptionViewModel> viewModelsResult = new ArrayList<>();

      CurrencyViewModel currencyViewModel = generateCurrencyModel();
      viewModelsResult.add(currencyViewModel);

//      ArrayList<PaymentOption> paymentOptions = getPaymentOptionsResponse().getPaymentOptions();
      ArrayList<PaymentOption> paymentOptionsWorker = new ArrayList<>(getPaymentOptionsResponse().getPaymentOptions());


      /**
       * Haitham Sheshtawy 20/12/2018
       * Java 8 way of filtering collection against old Utils filter method
       * Just Test >>> can't apply it now because Java 8 stream only works starting from API 24+
       * and our project minSDK = 15
       */

      ArrayList<PaymentOption> webPaymentOptions = Utils.List
          .filter(paymentOptionsWorker, getPaymentOptionsFilter(PaymentType.WEB));

      ArrayList<PaymentOption> cardPaymentOptions = Utils.List
          .filter(paymentOptionsWorker, getPaymentOptionsFilter(PaymentType.CARD));

      ArrayList<SavedCard> savedCardsWorker = new ArrayList<>(getPaymentOptionsResponse().getCards());

      boolean hasSavedCards = savedCardsWorker.size() > 0;
      boolean hasWebPaymentOptions = webPaymentOptions.size() > 0;
      boolean hasCardPaymentOptions = cardPaymentOptions.size() > 0;
      boolean hasOtherPaymentOptions = hasWebPaymentOptions || hasCardPaymentOptions;
      boolean displaysGroupTitles = hasSavedCards && hasOtherPaymentOptions;

      if (displaysGroupTitles) {

        GroupViewModel recentGroupModel = generateGroupModel(Constants.recentGroupTitle);
        viewModelsResult.add(recentGroupModel);
      }

      if (hasSavedCards) {

        RecentSectionViewModel recentCardsModel = generateSavedCardsModel(savedCardsWorker);
        viewModelsResult.add(recentCardsModel);
      }

      if (displaysGroupTitles) {

        GroupViewModel othersGroupModel = generateGroupModel(Constants.othersGroupTitle);
        viewModelsResult.add(othersGroupModel);
      }
      // according to paymentOptions response im working on for test >>> user has two web payment options [ KNEt - BENEFIT]
      if (hasWebPaymentOptions) {
        EmptyViewModel emptyModel = PaymentOptionsDataManagerUtils.ViewModelUtils
            .generateEmptyModel(Constants.spaceBeforeWebPaymentOptionsIdentifier,
                PaymentOptionsDataManager.this);
        viewModelsResult.add(emptyModel);
        for (PaymentOption paymentOption : webPaymentOptions) {
          WebPaymentViewModel webPaymentModel = PaymentOptionsDataManagerUtils.ViewModelUtils
              .generateWebPaymentModel(paymentOption, PaymentOptionsDataManager.this);
          viewModelsResult.add(webPaymentModel);
        }
      }

      if (hasCardPaymentOptions) {
        EmptyViewModel emptyModel = PaymentOptionsDataManagerUtils.ViewModelUtils
            .generateEmptyModel(Constants.spaceBetweenWebAndCardOptionsIdentifier,
                PaymentOptionsDataManager.this);
        viewModelsResult.add(emptyModel);
        CardCredentialsViewModel cardPaymentModel = generateCardPaymentModel(cardPaymentOptions);
        viewModelsResult.add(cardPaymentModel);
      }

      viewModels = viewModelsResult;
    }

      /**
       * Filter view models.
       *
       * @param currency the currency
       */
      public void filterViewModels(String currency) {
      ArrayList<PaymentOption> paymentOptionsWorker= new ArrayList<>(getPaymentOptionsResponse().getPaymentOptions());
      ArrayList<SavedCard> savedCardsWorker = new ArrayList<>(getPaymentOptionsResponse().getCards());
      ArrayList<PaymentOptionViewModel> viewModelResult = new ArrayList<>();

        ArrayList<SavedCard> savedCards=null;
      if(
              PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() != TransactionMode.SAVE_CARD
      &&
              PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() != TransactionMode.TOKENIZE_CARD
      )
      {
        CurrencyViewModel model = findCurrencyModel();
        viewModelResult.add(model);

        savedCards = filterByCurrenciesAndSortList(savedCardsWorker, currency);
      }

      if(savedCards==null )savedCards= new ArrayList<>();



      ArrayList<PaymentOption> webPaymentOptions = filteredByPaymentTypeAndCurrencyAndSortedList(
          paymentOptionsWorker, PaymentType.WEB, currency);

      ArrayList<PaymentOption> cardPaymentOptions = filteredByPaymentTypeAndCurrencyAndSortedList(
          paymentOptionsWorker, PaymentType.CARD, currency);

      boolean hasSavedCards = savedCards.size() > 0;
      boolean hasWebPaymentOptions = webPaymentOptions.size() > 0;
      boolean hasCardPaymentOptions = cardPaymentOptions.size() > 0;
      boolean hasOtherPaymentOptions = hasWebPaymentOptions || hasCardPaymentOptions;
      boolean displaysGroupTitles = hasSavedCards && hasOtherPaymentOptions;

      if (displaysGroupTitles) {

        GroupViewModel recentGroupModel = findGroupModel(Constants.recentGroupTitle);
        viewModelResult.add(recentGroupModel);
      }

      if (hasSavedCards) {
        RecentSectionViewModel savedCardsModel = findSavedCardsModel();
        savedCardsModel.setData(savedCards);
        // update RecentSectionViewModel data with only filtered cards. // added to fix filtering saved cards based on changed currency
        viewModelResult.add(savedCardsModel);
      }

      if (displaysGroupTitles) {

        GroupViewModel recentGroupModel = findGroupModel(Constants.othersGroupTitle);
        viewModelResult.add(recentGroupModel);
      }

      if (hasWebPaymentOptions &&  PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() != TransactionMode.TOKENIZE_CARD) {

        if (!hasSavedCards) {

          EmptyViewModel emptyModel = findEmptyModel(
              Constants.spaceBeforeWebPaymentOptionsIdentifier);
          viewModelResult.add(emptyModel);
        }

        for (PaymentOption paymentOption : webPaymentOptions) {

          WebPaymentViewModel webPaymentModel = findWebPaymentModel(paymentOption);
          viewModelResult.add(webPaymentModel);
        }
      }

      if (hasCardPaymentOptions) {

        if (hasWebPaymentOptions || !displaysGroupTitles) {

          EmptyViewModel emptyModel = findEmptyModel(
              Constants.spaceBetweenWebAndCardOptionsIdentifier);
          viewModelResult.add(emptyModel);
        }

        CardCredentialsViewModel cardPaymentModel = findCardPaymentModel();
        // save CardCredentialsViewModel in ActivityDataExchanger to use it CardCredentialsViewHolder
        ActivityDataExchanger.getInstance().setCardCredentialsViewModel(cardPaymentModel);
        viewModelResult.add(cardPaymentModel);
      }

      visibleViewModels = viewModelResult;
      lastFilteredCurrency = currency;
    }

    private CurrencyViewModel generateCurrencyModel() {
      CurrencyViewModelData currencyViewModelData = new CurrencyViewModelData(
          getTransactionCurrency(), getSelectedCurrency());

      return new CurrencyViewModel(PaymentOptionsDataManager.this, currencyViewModelData);
    }

    private CurrencyViewModel findCurrencyModel() {

      for (PaymentOptionViewModel model : getViewModels()) {
        if (model instanceof CurrencyViewModel) {

          return (CurrencyViewModel) model;
        }
      }

      return null;
    }

    private GroupViewModel generateGroupModel(String title) {

      return new GroupViewModel(PaymentOptionsDataManager.this, title);
    }

    private GroupViewModel findGroupModel(String title) {

      for (PaymentOptionViewModel model : getViewModels()) {

        if (model instanceof GroupViewModel) {

          if (((GroupViewModel) model).getData().equals(title)) {

            return (GroupViewModel) model;
          }
        }
      }

      return null;
    }

    private RecentSectionViewModel generateSavedCardsModel(ArrayList<SavedCard> cards) {

      return new RecentSectionViewModel(PaymentOptionsDataManager.this, cards);
    }

    private RecentSectionViewModel findSavedCardsModel() {

      return findSingleModel(RecentSectionViewModel.class);
    }

    private EmptyViewModel findEmptyModel(String identifier) {

      for (PaymentOptionViewModel model : getViewModels()) {

        if (model instanceof EmptyViewModel) {

          if (((EmptyViewModel) model).getData().getIdentifier().equals(identifier)) {

            return (EmptyViewModel) model;
          }
        }
      }

      return null;
    }

    private WebPaymentViewModel findWebPaymentModel(PaymentOption paymentOption) {

      for (PaymentOptionViewModel model : getViewModels()) {

        if (model instanceof WebPaymentViewModel) {

          if (((WebPaymentViewModel) model).getData() == paymentOption) {

            return (WebPaymentViewModel) model;
          }
        }
      }

      return null;
    }



    private CardCredentialsViewModel generateCardPaymentModel(
        ArrayList<PaymentOption> paymentOptions) {

        boolean displaysSaveCardSection=false;

      if(PaymentDataManager.getInstance().getSDKSettings().getData()
        .getPermissions()!=null)

       displaysSaveCardSection = PaymentDataManager.getInstance().getSDKSettings().getData()
          .getPermissions().contains(Permission.MERCHANT_CHECKOUT);

      CardCredentialsViewModelData data = new CardCredentialsViewModelData(paymentOptions,
          displaysSaveCardSection);

      return new CardCredentialsViewModel(PaymentOptionsDataManager.this, data);
    }

    private CardCredentialsViewModel findCardPaymentModel() {

      return findSingleModel(CardCredentialsViewModel.class);
    }

    private <T extends PaymentOptionViewModel> T findSingleModel(Class modelClass) {

      for (PaymentOptionViewModel model : getViewModels()) {

        if (model.getClass() == modelClass) {

          return (T) model;
        }
      }

      return null;
    }

    private Utils.List.Filter<PaymentOption> getPaymentOptionsFilter(
        final PaymentType paymentType) {

      return new Utils.List.Filter<PaymentOption>() {

        @Override
        public boolean isIncluded(PaymentOption object) {

          return object.getPaymentType() == paymentType;
        }
      };
    }

    private <E extends CurrenciesSupport & Comparable<E>> ArrayList<E> filterByCurrenciesAndSortList(
        ArrayList<E> list, final String currency) {
      Utils.List.Filter<E> filter = getCurrenciesFilter(currency);
      ArrayList<E> filtered = Utils.List.filter(list, filter);
      Collections.sort(filtered);
      return filtered;
    }

    private ArrayList<PaymentOption> filteredByPaymentTypeAndCurrencyAndSortedList(
        ArrayList<PaymentOption> list, PaymentType paymentType, String currency) {

      ArrayList<Utils.List.Filter<PaymentOption>> filters = new ArrayList<>();
      filters.add(this.<PaymentOption>getCurrenciesFilter(currency));
      filters.add(getPaymentOptionsFilter(paymentType));
      CompoundFilter<PaymentOption> filter = new CompoundFilter<>(filters);

      ArrayList<PaymentOption> filtered = Utils.List.filter(list, filter);
      Collections.sort(filtered);

      return filtered;
    }


    private <E extends CurrenciesSupport> Utils.List.Filter<E> getCurrenciesFilter(
        final String currency) {

      return new Utils.List.Filter<E>() {

        @Override
        public boolean isIncluded(E object) {
          return object.getSupportedCurrencies().contains(currency);
        }
      };
    }

    private Utils.List.Filter<PaymentOptionViewModel> getPaymentOptionViewModelFilter(
        final Class modelClass) {

      return new Utils.List.Filter<PaymentOptionViewModel>() {

        @Override
        public boolean isIncluded(PaymentOptionViewModel object) {

          return object.getClass() == modelClass;
        }
      };
    }

    private final class Constants {

      private static final String recentGroupTitle = "RECENT";
      private static final String othersGroupTitle = "OTHERS";

      private static final String spaceBeforeWebPaymentOptionsIdentifier = "space_before_web_payment_options";
      private static final String spaceBetweenWebAndCardOptionsIdentifier = "space_between_web_and_card_options";
    }
  }
}
