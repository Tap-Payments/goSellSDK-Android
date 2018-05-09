package company.tap.gosellapi.internal.viewholders_and_viewmodels;

import android.view.View;

import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;

public class EmptyViewHolder
        extends PaymentOptionsBaseViewHolder<EmptyType> {

    public EmptyViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener adapterListener) {
        super(view, focusedStateInterface, adapterListener);
    }

    @Override
    void bind(EmptyType data) {

    }

    @Override
    public void setFocused(boolean isFocused) { }
}