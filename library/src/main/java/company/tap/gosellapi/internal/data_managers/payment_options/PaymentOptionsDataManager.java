package company.tap.gosellapi.internal.data_managers.payment_options;

import java.util.ArrayList;
import java.util.Collections;

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

        ArrayList<AmountedCurrency> currencies = getPaymentOptionsResponse().getSupportedCurrencies();

        CurrencyViewModelData currencyViewModelData = ((CurrencyViewModel) viewModels.get(position)).getData();
        listener.startCurrencySelection(currencies, currencyViewModelData.getSelectedCurrency());
    }

    public void recentPaymentItemClicked(int position, SavedCard recentItem) {
        setFocused(position);
        listener.startOTP();
    }

    public void webPaymentSystemViewHolderClicked(WebPaymentViewModel sender, int position) {

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

            ArrayList<PaymentOption> paymentOptions     = getPaymentOptionsResponse().getPaymentOptions();
            ArrayList<PaymentOption> webPaymentOptions  = Utils.List.filter(paymentOptions, getPaymentOptionsFilter(PaymentType.WEB));
            ArrayList<PaymentOption> cardPaymentOptions = Utils.List.filter(paymentOptions, getPaymentOptionsFilter(PaymentType.CARD));

            ArrayList<SavedCard> savedCards = getPaymentOptionsResponse().getCards();

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

            if ( hasWebPaymentOptions ) {

                EmptyViewModel emptyModel = generateEmptyModel(Constants.spaceBeforeWebPaymentOptionsIdentifier);
                result.add(emptyModel);

                for ( PaymentOption paymentOption : webPaymentOptions ) {

                    WebPaymentViewModel webPaymentModel = generateWebPaymentModel(paymentOption);
                    result.add(webPaymentModel);
                }
            }

            if ( hasCardPaymentOptions ) {

                EmptyViewModel emptyModel = generateEmptyModel(Constants.spaceBetweenWebAndCardOptionsIdentifier);
                result.add(emptyModel);

                CardCredentialsViewModel cardPaymentModel = generateCardPaymentModel(cardPaymentOptions);
                result.add(cardPaymentModel);
            }

            viewModels = result;
        }

        private void filterViewModels(String currency) {

            if ( lastFilteredCurrency != null && lastFilteredCurrency.equals(currency) ) { return; }

            ArrayList<PaymentOptionViewModel> result = new ArrayList<>();

            CurrencyViewModel model = findCurrencyModel();
            result.add(model);

            ArrayList<SavedCard> savedCards = filterByCurrenciesAndSortList(getPaymentOptionsResponse().getCards(), currency);

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

        private EmptyViewModel generateEmptyModel(String identifier) {

            EmptyViewModelData modelData = new EmptyViewModelData(identifier);

            return new EmptyViewModel(PaymentOptionsDataManager.this, modelData);
        }

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

        private WebPaymentViewModel generateWebPaymentModel(PaymentOption paymentOption) {

            return new WebPaymentViewModel(PaymentOptionsDataManager.this, paymentOption);
        }

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

        private <E extends CurrenciesSupport & Comparable<E>> ArrayList<E> filterByCurrenciesAndSortList(ArrayList<E> list, String currency) {

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
