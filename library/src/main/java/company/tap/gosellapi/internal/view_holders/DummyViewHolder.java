package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class DummyViewHolder extends PaymentOptionsBaseViewHolder {
    public DummyViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener adapterListener) {
        super(view, focusedStateInterface, adapterListener);
    }

    @Override
    public void bind(PaymentOptionsBaseModel data) {

    }

    @Override
    public void setFocused(boolean isFocused) { }
}