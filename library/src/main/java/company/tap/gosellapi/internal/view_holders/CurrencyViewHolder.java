package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.HashMap;

import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class CurrencyViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<HashMap<String,Double>>> {
    CurrencyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<HashMap<String, Double>> data) {

    }
}
