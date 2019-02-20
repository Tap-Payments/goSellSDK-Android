package company.tap.gosellapi.internal.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * The type Locale currencies.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class LocaleCurrencies {

  @Nullable
  private static Set<String> isoCodesArray;


    /**
     * Gets iso codes array.
     *
     * @return the iso codes array
     */
    @NonNull
  public static Set<String> getIsoCodesArray() {
    if (isoCodesArray == null)
      isoCodesArray = getLocalizedArray();
    return isoCodesArray;
  }

    /**
     * Check user currency boolean.
     *
     * @param userProvidedCurrency the user provided currency
     * @return the boolean
     */
    public static boolean checkUserCurrency(@NonNull String userProvidedCurrency) {
    return isIsoCodeContainsUserCurrency(userProvidedCurrency.toLowerCase());
  }


  private static boolean isIsoCodeContainsUserCurrency(String userProvidedCurrency) {
    return getIsoCodesArray().contains(userProvidedCurrency);
  }


  private static Set<String> getLocalizedArray() {
    Set<String> currenciesList = new HashSet<String>();
    Locale[] locs = Locale.getAvailableLocales();
    for (Locale loc : locs) {
      try {
        Currency currency = Currency.getInstance(loc);

        if (currency != null) {
          currenciesList.add(currency.getCurrencyCode().toLowerCase());
        }
      } catch (Exception exc) {
        // Locale not found
      }
    }
    return currenciesList;
  }
}
