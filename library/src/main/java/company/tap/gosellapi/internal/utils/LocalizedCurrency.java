package company.tap.gosellapi.internal.utils;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;

/**
 * The type Localized currency.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class LocalizedCurrency implements Comparable<LocalizedCurrency> {

  private AmountedCurrency currency;
  private String localizedDisplayName;

  @Override
  public int compareTo(@NonNull LocalizedCurrency o) {

    return getLocalizedDisplayName().compareToIgnoreCase(o.getLocalizedDisplayName());
  }

    /**
     * Instantiates a new Localized currency.
     *
     * @param currency the currency
     */
    public LocalizedCurrency(AmountedCurrency currency) {

    this.currency = currency;
    this.localizedDisplayName = CurrencyFormatter.getLocalizedCurrencyName(currency.getCurrency());
  }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public AmountedCurrency getCurrency() { return currency; }

    /**
     * Gets localized display name.
     *
     * @return the localized display name
     */
    public String getLocalizedDisplayName() { return localizedDisplayName; }
}
