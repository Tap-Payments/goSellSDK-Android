package company.tap.gosellapi.internal.data_source.payment_options;

class PaymentOptionsBaseModel<T> {
    private T data;

    PaymentOptionsBaseModel(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }
}
