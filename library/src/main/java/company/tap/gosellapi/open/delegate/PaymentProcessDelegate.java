package company.tap.gosellapi.open.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.data_manager.PaymentResultDataManager;

public class PaymentProcessDelegate implements company.tap.gosellapi.open.interfaces.PaymentProcessDelegate {

   private Charge chargeOrAuthorize;
  private GoSellError goSellError;

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

  public void setCustomerID(Charge chargeOrAuthorize,Context context) {
    if(chargeOrAuthorize!=null){
      if(chargeOrAuthorize instanceof Charge){
        System.out.println(" customer id after charge authorized :" + chargeOrAuthorize.getCustomer().getIdentifier());
        PaymentResultDataManager paymentResultDataManager = PaymentResultDataManager.getInstance();
        paymentResultDataManager.saveCustomerReference(chargeOrAuthorize.getCustomer().getIdentifier(),context);
      }
    }
  }

  public void setPaymentError(GoSellError error) {
    this.goSellError = error;
  }

  public GoSellError getGoSellError(){
    return goSellError;
  }

  private static class PaymentDelegateHelper {
    private static final PaymentProcessDelegate INSTANCE = new PaymentProcessDelegate();
  }

  public static PaymentProcessDelegate getInstance() {
    return PaymentDelegateHelper.INSTANCE;
  }
}
