package company.tap.gosellapi.internal.data_managers.payment_options;

import java.util.ArrayList;
import java.util.Collections;
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
import company.tap.gosellapi.open.enums.TransactionMode;
import io.card.payment.CreditCard;

public class PaymentOptionsDataManager {

  private int availableHeight;
  private int saveCardHeight;
  private int cardSwitchHeight;
  private AmountedCurrency selectedCurrency;

  public PaymentOptionsResponse getPaymentOptionsResponse() {
    return paymentOptionsResponse;
  }

  public PaymentOption findPaymentOption(String paymentOptionIdentifier) {
    for(PaymentOption paymentOption: paymentOptionsResponse.getPaymentOptions()){
      if((paymentOptionIdentifier).equals(paymentOption.getId())){
        return paymentOption;
      }
    }
    return null;
  }

  public void filterViewModelsWithAmountedCurrency(AmountedCurrency amountedCurrency) {
    //getModelsHandler().generateViewModels();
    getModelsHandler().filterViewModels(amountedCurrency.getCurrency());
  }

  //outer interface (for fragment, containing recyclerView)
  public interface PaymentOptionsDataListener {

    void startCurrencySelection(ArrayList<AmountedCurrency> currencies,
                                AmountedCurrency selectedCurrency);

    void updatePayButtonWithSavedCardExtraFees(SavedCard recentItem,RecentSectionViewModel recentSectionViewModel);

    void startWebPayment(WebPaymentViewModel model);

    void startScanCard();

    void cardDetailsFilled(boolean isFilled, CardCredentialsViewModel cardCredentialsViewModel);

    void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition);

    void addressOnCardClicked();

    void cardExpirationDateClicked(CardCredentialsViewModel model);

    void binNumberEntered(String binNumber);

    void fireWebPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

    void fireCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

    void fireSavedCardPaymentExtraFeesUserDecision(ExtraFeesStatus userChoice);

