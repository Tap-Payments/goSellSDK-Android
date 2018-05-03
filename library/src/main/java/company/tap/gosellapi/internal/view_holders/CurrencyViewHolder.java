package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    private MainRecyclerViewAdapter.MainRecyclerViewAdapterListener mListener;

    public CurrencyViewHolder (View view, MainRecyclerViewAdapter.MainRecyclerViewAdapterListener listener) {
        super(view);

        mListener = listener;
    }
}
