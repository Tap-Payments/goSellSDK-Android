package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.HashMap;

import company.tap.gosellapi.internal.adapters.PaymentOptionsViewHolderFocusedStateInterface;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class CurrencyViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<HashMap<String,Double>>> {
    CurrencyViewHolder(View view, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(view, focusedStateInterface);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<HashMap<String, Double>> data) {

    }
}
