package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CurrencyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CurrencyViewModel;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

/**
 * The type Currency view holder.
 */
public class CurrencyViewHolder extends PaymentOptionsBaseViewHolder<CurrencyViewModelData, CurrencyViewHolder, CurrencyViewModel> {

    private TextView currencyMainText;
    private TextView currencySecondaryText;
    private ImageView arrowIcon;

    /**
     * Instantiates a new Currency view holder.
     *
     * @param view the view
     */
    CurrencyViewHolder(View view) {

        super(view);

        currencyMainText = view.findViewById(R.id.currencyMainText);
        currencySecondaryText = view.findViewById(R.id.currencySecondaryText);
        arrowIcon = view.findViewById(R.id.arrowIcon);

        if (SDK_INT >= JELLY_BEAN_MR1) {
            if (view.getContext().getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                arrowIcon.setBackgroundResource(R.drawable.ic_arrow_left_normal);
            }
        }
    }

    @Override
    public void bind(CurrencyViewModelData data) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.holderClicked();
            }
        });

        setTexts(data);
    }


    private void setTexts(CurrencyViewModelData data) {

        AmountedCurrency transactionCurrency    = data.getTransactionCurrency();
        AmountedCurrency selectedCurrency       = data.getSelectedCurrency();

        // replace CurrencyFormatter with Utils.getFormattedCurrency();
        String selectedCurrencyText = selectedCurrency.getSymbol()+ " " + selectedCurrency.getAmount();// Utils.getFormattedCurrency(selectedCurrency);

        if (transactionCurrency.getCurrency().equals(selectedCurrency.getCurrency())) {
            currencySecondaryText.setVisibility(View.GONE);
            currencySecondaryText.setText("");
        }
        else {
            String transactionCurrencyText =   transactionCurrency.getSymbol() + " " + transactionCurrency.getAmount();  //  Utils.getFormattedCurrency(transactionCurrency);
            currencySecondaryText.setVisibility(View.VISIBLE);
            currencySecondaryText.setText(transactionCurrencyText);
        }
        currencyMainText.setText(selectedCurrencyText);



    }
}
