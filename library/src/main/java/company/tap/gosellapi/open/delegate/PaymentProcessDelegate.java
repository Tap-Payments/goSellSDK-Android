package company.tap.gosellapi.open.delegate;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.Charge;

public class PaymentProcessDelegate implements company.tap.gosellapi.open.interfaces.PaymentProcessDelegate {

   private Charge chargeOrAuthorize;
  @NonNull
  @Override
  public void setPaymentResult(Charge _chargeOrAuthorize) {
   chargeOrAuthorize = _chargeOrAuthorize;
  }

  @NonNull
  @Override
  public Charge getPaymentResult() {
    return chargeOrAuthorize;
  }

  private static class PaymentDelegateHelper {
    private static final PaymentProcessDelegate INSTANCE = new PaymentProcessDelegate();
  }

  public static PaymentProcessDelegate getInstance() {
    return PaymentDelegateHelper.INSTANCE;
  }
}
