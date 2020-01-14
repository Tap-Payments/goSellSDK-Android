package company.tap.gosellapi.open.models;

import android.support.annotation.NonNull;

import company.tap.gosellapi.internal.utils.LocaleCurrencies;
import company.tap.gosellapi.open.exception.CurrencyException;

/**
 * The type Tap currency.
 */
public class TapCurrency {

  @NonNull
  private String isoCode;

    /**
     * Instantiates a new Tap currency.
     *
     * @param isoCode the iso code
     * @throws CurrencyException the currency exception
     */
    public TapCurrency(@NonNull String isoCode) throws CurrencyException {
      if (isoCode == null || isoCode.isEmpty()) {
        this.isoCode = isoCode;
      } else {
        String code = isoCode.toLowerCase();
        if (!LocaleCurrencies.checkUserCurrency(code)) {
          throw CurrencyException.getUnknown(code);
        }
        this.isoCode = code;
      }
    }

    /**
     * Gets iso code.
     *
     * @return the iso code
     */
    @NonNull
  public String getIsoCode() {
    return isoCode;
  }

}
