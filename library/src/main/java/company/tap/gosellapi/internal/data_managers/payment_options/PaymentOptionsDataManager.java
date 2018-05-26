package company.tap.gosellapi.internal.data_managers.payment_options;

import com.google.gson.Gson;

import java.util.ArrayList;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CurrencyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.EmptyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.PaymentOptionsBaseViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.SaveCardViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.WebPaymentViewModel;

public class PaymentOptionsDataManager {
    private int availableHeight;
    private int saveCardHeight;
    private int cardSwitchHeight;

    //outer interface (for fragment, containing recyclerView)
    public interface PaymentOptionsDataListener {
        void startCurrencySelection(ArrayList<AmountedCurrency> currencies, AmountedCurrency selectedCurrency);
        void startOTP();
        void startWebPayment();
        void startScanCard();
        void cardDetailsFilled(boolean isFilled, CardRawData cardRawData);
        void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition);
        void addressOnCardClicked();
    }

    private PaymentOptionsDataListener listener;

    private PaymentOptionsResponse paymentOptionsResponse;
    private ArrayList<PaymentOptionsBaseViewModel> dataList;
    private int focusedPosition = Constants.NO_FOCUS;
    
    public PaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {
        this.paymentOptionsResponse = paymentOptionsResponse;
        new DataFiller().fill();
    }

    //region data for adapter
    public int getSize() {
        return dataList.size();
    }

    public int getItemViewType(int position) {
        return dataList.get(position).getModelType();
    }

    public PaymentOptionsBaseViewModel getViewModel(int position) {
        return dataList.get(position);
    }

    public PaymentOptionsDataManager setListener(PaymentOptionsDataListener listener) {
        this.listener = listener;
        return this;
    }
    //endregion

    //region callback actions from child viewModels
    public void currencyHolderClicked(int position, ArrayList<AmountedCurrency> currencies) {
        CurrencySectionData currencySectionData = ((CurrencyViewModel) dataList.get(position)).getData();
        listener.startCurrencySelection(currencies, currencySectionData.getSelectedCurrency());
    }

    public void recentPaymentItemClicked(int position, Card recentItem) {
        setFocused(position);
        listener.startOTP();
    }

    public void webPaymentSystemViewHolderClicked(int position) {
        setFocused(position);
        listener.startWebPayment();
    }

    public void cardScannerButtonClicked() {
        listener.startScanCard();
    }

    public void saveCardSwitchCheckedChanged(boolean isChecked, int saveCardBlockPosition) {
        displaySaveCard(isChecked);
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
    //endregion

    //region update actions (from activity mainly)
    private PaymentOptionsBaseViewModel getViewModelByType(PaymentType type) {
        for (PaymentOptionsBaseViewModel viewModel : dataList) {
            if (viewModel.getModelType() == type.getViewType()) {
                return viewModel;
            }
        }

        return null;
    }

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
        if (availableHeight != 0 && saveCardHeight != 0 && cardSwitchHeight != 0) {
            int emptyHolderHeight = availableHeight - saveCardHeight - cardSwitchHeight;

            EmptyViewModel emptyViewModel = getEmptyViewModel();
            if (emptyViewModel != null) {
                emptyViewModel.setSpecifiedHeight(emptyHolderHeight > 0 ? emptyHolderHeight : 0);
            }
        }
    }

    public void currencySelectedByUser(AmountedCurrency userChoiceCurrency) {
        //update currency section
        CurrencyViewModel currencyViewModel = getCurrencyViewModel();
        if (currencyViewModel == null) return;
        CurrencySectionData currencySectionData = currencyViewModel.getData();

        currencySectionData.setUserChoiceData(userChoiceCurrency);
        currencyViewModel.updateData();

        //filter payment options
        CardCredentialsViewModel cardCredentialsViewModel = getCardCredentialsViewModel();
        if (cardCredentialsViewModel == null) return;

        cardCredentialsViewModel.filterByCurrency(userChoiceCurrency.getCurrency_code());
    }

    private void displaySaveCard(boolean show) {
        SaveCardViewModel saveCardViewModel = getSaveCardViewModel();
        if (saveCardViewModel != null) {
            saveCardViewModel.displaySaveCard(show);
        }

        EmptyViewModel emptyViewModel = getEmptyViewModel();
        if (emptyViewModel != null) {
            emptyViewModel.displayEmpty(show);
        }
    }

    private CurrencyViewModel getCurrencyViewModel() {
        PaymentOptionsBaseViewModel baseViewModel = getViewModelByType(PaymentType.CURRENCY);
        if (baseViewModel == null || !(baseViewModel instanceof CurrencyViewModel)) return null;

        return (CurrencyViewModel) baseViewModel;
    }

    private CardCredentialsViewModel getCardCredentialsViewModel() {
        PaymentOptionsBaseViewModel baseViewModel = getViewModelByType(PaymentType.CARD);
        if (baseViewModel == null || !(baseViewModel instanceof CardCredentialsViewModel)) return null;

        return (CardCredentialsViewModel) baseViewModel;
    }

    private SaveCardViewModel getSaveCardViewModel() {
        PaymentOptionsBaseViewModel baseViewModel = getViewModelByType(PaymentType.SAVE_CARD);
        if (baseViewModel == null || !(baseViewModel instanceof SaveCardViewModel)) return null;

        return (SaveCardViewModel) baseViewModel;
    }

    private EmptyViewModel getEmptyViewModel() {
        PaymentOptionsBaseViewModel baseViewModel = getViewModelByType(PaymentType.EMPTY);
        if (baseViewModel == null || !(baseViewModel instanceof EmptyViewModel)) return null;

        return  (EmptyViewModel) baseViewModel;
    }
    //endregion

    //region focus interaction between holders
    private void setFocused(int position) {
        if (focusedPosition != Constants.NO_FOCUS) {
            dataList.get(focusedPosition).setViewFocused(false);
        }

        focusedPosition = position;
        dataList.get(focusedPosition).setViewFocused(true);
    }

    public void clearFocus() {
        if (focusedPosition != Constants.NO_FOCUS) {
            dataList.get(focusedPosition).setViewFocused(false);
        }
        focusedPosition = Constants.NO_FOCUS;
    }

    public boolean isPositionInFocus(int position) {
        return position == focusedPosition;
    }
    //endregion

    //save/restore state
    public void saveState() {
        for (PaymentOptionsBaseViewModel viewModel : dataList) {
            viewModel.saveState();
        }
    }

    private final class DataFiller {
        private void fill() {
            dataList = new ArrayList<>();
            for (PaymentType paymentType : PaymentType.values()) {
                switch (paymentType) {
                    case CURRENCY:
                        addCurrencies();
                        break;
                    case RECENT:
                        addRecent();
                        break;
                    case WEB:
                        addWeb();
                        break;
                    case CARD:
                        addCard();
                        break;
                    case SAVE_CARD:
                        addSaveCard();
                        break;
                    case EMPTY:
                        addEmpty();
                        break;
                }
            }
        }

        private void addCurrencies() {
            ArrayList<AmountedCurrency> supportedCurrencies = paymentOptionsResponse.getSupported_currencies();
            if (supportedCurrencies != null && supportedCurrencies.size() > 0) {
                String initialCurrency = paymentOptionsResponse.getCurrency_code();
                CurrencySectionData currencySectionData = new CurrencySectionData(supportedCurrencies, initialCurrency);

                dataList.add(new CurrencyViewModel(PaymentOptionsDataManager.this, currencySectionData, PaymentType.CURRENCY.getViewType()));
            }
        }

        private void addRecent() {
            ArrayList<Card> recentCards = new ArrayList<>();//paymentOptionsResponse.getCards();

            // TODO - temporary Card filler
            for(int i = 0; i < 20; i++) {
                recentCards.add(createCardData());
            }

            if (recentCards != null && recentCards.size() > 0) {
                dataList.add(new RecentSectionViewModel(PaymentOptionsDataManager.this, recentCards, PaymentType.RECENT.getViewType()));
            }
        }

        private void addWeb() {
            ArrayList<PaymentOption> paymentOptions = paymentOptionsResponse.getPayment_options();

            if (paymentOptions == null || paymentOptions.size() == 0) {
                return;
            }

            for (PaymentOption paymentOption : paymentOptions) {
                if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.WEB.getValue())) {
                    dataList.add(new WebPaymentViewModel(PaymentOptionsDataManager.this, paymentOption, PaymentType.WEB.getViewType()));
                }
            }
        }

        private void addCard() {
            ArrayList<PaymentOption> paymentOptions = paymentOptionsResponse.getPayment_options();
            ArrayList<PaymentOption> paymentOptionsCards = new ArrayList<>();

            if (paymentOptions == null || paymentOptions.size() == 0) {
                return;
            }

            for (PaymentOption paymentOption : paymentOptions) {
                if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.CARD.getValue())) {
                    paymentOptionsCards.add(paymentOption);
                }
            }

            if (paymentOptionsCards.size() > 0) {
                dataList.add(new CardCredentialsViewModel(PaymentOptionsDataManager.this, paymentOptionsCards, PaymentType.CARD.getViewType()));
            }
        }

        private void addSaveCard() {
            dataList.add(new SaveCardViewModel(PaymentOptionsDataManager.this, null, PaymentType.SAVE_CARD.getViewType()));
        }

        private void addEmpty() {
            dataList.add(new EmptyViewModel(PaymentOptionsDataManager.this, null, PaymentType.EMPTY.getViewType()));
        }

            // TODO remove after real data will be received
        private String cardJSONString = "{\n" +
                "      \"id\": \"crd_q3242434\",\n" +
                "      \"object\": \"card\",\n" +
                "      \"last4\": \"1025\",\n" +
                "      \"exp_month\": \"02\",\n" +
                "      \"exp_year\": \"25\",\n" +
                "      \"brand\": \"VISA\",\n" +
                "      \"name\": \"test\",\n" +
                "      \"bin\": \"415254\"\n" +
                "    }";

        private Card createCardData() {
            Gson mapper = new Gson();
            return mapper.fromJson(cardJSONString, Card.class);
        }
    }
}
