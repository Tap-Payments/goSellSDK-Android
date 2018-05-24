package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import android.os.Parcelable;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

//T - data, K - holder, Q - this model
public class PaymentOptionsBaseViewModel<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionsBaseViewModel<T, K, Q>> {
    PaymentOptionsDataManager parentDataManager;
    T data;
    private int modelType;

    private K holder;
    int position;

    private Parcelable savedState;

    PaymentOptionsBaseViewModel(PaymentOptionsDataManager parentDataManager, T data, int modelType) {
        this.parentDataManager = parentDataManager;
        this.data = data;
        this.modelType = modelType;
    }

    public int getModelType() {
        return modelType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void registerHolder(K holder, int position) {
        this.holder = holder;
        this.position = position;

        applyStateToHolder();
    }

    private void applyStateToHolder() {
        holder.bind(data);
        holder.setFocused(parentDataManager.isPositionInFocus(position));
        holder.restoreState(savedState);
    }

    public void unregisterHolder() {
        if (holder != null) holder.saveState();
        holder = null;
    }

    public void updateData() {
        if (holder != null) holder.bind(data);
    }

    public void setViewFocused(boolean focused) {
        if (holder != null) holder.setFocused(focused);
    }

    public void saveState() {
        if (holder != null) savedState = holder.saveState();
    }
}
