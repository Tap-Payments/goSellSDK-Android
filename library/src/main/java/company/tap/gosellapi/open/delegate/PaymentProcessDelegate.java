package company.tap.gosellapi.open.delegate;

import android.content.Context;
import android.support.annotation.NonNull;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.data_manager.PaymentResultDataManager;

/**
 * The type Payment process delegate.
 */
public class PaymentProcessDelegate implements company.tap.gosellapi.open.interfaces.PaymentProcessDelegate {

   private Charge chargeOrAuthorize;
  private GoSellError goSellError;

  /**
   * save payment result after payment process finished
   * @param _chargeOrAuthorizeOrSaveCard
   */
  @NonNull
  @Override
  public void setPaymentResult(Charge _chargeOrAuthorizeOrSaveCard) {
   chargeOrAuthorize = _chargeOrAuthorizeOrSaveCard;
  }

  /**
   * retrieve payment result
   * @return
   */
  @NonNull
  @Override
  public Charge getPaymentResult() {
    return chargeOrAuthorize;
  }

  /**
   * save customer ref.
   *
   * @param chargeOrAuthorizeOrSaveCard the charge or authorize or save card
   * @param context                     the context
   */
  public void setCustomerID(Charge chargeOrAuthorizeOrSaveCard,Context context) {
    if(chargeOrAuthorizeOrSaveCard!=null){
      if(chargeOrAuthorizeOrSaveCard instanceof Charge){
        System.out.println(" customer id after charge or authorized or save card :" + chargeOrAuthorizeOrSaveCard.getCustomer().getIdentifier());
        PaymentResultDataManager paymentResultDataManager = PaymentResultDataManager.getInstance();
        paymentResultDataManager.saveCustomerReference(chargeOrAuthorizeOrSaveCard.getCustomer().getIdentifier(),context);
      }
    }
  }

  /**
   * set payment error result in case of fail payment
   *
   * @param error the error
   */
  public void setPaymentError(GoSellError error) {
    this.goSellError = error;
  }

  /**
   * retrieve payment error
   *
   * @return go sell error
   */
  public GoSellError getGoSellError(){
    return goSellError;
  }


  private static class PaymentDelegateHelper {
    private static final PaymentProcessDelegate INSTANCE = new PaymentProcessDelegate();
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static PaymentProcessDelegate getInstance() {
    return PaymentDelegateHelper.INSTANCE;
  }
}
