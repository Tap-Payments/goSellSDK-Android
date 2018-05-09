package company.tap.gosellapi.internal.viewholders_and_viewmodels;

import android.view.View;

import java.util.HashMap;

import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;

public class CurrencyViewHolder
        extends PaymentOptionsBaseViewHolder<HashMap<String, Double>> {

    CurrencyViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener adapterListener) {
        super(view, focusedStateInterface, adapterListener);
    }

    @Override
    public void bind(HashMap<String, Double> data) {

    }

    @Override
    public void setFocused(boolean isFocused) { }
}
