package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;

/**
 * The type Currency view model data.
 */
public class CurrencyViewModelData {

    @NonNull    private AmountedCurrency transactionCurrency;
    @Nullable   private AmountedCurrency selectedCurrency;

    /**
     * Instantiates a new Currency view model data.
     *
     * @param transactionCurrency the transaction currency
     * @param selectedCurrency    the selected currency
     */
    public CurrencyViewModelData(@NonNull AmountedCurrency transactionCurrency, @Nullable AmountedCurrency selectedCurrency) {
        
        this.transactionCurrency    = transactionCurrency;
        this.selectedCurrency       = selectedCurrency;
    }

    /**
     * Gets selected currency.
     *
     * @return the selected currency
     */
    public AmountedCurrency getSelectedCurrency() {

        if ( selectedCurrency == null ) {

            return getTransactionCurrency();
        }
        else {

            return selectedCurrency;
        }
    }

    /**
     * Sets selected currency.
     *
     * @param selectedCurrency the selected currency
     */
    public void setSelectedCurrency(@Nullable AmountedCurrency selectedCurrency) {

        this.selectedCurrency = selectedCurrency;
    }

    /**
     * Gets transaction currency.
     *
     * @return the transaction currency
     */
    public @NonNull AmountedCurrency getTransactionCurrency() { return transactionCurrency; }
}
