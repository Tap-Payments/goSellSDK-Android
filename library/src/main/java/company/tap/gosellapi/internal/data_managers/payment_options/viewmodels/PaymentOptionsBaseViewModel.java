package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.viewholders_and_viewmodels.PaymentOptionsBaseViewHolder;

public class PaymentOptionsBaseViewModel<T, K extends PaymentOptionsBaseViewHolder<T>> {
    private T data;
    private int modelType;
    private K holder;

    PaymentOptionsBaseViewModel(T data, int modelType) {
        this.data = data;
        this.modelType = modelType;
    }

    public void bindHolder(K holder) {
        this.holder = holder;
    }

    public void unbindHolder() {
        holder = null;
    }

    public T getData() {
        return data;
    }

    public int getModelType() {
        return modelType;
    }
}
