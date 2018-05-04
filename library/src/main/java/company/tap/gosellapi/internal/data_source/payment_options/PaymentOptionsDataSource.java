package company.tap.gosellapi.internal.data_source.payment_options;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;

public class PaymentOptionsDataSource {
    public enum PaymentType {
        CURRENCY,
        RECENT,
        WEB,
        CARD
    }

    private enum CardPaymentType {
        WEB("web"),
        CARD("card");

        private String value;

        CardPaymentType(String value) {
            this.value = value;
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
            dataList.add(new PaymentOptionsCurrencyModel(supportedCurrencies));
        }
    }

    private void addRecent() {
        ArrayList<Card> recentCards = paymentOptionsResponse.getCards();
        if (recentCards != null && recentCards.size() > 0) {
            dataList.add(new PaymentOptionsRecentModel(recentCards));
        }
    }

    private void addWeb() {
        ArrayList<PaymentOption> paymentOptions = paymentOptionsResponse.getPayment_options();

        if (paymentOptions == null || paymentOptions.size() == 0) {
            return;
        }

        for (PaymentOption paymentOption : paymentOptions) {
            if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.WEB.value)) {
                dataList.add(new PaymentOptionsWebModel(paymentOption));
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
            dataList.add(new PaymentOptionsCardModel(paymentOptionsCards));
        }
    }
}
