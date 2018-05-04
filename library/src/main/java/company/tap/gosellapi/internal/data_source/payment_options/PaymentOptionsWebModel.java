package company.tap.gosellapi.internal.data_source.payment_options;

import company.tap.gosellapi.internal.api.models.PaymentOption;

class PaymentOptionsWebModel extends PaymentOptionsBaseModel<PaymentOption> {
    PaymentOptionsWebModel(PaymentOption data, int modelType) {
        super(data, modelType);
    }
}
