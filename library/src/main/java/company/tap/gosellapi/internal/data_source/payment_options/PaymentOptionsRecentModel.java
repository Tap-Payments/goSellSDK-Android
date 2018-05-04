package company.tap.gosellapi.internal.data_source.payment_options;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Card;

class PaymentOptionsRecentModel extends PaymentOptionsBaseModel<ArrayList<Card>> {
    PaymentOptionsRecentModel(ArrayList<Card> data, int modelType) {
        super(data, modelType);
    }
}