    void updatePayButtonWithExtraFees(PaymentOption paymentOption);

  }

  private PaymentOptionsDataListener listener;

  private PaymentOptionsResponse paymentOptionsResponse;
  private ViewModelsHandler modelsHandler;

  private ArrayList<PaymentOptionViewModel> viewModels;
  private ArrayList<PaymentOptionViewModel> visibleViewModels;
  private String lastFilteredCurrency;


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

  public PaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {

    this.paymentOptionsResponse = paymentOptionsResponse;
    this.modelsHandler = new ViewModelsHandler();
    getModelsHandler().generateViewModels();
    getModelsHandler().filterViewModels(getPaymentOptionsResponse().getCurrency());
  }

  //region data for adapter
  public int getSize() {
    return getVisibleViewModels().size();
  }

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

  public void setSelectedCurrency(AmountedCurrency selectedCurrency) {

    this.selectedCurrency = selectedCurrency;

    CurrencyViewModel currencyViewModel = getModelsHandler().findCurrencyModel();
    CurrencyViewModelData currencyViewModelData = currencyViewModel.getData();
    currencyViewModelData.setSelectedCurrency(selectedCurrency);
    currencyViewModel.setData(currencyViewModelData);
  }

  public int getItemViewType(int position) {

    return getViewModel(position).getType().getViewType();
  }

  public PaymentOptionViewModel getViewModel(int position) {

    return getVisibleViewModels().get(position);
  }

  public PaymentOptionsDataManager setListener(PaymentOptionsDataListener listener) {

    this.listener = listener;
    return this;
  }
  //endregion

  //region callback actions from child viewModels
  public void currencyHolderClicked(int position) {
    ArrayList<AmountedCurrency> currencies = getPaymentOptionsResponse().getSupportedCurrencies();

    CurrencyViewModelData currencyViewModelData = ((CurrencyViewModel) viewModels.get(position))
        .getData();
    listener.startCurrencySelection(currencies, currencyViewModelData.getSelectedCurrency());
  }

  public void recentPaymentItemClicked(int position, SavedCard recentItem,RecentSectionViewModel recentSectionViewModel) {
    setRecentPaymentFocusedCard(position);
    CardCredentialsViewModel model = getModelsHandler().findCardPaymentModel();
    listener.updatePayButtonWithSavedCardExtraFees(recentItem,recentSectionViewModel);
  }

  public void webPaymentSystemViewHolderClicked(WebPaymentViewModel sender, int position) {
    setFocused(position);
    listener.startWebPayment(sender);
  }

  public void cardScannerButtonClicked() {
    listener.startScanCard();
  }

  public void saveCardSwitchCheckedChanged(boolean isChecked, int saveCardBlockPosition) {
    if (isChecked) {
      listener.saveCardSwitchClicked(isChecked, saveCardBlockPosition);
    }
  }

  public void cardDetailsFilled(boolean isFilled,
                                CardCredentialsViewModel cardCredentialsViewModel) {

    listener.cardDetailsFilled(isFilled, cardCredentialsViewModel);
  }

  public void addressOnCardClicked() {
    listener.addressOnCardClicked();
  }

  public void binNumberEntered(String binNumber) {

    listener.binNumberEntered(binNumber);

  }

  public void cardExpirationDateClicked() {

    CardCredentialsViewModel model = getModelsHandler().findCardPaymentModel();
    if (model != null) {

      listener.cardExpirationDateClicked(model);
    }
  }

  //endregion

  //region update actions (from activity mainly)

  public void setAvailableHeight(int availableHeight) {

    this.availableHeight = availableHeight;
    applyEmptyHolderHeight();
  }

  public void setCardSwitchHeight(int cardSwitchHeight) {

    this.cardSwitchHeight = cardSwitchHeight;
    applyEmptyHolderHeight();
  }

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

  public void currencySelectedByUser(AmountedCurrency selectedCurrency) {

    this.selectedCurrency = selectedCurrency;
    filterViewModelsWithAmountedCurrency(selectedCurrency);
  }


  public void updateCurrencySection(){
    //update currency section
    CurrencyViewModel currencyViewModel = getModelsHandler().findCurrencyModel();
    if (currencyViewModel == null) return;

    CurrencyViewModelData currencyViewModelData = currencyViewModel.getData();
    currencyViewModelData.setSelectedCurrency(selectedCurrency);
    currencyViewModel.updateData();
  }

  public void cardExpirationDateSelected(String month, String year) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    cardCredentialsViewModel.setExpirationMonth(month);
    cardCredentialsViewModel.setExpirationYear(year);

    cardCredentialsViewModel.updateData();
  }

  public void cardScanned(CreditCard card) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    String cardNumber = card.cardNumber;
    if (cardNumber != null && !cardNumber.isEmpty()) {

      cardCredentialsViewModel.setCardNumber(cardNumber);
    }

    int expirationMonth = card.expiryMonth;
    int expirationYear = card.expiryYear;
    System.out.println(" cardScanned >>> expirationMonth " + expirationMonth);
    System.out.println(" cardScanned >>> expirationYear " + expirationYear);
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


  public void updatePayButtonWithExtraFees(PaymentOption paymentOption) {
         listener.updatePayButtonWithExtraFees(paymentOption);
  }

  public void showAddressOnCardCell(boolean isShow) {

    CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
    if (cardCredentialsViewModel == null) return;

    cardCredentialsViewModel.showAddressOnCardCell(isShow);
    //cardCredentialsViewModel.updateData();
  }

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

  public void setRecentPaymentFocusedCard(int position) {
    focusedPosition = position;
  }

  public void clearFocus() {
    if (focusedPosition != Constants.NO_FOCUS) {
      viewModels.get(focusedPosition).setViewFocused(false);
    }
    focusedPosition = Constants.NO_FOCUS;
  }

  public boolean isPositionInFocus(int position) {
    return position == focusedPosition;
  }
  //endregion

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


      System.out.println("/////////////////////  get web/card payment options   ///////////////");
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

    public void filterViewModels(String currency) {
      ArrayList<PaymentOption> paymentOptionsWorker= new ArrayList<>(getPaymentOptionsResponse().getPaymentOptions());
      ArrayList<SavedCard> savedCardsWorker = new ArrayList<>(getPaymentOptionsResponse().getCards());
      ArrayList<PaymentOptionViewModel> viewModelResult = new ArrayList<>();

      if(PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() != TransactionMode.SAVE_CARD)
      {
        CurrencyViewModel model = findCurrencyModel();
        viewModelResult.add(model);
      }


      ArrayList<SavedCard> savedCards = filterByCurrenciesAndSortList(savedCardsWorker, currency);

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

      if (hasWebPaymentOptions) {

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

      boolean displaysSaveCardSection = PaymentDataManager.getInstance().getSDKSettings().getData()
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
