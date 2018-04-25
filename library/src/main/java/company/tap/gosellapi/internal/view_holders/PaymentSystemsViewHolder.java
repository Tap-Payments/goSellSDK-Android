package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import company.tap.gosellapi.R;

public class PaymentSystemsViewHolder extends RecyclerView.ViewHolder {

    public PaymentSystemsViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false));
    }
}
