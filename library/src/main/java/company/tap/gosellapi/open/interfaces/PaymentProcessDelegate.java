package company.tap.gosellapi.open.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.Charge;

/**
 * The interface Payment process delegate.
 */
public interface PaymentProcessDelegate {
  /**
   * save charge result
   *
   * @param chargeOrAuthorize the charge or authorize
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  @NonNull        void              setPaymentResult(Charge chargeOrAuthorize);

  /**
   * retrieve charge result
   *
   * @return payment result
   */
  @NonNull        Charge             getPaymentResult();
}
