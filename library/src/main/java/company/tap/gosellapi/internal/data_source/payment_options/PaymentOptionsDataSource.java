package company.tap.gosellapi.internal.data_source.payment_options;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

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
            dataList.add(new PaymentOptionsBaseModel<>(supportedCurrencies, PaymentType.CURRENCY.getViewType()));
        }
    }

    private void addRecent() {
        ArrayList<Card> recentCards = new ArrayList<>();//paymentOptionsResponse.getCards();

        // TODO - temporary Card filler
        for(int i = 0; i < 5; i++) {
            recentCards.add(createCardData());
        }

        if (recentCards != null && recentCards.size() > 0) {
            dataList.add(new PaymentOptionsBaseModel<>(recentCards, PaymentType.RECENT.getViewType()));
        }
    }

    private void addWeb() {
        ArrayList<PaymentOption> paymentOptions = paymentOptionsResponse.getPayment_options();

        if (paymentOptions == null || paymentOptions.size() == 0) {
            return;
        }

        for (PaymentOption paymentOption : paymentOptions) {
            if (paymentOption.getPayment_type().equalsIgnoreCase(CardPaymentType.WEB.value)) {
                dataList.add(new PaymentOptionsBaseModel<>(paymentOption, PaymentType.WEB.getViewType()));
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
            dataList.add(new PaymentOptionsBaseModel<>(paymentOptionsCards, PaymentType.CARD.getViewType()));
        }
    }

    public ArrayList<PaymentOptionsBaseModel> getDataList() {
        return dataList;
    }

    public int getItemViewType(int position) {
        return dataList.get(position).getModelType();
    }

    // TODO remove after real data will be received
    private static String cardJSONString = "{\n" +
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
