package company.tap.gosellapi.open.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.Charge;

public interface PaymentProcessDelegate {
  /**
   * save charge result
   * @param chargeOrAuthorize
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  @NonNull        void              setPaymentResult(Charge chargeOrAuthorize);

  /**
   * retrieve charge result
   * @return
   */
  @NonNull        Charge             getPaymentResult();
}
