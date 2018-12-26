package company.tap.gosellapi.internal.data_managers.payment_options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.internal.api.interfaces.CurrenciesSupport;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.SavedCard;
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
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;
import company.tap.gosellapi.internal.utils.CompoundFilter;
import company.tap.gosellapi.internal.utils.Utils;
import io.card.payment.CreditCard;

public class PaymentOptionsDataManager {

    private int availableHeight;
    private int saveCardHeight;
    private int cardSwitchHeight;
    private AmountedCurrency selectedCurrency;

    public PaymentOptionsResponse getPaymentOptionsResponse() {
        return paymentOptionsResponse;
    }

    //outer interface (for fragment, containing recyclerView)
    public interface PaymentOptionsDataListener {

        void startCurrencySelection(ArrayList<AmountedCurrency> currencies, AmountedCurrency selectedCurrency);

        void startOTP();

        void startWebPayment(WebPaymentViewModel model);

        void startScanCard();

        void cardDetailsFilled(boolean isFilled, CardRawData cardRawData);

        void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition);

        void addressOnCardClicked();

        void cardExpirationDateClicked(CardCredentialsViewModel model);

        void binNumberEntered(String binNumber);
    }

    private PaymentOptionsDataListener listener;

    private PaymentOptionsResponse paymentOptionsResponse;
    private ViewModelsHandler modelsHandler;

    private ArrayList<PaymentOptionViewModel> viewModels;
    private ArrayList<PaymentOptionViewModel> visibleViewModels;
    private String lastFilteredCurrency;

    private ViewModelsHandler getModelsHandler() { return modelsHandler; }

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
        System.out.println(" PaymentOptionsRecyclerViewAdapter >>> visibleViewModels size :  "+ getVisibleViewModels().size());
        return getVisibleViewModels().size();
    }

    public AmountedCurrency getSelectedCurrency() {

        if(this.selectedCurrency == null) {
           this.selectedCurrency = getTransactionCurrency();
        }
        return selectedCurrency;
    }

    private AmountedCurrency getTransactionCurrency() {

        String currencyCode = paymentOptionsResponse.getCurrency();

        for(AmountedCurrency amountedCurrency : paymentOptionsResponse.getSupportedCurrencies()) {

            if(amountedCurrency.getCurrency().equals(currencyCode)) return amountedCurrency;
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
        System.out.println(" PaymentOptionsDataManager .... "+ position);
        ArrayList<AmountedCurrency> currencies = getPaymentOptionsResponse().getSupportedCurrencies();
        for(AmountedCurrency amountedCurrency : currencies){
            System.out.println(" supported currency : "+ amountedCurrency);
        }
        CurrencyViewModelData currencyViewModelData = ((CurrencyViewModel) viewModels.get(position)).getData();
        System.out.println(" currencyViewModelData : "+ currencyViewModelData.getSelectedCurrency().getCurrency());
        listener.startCurrencySelection(currencies, currencyViewModelData.getSelectedCurrency());
    }

    public void recentPaymentItemClicked(int position, SavedCard recentItem) {
        setFocused(position);
        listener.startOTP();
    }

    public void webPaymentSystemViewHolderClicked(WebPaymentViewModel sender, int position) {
        System.out.println("webPaymentSystemViewHolderClicked >> webPayment model : " +sender );
        setFocused(position);
        listener.startWebPayment(sender);
    }

    public void cardScannerButtonClicked() {
        listener.startScanCard();
    }

    public void saveCardSwitchCheckedChanged(boolean isChecked, int saveCardBlockPosition) {
//        displaySaveCard(isChecked);
        if (isChecked) {
            listener.saveCardSwitchClicked(isChecked, saveCardBlockPosition);
        }
    }

    public void cardDetailsFilled(boolean isFilled, CardRawData cardRawData) {

        listener.cardDetailsFilled(isFilled, cardRawData);
    }

    public void addressOnCardClicked() {
        listener.addressOnCardClicked();
    }

    public void binNumberEntered(String binNumber) { listener.binNumberEntered(binNumber); }

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

        //update currency section
        CurrencyViewModel currencyViewModel = getModelsHandler().findCurrencyModel();
        if (currencyViewModel == null) return;

        CurrencyViewModelData currencyViewModelData = currencyViewModel.getData();
        currencyViewModelData.setSelectedCurrency(selectedCurrency);
        currencyViewModel.updateData();

        //filter payment options
        CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
        if (cardCredentialsViewModel == null) return;

        ArrayList<PaymentOption> paymentOptions = getPaymentOptionsResponse().getPaymentOptions();
        paymentOptions = Utils.List.filter(paymentOptions, getModelsHandler().<PaymentOption>getCurrenciesFilter(selectedCurrency.getCurrency()));

        CardCredentialsViewModelData cardCredentialsModelData = cardCredentialsViewModel.getData();
        cardCredentialsModelData.setPaymentOptions(paymentOptions);

        cardCredentialsViewModel.updateData();
    }

    public void cardExpirationDateSelected(String month, String year) {

        CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
        if(cardCredentialsViewModel == null) return;

        cardCredentialsViewModel.setExpirationMonth(month);
        cardCredentialsViewModel.setExpirationYear(year);

        cardCredentialsViewModel.updateData();
    }

    public void cardScanned(CreditCard card) {

        CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
        if(cardCredentialsViewModel == null) return;

        String cardNumber = card.cardNumber;
        if ( cardNumber != null  && !cardNumber.isEmpty() ) {

            cardCredentialsViewModel.setCardNumber(cardNumber);
        }

        int expirationMonth = card.expiryMonth;
        int expirationYear = card.expiryYear;
        if (expirationMonth != 0 && expirationYear != 0) {

            cardCredentialsViewModel.setExpirationYear(String.valueOf(expirationYear));
            cardCredentialsViewModel.setExpirationMonth(String.valueOf(expirationMonth));
        }

        String cvv = card.cvv;
        if ( cvv != null && !cvv.isEmpty() ) {

            cardCredentialsViewModel.setCVVnumber(cvv);
        }

        String cardholderName = card.cardholderName;
        if ( cardholderName != null && !cardholderName.isEmpty() ) {

            cardCredentialsViewModel.setNameOnCard(card.cardholderName);
        }

        cardCredentialsViewModel.updateData();
    }

    public void showAddressOnCardCell(boolean isShow) {

        CardCredentialsViewModel cardCredentialsViewModel = getModelsHandler().findCardPaymentModel();
        if(cardCredentialsViewModel == null) return;

        cardCredentialsViewModel.showAddressOnCardCell(isShow);
        cardCredentialsViewModel.updateData();
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

            ArrayList<PaymentOptionViewModel> result = new ArrayList<>();

            CurrencyViewModel currencyViewModel = generateCurrencyModel();
            result.add(currencyViewModel);

             // paymentOptions === paymentMethods
            ArrayList<PaymentOption> paymentOptions     = getPaymentOptionsResponse().getPaymentOptions();

            System.out.println( "/////////////////////  get web/card payment options   ///////////////");
             /**
              * Haitham Sheshtawy 20/12/2018
             * I need to test Java 8 way of filtering collection against old Utils filter method
              * Just Test >>> and I can't apply it now because Java 8 stream only works starting from API 24+
              * and our project minSDK = 15
             */
            // 1- Utils filter
            ArrayList<PaymentOption> webPaymentOptions  = Utils.List.filter(paymentOptions, getPaymentOptionsFilter(PaymentType.WEB));
//            System.out.println("$$$$$$$$$$$$$$$$         Utils filter result                  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
//            for(PaymentOption paymentOption: webPaymentOptions){
//                System.out.println(paymentOption.getName());
//            }
//            // 2- Java 8 stream method
//            List<PaymentOption> paymentOptions1 = paymentOptions.stream()
//                                                                      .filter(p -> p.getPaymentType() == PaymentType.WEB)
//                                                                     .collect(Collectors.toList());
//            System.out.println("$$$$$$$$$$$$$$$$         Java 8 stream filter result   "+paymentOptions1.size()+"               $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
//            for(PaymentOption paymentOption: paymentOptions1){
//                System.out.println(paymentOption.getName());
//            }
//            System.out.println( "////////////////////////////////////////////////////////////////////////////////////////////////////////");
            ArrayList<PaymentOption> cardPaymentOptions = Utils.List.filter(paymentOptions, getPaymentOptionsFilter(PaymentType.CARD));
            for(PaymentOption paymentOption:cardPaymentOptions){
                System.out.println(" Card  option : "+ paymentOption.getName());
            }

            System.out.println( "/////////////////////  get saved cards  ///////////////");
            ArrayList<SavedCard> savedCards = getPaymentOptionsResponse().getCards();

            System.out.println( "/////////////////////  decide which view model to use   ///////////////");
            boolean hasSavedCards           = savedCards.size() > 0;
            boolean hasWebPaymentOptions    = webPaymentOptions.size() > 0;
            boolean hasCardPaymentOptions   = cardPaymentOptions.size() > 0;
            boolean hasOtherPaymentOptions  = hasWebPaymentOptions || hasCardPaymentOptions;
            boolean displaysGroupTitles     = hasSavedCards && hasOtherPaymentOptions;

            if ( displaysGroupTitles ) {

                GroupViewModel recentGroupModel = generateGroupModel(Constants.recentGroupTitle);
                result.add(recentGroupModel);
            }

            if ( hasSavedCards ) {

                RecentSectionViewModel recentCardsModel = generateSavedCardsModel(savedCards);
                result.add(recentCardsModel);
            }

            if ( displaysGroupTitles ) {

                GroupViewModel othersGroupModel = generateGroupModel(Constants.othersGroupTitle);
                result.add(othersGroupModel);
            }
            // according to paymentOptions response im working on for test >>> user has two web payment options [ KNEt - BENEFIT]
            if ( hasWebPaymentOptions ) {
                EmptyViewModel emptyModel = PaymentOptionsDataManagerUtils.ViewModelUtils.generateEmptyModel(Constants.spaceBeforeWebPaymentOptionsIdentifier,PaymentOptionsDataManager.this);
                result.add(emptyModel);
                for ( PaymentOption paymentOption : webPaymentOptions ) {
                    System.out.println(" paymentOption >> to be added ti viewModel List : "+ paymentOption.getName());
                    WebPaymentViewModel webPaymentModel = PaymentOptionsDataManagerUtils.ViewModelUtils.generateWebPaymentModel(paymentOption,PaymentOptionsDataManager.this);
                    result.add(webPaymentModel);
                }
            }

            if ( hasCardPaymentOptions ) {
                EmptyViewModel emptyModel = PaymentOptionsDataManagerUtils.ViewModelUtils.generateEmptyModel(Constants.spaceBetweenWebAndCardOptionsIdentifier,PaymentOptionsDataManager.this);
                result.add(emptyModel);
                CardCredentialsViewModel cardPaymentModel = generateCardPaymentModel(cardPaymentOptions);
                result.add(cardPaymentModel);
            }

            viewModels = result;
        }

        // need more re-factorization >>> it can be inside generateViewModel() and just use filterFlag=true or false
        private void filterViewModels(String currency) {
            System.out.println(" filterViewModels :lastFilteredCurrency :" + lastFilteredCurrency );
            System.out.println(" filterViewModels : currency " + currency );
            System.out.println(" >>> " + (lastFilteredCurrency != null && lastFilteredCurrency.equals(currency)));


            ArrayList<PaymentOptionViewModel> result = new ArrayList<>();

            CurrencyViewModel model = findCurrencyModel();


            result.add(model);

            ArrayList<SavedCard> savedCards = filterByCurrenciesAndSortList(getPaymentOptionsResponse().getCards(), currency);

            System.out.println( "Size : " +  savedCards.size() );
//            savedCards.stream().forEach(item->System.out.println(item.getId()) );
            ArrayList<PaymentOption> paymentOptions = getPaymentOptionsResponse().getPaymentOptions();

            ArrayList<PaymentOption> webPaymentOptions = filteredByPaymentTypeAndCurrencyAndSortedList(paymentOptions, PaymentType.WEB, currency);
            ArrayList<PaymentOption> cardPaymentOptions = filteredByPaymentTypeAndCurrencyAndSortedList(paymentOptions, PaymentType.CARD, currency);

            boolean hasSavedCards           = savedCards.size() > 0;
            boolean hasWebPaymentOptions    = webPaymentOptions.size() > 0;
            boolean hasCardPaymentOptions   = cardPaymentOptions.size() > 0;
            boolean hasOtherPaymentOptions  = hasWebPaymentOptions || hasCardPaymentOptions;
            boolean displaysGroupTitles     = hasSavedCards && hasOtherPaymentOptions;

            if ( displaysGroupTitles ) {

                GroupViewModel recentGroupModel = findGroupModel(Constants.recentGroupTitle);
                result.add(recentGroupModel);
            }

            if ( hasSavedCards ) {

                RecentSectionViewModel savedCardsModel = findSavedCardsModel();
                result.add(savedCardsModel);
            }

            if ( displaysGroupTitles ) {

                GroupViewModel recentGroupModel = findGroupModel(Constants.othersGroupTitle);
                result.add(recentGroupModel);
            }

            if ( hasWebPaymentOptions ) {

                if ( !hasSavedCards ) {

                    EmptyViewModel emptyModel = findEmptyModel(Constants.spaceBeforeWebPaymentOptionsIdentifier);
                    result.add(emptyModel);
                }

                for ( PaymentOption paymentOption : webPaymentOptions ) {

                    WebPaymentViewModel webPaymentModel = findWebPaymentModel(paymentOption);
                    result.add(webPaymentModel);
                }
            }

            if ( hasCardPaymentOptions ) {

                if ( hasWebPaymentOptions || !displaysGroupTitles ) {

                    EmptyViewModel emptyModel = findEmptyModel(Constants.spaceBetweenWebAndCardOptionsIdentifier);
                    result.add(emptyModel);
                }

                CardCredentialsViewModel cardPaymentModel = findCardPaymentModel();
                result.add(cardPaymentModel);
            }

            visibleViewModels = result;
            lastFilteredCurrency = currency;
        }

        private CurrencyViewModel generateCurrencyModel() {
          System.out.println("getTransactionCurrency() : " + getTransactionCurrency().getCurrency());
          System.out.println("getSelectedCurrency() : " + getSelectedCurrency().getCurrency());
            CurrencyViewModelData currencyViewModelData = new CurrencyViewModelData(getTransactionCurrency(), getSelectedCurrency());

            return new CurrencyViewModel(PaymentOptionsDataManager.this, currencyViewModelData);
        }

        private CurrencyViewModel findCurrencyModel() {

            for (PaymentOptionViewModel model : getViewModels() ) {
                if ( model instanceof CurrencyViewModel ) {

                    return (CurrencyViewModel) model;
                }
            }

            return null;
        }

        private GroupViewModel generateGroupModel(String title) {

            return new GroupViewModel(PaymentOptionsDataManager.this, title);
        }

        private GroupViewModel findGroupModel(String title) {

            for (PaymentOptionViewModel model : getViewModels() ) {

                if ( model instanceof GroupViewModel ) {

                    if ( ((GroupViewModel) model).getData().equals(title) ) {

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
         /*
          commented by haitham >>> as i moved it to PayamentOptionsDataManagerUtils
         */
//        private EmptyViewModel generateEmptyModel(String identifier) {
//
//            EmptyViewModelData modelData = new EmptyViewModelData(identifier);
//
//            return new EmptyViewModel(PaymentOptionsDataManager.this, modelData);
//        }

        private EmptyViewModel findEmptyModel(String identifier) {

            for ( PaymentOptionViewModel model : getViewModels() ) {

                if ( model instanceof EmptyViewModel ) {

                    if ( ((EmptyViewModel) model).getData().getIdentifier().equals(identifier) ) {

                        return (EmptyViewModel) model;
                    }
                }
            }

            return null;
        }

         /*
          commented by haitham >>> as i moved it to PayamentOptionsDataManagerUtils
         */
//        private WebPaymentViewModel generateWebPaymentModel(PaymentOption paymentOption) {
//
//            return new WebPaymentViewModel(PaymentOptionsDataManager.this, paymentOption);
//        }

        private WebPaymentViewModel findWebPaymentModel(PaymentOption paymentOption) {

            for ( PaymentOptionViewModel model : getViewModels() ) {

                if ( model instanceof WebPaymentViewModel ) {

                    if ( ((WebPaymentViewModel) model).getData() == paymentOption ) {

                        return (WebPaymentViewModel) model;
                    }
                }
            }

            return null;
        }


        private CardCredentialsViewModel generateCardPaymentModel(ArrayList<PaymentOption> paymentOptions) {

            boolean displaysSaveCardSection = PaymentDataManager.getInstance().getSDKSettings().getData().getPermissions().contains(Permission.MERCHANT_CHECKOUT);
            CardCredentialsViewModelData data = new CardCredentialsViewModelData(paymentOptions, displaysSaveCardSection);

            return new CardCredentialsViewModel(PaymentOptionsDataManager.this, data);
        }

        private CardCredentialsViewModel findCardPaymentModel() {

            return findSingleModel(CardCredentialsViewModel.class);
        }

        private <T extends PaymentOptionViewModel> T findSingleModel(Class modelClass) {

            for ( PaymentOptionViewModel model : getViewModels() ) {

                if ( model.getClass() == modelClass ) {

                    return (T) model;
                }
            }

            return null;
        }

        private Utils.List.Filter<PaymentOption> getPaymentOptionsFilter(final PaymentType paymentType) {

            return new Utils.List.Filter<PaymentOption>() {

                @Override
                public boolean isIncluded(PaymentOption object) {

                    return object.getPaymentType() == paymentType;
                }
            };
        }

        private <E extends CurrenciesSupport & Comparable<E>> ArrayList<E> filterByCurrenciesAndSortList(ArrayList<E> list, final String currency) {
            Utils.List.Filter<E> filter = getCurrenciesFilter(currency);
            ArrayList<E> filtered = Utils.List.filter(list, filter);
            Collections.sort(filtered);
            return filtered;
        }

        private ArrayList<PaymentOption> filteredByPaymentTypeAndCurrencyAndSortedList(ArrayList<PaymentOption> list, PaymentType paymentType, String currency) {

            ArrayList<Utils.List.Filter<PaymentOption>> filters = new ArrayList<>();
            filters.add(this.<PaymentOption>getCurrenciesFilter(currency));
            filters.add(getPaymentOptionsFilter(paymentType));
            CompoundFilter<PaymentOption> filter = new CompoundFilter<>(filters);

            ArrayList<PaymentOption> filtered = Utils.List.filter(list, filter);
            Collections.sort(filtered);

            return filtered;
        }

        private <E extends CurrenciesSupport> Utils.List.Filter<E> getCurrenciesFilter(final String currency) {

            return new Utils.List.Filter<E>() {

                @Override
                public boolean isIncluded(E object) {

                    return object.getSupportedCurrencies().contains(currency);
                }
            };
        }

        private Utils.List.Filter<PaymentOptionViewModel> getPaymentOptionViewModelFilter(final Class modelClass) {

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

            private static final String spaceBeforeWebPaymentOptionsIdentifier  = "space_before_web_payment_options";
            private static final String spaceBetweenWebAndCardOptionsIdentifier = "space_between_web_and_card_options";
        }
    }
}
