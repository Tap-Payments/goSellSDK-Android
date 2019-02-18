package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;

public interface CurrenciesAdapterCallback {

  void itemSelected(AmountedCurrency currency);
}