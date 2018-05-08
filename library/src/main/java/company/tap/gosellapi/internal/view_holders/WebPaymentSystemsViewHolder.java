package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class WebPaymentSystemsViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<ArrayList<PaymentOption>>> {
    WebPaymentSystemsViewHolder(final View itemView, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(itemView, focusedStateInterface);
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
        if (isFocused) {
            itemView.setBackgroundResource(R.color.vibrant_green);
        } else {
            itemView.setBackground(null);
        }
    }
}
