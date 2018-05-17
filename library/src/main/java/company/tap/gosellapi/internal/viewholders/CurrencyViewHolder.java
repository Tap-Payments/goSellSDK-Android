package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_managers.payment_options.CurrencySectionData;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CurrencyViewModel;

public class CurrencyViewHolder
        extends PaymentOptionsBaseViewHolder<CurrencySectionData, CurrencyViewHolder, CurrencyViewModel> {
    private TextView currencyMainText;
    private TextView currencySecondaryText;

    CurrencyViewHolder(View view) {
        super(view);
        currencyMainText = view.findViewById(R.id.currencyMainText);
        currencySecondaryText = view.findViewById(R.id.currencySecondaryText);
    }

    @Override
    public void bind(CurrencySectionData data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.holderClicked();
            }
        });

        setTexts(data);
    }

    private void setTexts(CurrencySectionData data) {
        if (data.getUserChoiceData() != null) {
            currencySecondaryText.setVisibility(View.VISIBLE);
            currencySecondaryText.setText(data.getInitialData());
            currencyMainText.setText(data.getUserChoiceData());
        } else {
            currencySecondaryText.setVisibility(View.GONE);
            currencySecondaryText.setText("");
            currencyMainText.setText(data.getInitialData());
        }
    }
}
