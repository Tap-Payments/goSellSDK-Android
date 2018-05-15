package company.tap.gosellapi.internal.viewholders;

import android.view.View;

import java.util.HashMap;

import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CurrencyViewModel;

public class CurrencyViewHolder
        extends PaymentOptionsBaseViewHolder<HashMap<String, Double>, CurrencyViewHolder, CurrencyViewModel> {

    CurrencyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(HashMap<String, Double> data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.holderClicked();
            }
        });
    }

    @Override
    public void setFocused(boolean isFocused) {
    }
}
