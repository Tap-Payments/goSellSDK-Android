package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class DummyViewHolder extends PaymentOptionsBaseViewHolder {
    public DummyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(PaymentOptionsBaseModel data) {

    }
}