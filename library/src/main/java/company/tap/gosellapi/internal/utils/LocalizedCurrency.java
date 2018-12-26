package company.tap.gosellapi.internal.utils;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class LocalizedCurrency implements Comparable<LocalizedCurrency> {

  private AmountedCurrency currency;
  private String localizedDisplayName;

  @Override
  public int compareTo(@NonNull LocalizedCurrency o) {

    return getLocalizedDisplayName().compareToIgnoreCase(o.getLocalizedDisplayName());
  }

  public LocalizedCurrency(AmountedCurrency currency) {

    this.currency = currency;
    this.localizedDisplayName = CurrencyFormatter.getLocalizedCurrencyName(currency.getCurrency());
  }

  public AmountedCurrency getCurrency() { return currency; }
  public String getLocalizedDisplayName() { return localizedDisplayName; }
}
