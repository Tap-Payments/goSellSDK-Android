package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.internal.adapters.PaymentOptionsViewHolderFocusedStateInterface;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class CardCredentialsViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<ArrayList<PaymentOption>>> {
    CardCredentialsViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(view, focusedStateInterface);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<ArrayList<PaymentOption>> data) {

    }
}