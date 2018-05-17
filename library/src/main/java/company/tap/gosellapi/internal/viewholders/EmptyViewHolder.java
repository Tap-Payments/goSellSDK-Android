package company.tap.gosellapi.internal.viewholders;

import android.view.View;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.EmptyViewModel;

public class EmptyViewHolder
        extends PaymentOptionsBaseViewHolder<EmptyType, EmptyViewHolder, EmptyViewModel> {

    public EmptyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(EmptyType data) {

    }
}