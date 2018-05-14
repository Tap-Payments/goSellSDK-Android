package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

//T - data, K - holder, Q - this model
public class PaymentOptionsBaseViewModel<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionsBaseViewModel<T, K, Q>> {
    PaymentOptionsDataManager parentDataManager;
    T data;
    private int modelType;

    private K holder;
    int position;

    PaymentOptionsBaseViewModel(PaymentOptionsDataManager parentDataManager, T data, int modelType) {
        this.parentDataManager = parentDataManager;
        this.data = data;
        this.modelType = modelType;
    }

    public int getModelType() {
        return modelType;
    }

    public void registerHolder(K holder, int position) {
        this.holder = holder;
        this.position = position;

        holder.bind(data);
        holder.setFocused(parentDataManager.isPositionInFocus(position));
    }

    public void unregisterHolder() {
        holder = null;
    }

    public void setViewFocused(boolean focused) {
        if (holder != null) holder.setFocused(focused);
    }
}
