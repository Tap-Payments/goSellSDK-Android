package company.tap.gosellapi.internal.data_source.payment_options;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;

class PaymentOptionsCardModel extends PaymentOptionsBaseModel<ArrayList<PaymentOption>> {
    PaymentOptionsCardModel(ArrayList<PaymentOption> data) {
        super(data);
    }
}
