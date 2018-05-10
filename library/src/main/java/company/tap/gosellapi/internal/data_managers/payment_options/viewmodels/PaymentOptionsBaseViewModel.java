package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.PaymentOptionsBaseViewHolder;

//T - data, K - this holder, Q - model
public class PaymentOptionsBaseViewModel<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionsBaseViewModel<T, K, Q>> {
    private PaymentOptionsDataManager parentDataManager;
    private T data;
    private int modelType;

    K holder;
    int position;

    PaymentOptionsBaseViewModel(PaymentOptionsDataManager parentDataManager, T data, int modelType) {
        this.parentDataManager = parentDataManager;
        this.data = data;
        this.modelType = modelType;
    }

    public T getData() {
        return data;
    }

    public int getModelType() {
        return modelType;
    }

    public void registerHolder(K holder, int position) {
        this.holder = holder;
        this.position = position;
    }

    public void unregisterHolder() {
        holder = null;
    }
}
