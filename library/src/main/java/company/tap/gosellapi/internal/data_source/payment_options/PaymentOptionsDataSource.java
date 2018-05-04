package company.tap.gosellapi.internal.data_source.payment_options;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;

public class PaymentOptionsDataSource {
    private enum CardPaymentType {
        WEB("web"),
        CARD("card");

        private String value;

        CardPaymentType(String value) {
            this.value = value;
        }
    }

    public enum PaymentType {
        CURRENCY(0),
        RECENT(1),
        WEB(2),
        CARD(3);

        private int viewType;

        PaymentType(int viewType) {
            this.viewType = viewType;
        }

        public static PaymentType getByViewType(int viewType) {
            for (PaymentType paymentType : PaymentType.values()) {
                if (paymentType.viewType == viewType) {
                    return paymentType;
                }
            }

            return null;
        }
    }

    private PaymentOptionsResponse paymentOptionsResponse;
    private ArrayList<PaymentOptionsBaseModel> dataList;

    public PaymentOptionsDataSource(@NonNull PaymentOptionsResponse paymentOptionsResponse) {
        this.paymentOptionsResponse = paymentOptionsResponse;
        init();
    }

    private void init() {
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
        HashMap<String,Double> supportedCurrencies = paymentOptionsResponse.getSupported_currencies();
        if (supportedCurrencies != null && supportedCurrencies.size() > 0) {
            dataList.add(new PaymentOptionsCurrencyModel(supportedCurrencies, PaymentType.CURRENCY.viewType));
        }
    }

    private void addRecent() {
        ArrayList<Card> recentCards = paymentOptionsResponse.getCards();
        if (recentCards != null && recentCards.size() > 0) {
            dataList.add(new PaymentOptionsRecentModel(recentCards, PaymentType.RECENT.viewType));
        }
    }

    private void addWeb() {
        ArrayList<PaymentOption> paymentOptions = paymentOptionsResponse.getPayment_options();

        if (paymentOptions == null || paymentOptions.size() == 0) {
            return;
        }

        for (PaymentOption paymentOption : paymentOptions) {
            if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.WEB.value)) {
                dataList.add(new PaymentOptionsWebModel(paymentOption, PaymentType.WEB.viewType));
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
            if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.CARD.value)) {
                paymentOptionsCards.add(paymentOption);
            }
        }

        if (paymentOptionsCards.size() > 0) {
            dataList.add(new PaymentOptionsCardModel(paymentOptionsCards, PaymentType.CARD.viewType));
        }
    }

    public ArrayList<PaymentOptionsBaseModel> getDataList() {
        return dataList;
    }

    public int getItemViewType(int position) {
        return dataList.get(position).getModelType();
    }
}
