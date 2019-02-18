package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CurrencyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CurrencyViewModel;
import company.tap.gosellapi.internal.utils.CurrencyFormatter;
import company.tap.gosellapi.internal.utils.Utils;

public class CurrencyViewHolder extends PaymentOptionsBaseViewHolder<CurrencyViewModelData, CurrencyViewHolder, CurrencyViewModel> {

    private TextView currencyMainText;
    private TextView currencySecondaryText;

    CurrencyViewHolder(View view) {

        super(view);

        currencyMainText = view.findViewById(R.id.currencyMainText);
        currencySecondaryText = view.findViewById(R.id.currencySecondaryText);
    }

    @Override
    public void bind(CurrencyViewModelData data) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" currency model clicked...... " + viewModel.getData());
                viewModel.holderClicked();
            }
        });

        setTexts(data);
    }


    private void setTexts(CurrencyViewModelData data) {

        AmountedCurrency transactionCurrency    = data.getTransactionCurrency();
        AmountedCurrency selectedCurrency       = data.getSelectedCurrency();

        System.out.println(" Currency View Holders : Utils.getFormattedCurrency : " +  selectedCurrency.getSymbol()+ " " + selectedCurrency.getAmount() );// Utils.getFormattedCurrency(selectedCurrency));
//        System.out.println(" Currency View Holders : CurrencyFormatter.format(selectedCurrency) : " + CurrencyFormatter.format(selectedCurrency));
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
