package company.tap.gosellapi.open.models;

import android.support.annotation.NonNull;

import company.tap.gosellapi.internal.utils.LocaleCurrencies;
import company.tap.gosellapi.open.exception.CurrencyException;

public class TapCurrency {

  @NonNull
  private String isoCode;

  public TapCurrency(@NonNull String isoCode) throws CurrencyException {
    String code = isoCode.toLowerCase();
    if(!LocaleCurrencies.checkUserCurrency(code)) {
      throw CurrencyException.getUnknown(code);
    }
    this.isoCode = code;
  }


  @NonNull
  public String getIsoCode() {
    return isoCode;
  }

}
