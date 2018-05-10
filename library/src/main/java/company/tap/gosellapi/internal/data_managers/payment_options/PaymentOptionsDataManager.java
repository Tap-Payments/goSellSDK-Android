package company.tap.gosellapi.internal.data_managers.payment_options;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CurrencyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.PaymentOptionsBaseViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.RecentSectionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.WebPaymentViewModel;

public class PaymentOptionsDataManager {
    private ArrayList<PaymentOptionsBaseViewModel> dataList;
    private int focusedPosition;

    public PaymentOptionsDataManager() {
        new DataFiller().fill();
    }

    public int getSize() {
        return dataList.size();
    }

    public int getItemViewType(int position) {
        return dataList.get(position).getModelType();
    }

    public PaymentOptionsBaseViewModel getViewModel(int position) {
        return dataList.get(position);
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
                }
            }
        }

        private void addCurrencies() {
            HashMap<String, Double> supportedCurrencies = GlobalDataManager.getInstance().getPaymentOptionsResponse().getSupported_currencies();
            if (supportedCurrencies != null && supportedCurrencies.size() > 0) {
                dataList.add(new CurrencyViewModel(PaymentOptionsDataManager.this, supportedCurrencies, PaymentType.CURRENCY.getViewType()));
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
            ArrayList<PaymentOption> paymentOptions = GlobalDataManager.getInstance().getPaymentOptionsResponse().getPayment_options();

            if (paymentOptions == null || paymentOptions.size() == 0) {
                return;
            }

            for (PaymentOption paymentOption : paymentOptions) {
                if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.WEB.getValue())) {
                    for (int i = 0; i < 20; i++) {
                        dataList.add(new WebPaymentViewModel(PaymentOptionsDataManager.this, paymentOption, PaymentType.WEB.getViewType()));
                    }
                }
            }
        }

        private void addCard() {
            ArrayList<PaymentOption> paymentOptions = GlobalDataManager.getInstance().getPaymentOptionsResponse().getPayment_options();
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
