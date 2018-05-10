package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class CardCredentialsViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<ArrayList<PaymentOption>>> {
    CardCredentialsViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener adapterListener) {
        super(view, focusedStateInterface, adapterListener);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<ArrayList<PaymentOption>> data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusedStateInterface.setFocused(getAdapterPosition());
            }
        });
    }

    @Override
    public void setFocused(boolean isFocused) {
//        if (isFocused) {
//            itemView.setBackgroundResource(R.color.vibrant_green);
//        } else {
//            itemView.setBackground(null);
//        }
    }
}