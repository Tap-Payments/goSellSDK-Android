package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import android.os.Parcelable;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

//T - data, K - holder, Q - this model
public class PaymentOptionViewModel<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionViewModel<T, K, Q>> {

    PaymentOptionsDataManager parentDataManager;
    T data;
    PaymentOptionsBaseViewHolder.ViewHolderType type;

    private K holder;
    int position;

    private Parcelable savedState;

    PaymentOptionViewModel(PaymentOptionsDataManager parentDataManager, T data, PaymentOptionsBaseViewHolder.ViewHolderType type) {

        this.parentDataManager  = parentDataManager;
        this.data               = data;
        this.type               = type;
    }

    public PaymentOptionsBaseViewHolder.ViewHolderType getType() { return type; }

    public T getPaymentOption() {

        return data;
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager(){
        return parentDataManager;
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
        System.out.println(" >>>> data.getClass() : " + data.getClass());
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