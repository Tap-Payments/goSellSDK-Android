package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    private PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener mListener;

    public CurrencyViewHolder (View view, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener listener) {
        super(view);

        mListener = listener;
    }
}
