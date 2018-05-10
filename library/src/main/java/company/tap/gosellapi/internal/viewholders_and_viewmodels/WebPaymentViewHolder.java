package company.tap.gosellapi.internal.viewholders_and_viewmodels;

import android.view.View;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.WebPaymentViewModel;

public class WebPaymentViewHolder
        extends PaymentOptionsBaseViewHolder<PaymentOption, WebPaymentViewHolder, WebPaymentViewModel> {

    WebPaymentViewHolder(final View itemView, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface, PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener adapterListener) {
        super(itemView, focusedStateInterface, adapterListener);
    }

    @Override
    public void bind(PaymentOption data) {
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
