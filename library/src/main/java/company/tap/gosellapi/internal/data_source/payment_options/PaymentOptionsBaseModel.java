package company.tap.gosellapi.internal.data_source.payment_options;

class PaymentOptionsBaseModel<T> {
    private T data;
    private int modelType;

    PaymentOptionsBaseModel(T data, int modelType) {
        this.data = data;
        this.modelType = modelType;
    }

    T getData() {
        return data;
    }

    int getModelType() {
        return modelType;
    }
}
