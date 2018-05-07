package company.tap.gosellapi.internal.data_source.payment_options;

public class PaymentOptionsBaseModel<T> {
    private T data;
    private int modelType;

    PaymentOptionsBaseModel(T data, int modelType) {
        this.data = data;
        this.modelType = modelType;
    }

    public T getData() {
        return data;
    }

    public int getModelType() {
        return modelType;
    }
}

