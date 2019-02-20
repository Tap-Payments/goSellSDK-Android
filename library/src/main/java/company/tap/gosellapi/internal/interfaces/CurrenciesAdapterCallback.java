package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;

/**
 * The interface Currencies adapter callback.
 */
public interface CurrenciesAdapterCallback {

    /**
     * Item selected.
     *
     * @param currency the currency
     */
    void itemSelected(AmountedCurrency currency);
}