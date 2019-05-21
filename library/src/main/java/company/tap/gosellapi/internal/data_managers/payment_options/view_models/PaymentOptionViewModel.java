package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import android.os.Parcelable;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Payment option view model.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 * @param <Q> the type parameter
 */
//T - data, K - holder, Q - this model
public class PaymentOptionViewModel<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionViewModel<T, K, Q>> {

    /**
     * The Parent data manager.
     */
    PaymentOptionsDataManager parentDataManager;
    /**
     * The Data.
     */
    T data;
    /**
     * The Type.
     */
    PaymentOptionsBaseViewHolder.ViewHolderType type;

    private K holder;
    /**
     * The Position.
     */
    int position;

    private Parcelable savedState;

    /**
     * Instantiates a new Payment option view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     * @param type              the type
     */
    PaymentOptionViewModel(PaymentOptionsDataManager parentDataManager, T data, PaymentOptionsBaseViewHolder.ViewHolderType type) {

        this.parentDataManager  = parentDataManager;
        this.data               = data;
        this.type               = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public PaymentOptionsBaseViewHolder.ViewHolderType getType() { return type; }

    /**
     * Gets payment option.
     *
     * @return the payment option
     */
    public T getPaymentOption() {

        return data;
    }

    /**
     * Get payment options data manager payment options data manager.
     *
     * @return the payment options data manager
     */
    public PaymentOptionsDataManager getPaymentOptionsDataManager(){
        return parentDataManager;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Register holder.
     *
     * @param holder   the holder
     * @param position the position
     */
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

    /**
     * Unregister holder.
     */
    public void unregisterHolder() {

        if (holder != null) holder.saveState();
        holder = null;
    }

    /**
     * Update data.
     */
    public void updateData() {
        if (holder != null) holder.bind(data);
    }


    /**
     * Sets view focused.
     *
     * @param focused the focused
     */
    public void setViewFocused(boolean focused) {
        if (holder != null) holder.setFocused(focused);
    }

    /**
     * Save state.
     */
    public void saveState() {
        if (holder != null) savedState = holder.saveState();
    }
}