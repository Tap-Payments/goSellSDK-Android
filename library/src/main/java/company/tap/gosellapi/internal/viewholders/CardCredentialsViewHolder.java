package company.tap.gosellapi.internal.viewholders;

import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {

    CardCredentialsViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(ArrayList<PaymentOption> data) {

    }

    @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }
}