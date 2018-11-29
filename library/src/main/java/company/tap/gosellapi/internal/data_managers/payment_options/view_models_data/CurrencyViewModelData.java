package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;

public class CurrencyViewModelData {

    @NonNull    private AmountedCurrency transactionCurrency;
    @Nullable   private AmountedCurrency selectedCurrency;

    public CurrencyViewModelData(@NonNull AmountedCurrency transactionCurrency, @Nullable AmountedCurrency selectedCurrency) {
        
        this.transactionCurrency    = transactionCurrency;
        this.selectedCurrency       = selectedCurrency;
    }

    public AmountedCurrency getSelectedCurrency() {

        if ( selectedCurrency == null ) {

            return getTransactionCurrency();
        }
        else {

            return selectedCurrency;
        }
    }

    public void setSelectedCurrency(@Nullable AmountedCurrency selectedCurrency) {

        this.selectedCurrency = selectedCurrency;
    }

    public @NonNull AmountedCurrency getTransactionCurrency() { return transactionCurrency; }
}
