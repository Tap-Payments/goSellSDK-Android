package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import company.tap.gosellapi.internal.adapters.PaymentOptionsViewHolderFocusedStateInterface;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class DummyViewHolder extends PaymentOptionsBaseViewHolder {
    public DummyViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(view, focusedStateInterface);
    }

    @Override
    public void bind(PaymentOptionsBaseModel data) {

    }
}