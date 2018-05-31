package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.EmptyViewModel;

public class EmptyViewHolder
        extends PaymentOptionsBaseViewHolder<EmptyType, EmptyViewHolder, EmptyViewModel> {

    EmptyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(EmptyType data) {
    }
}