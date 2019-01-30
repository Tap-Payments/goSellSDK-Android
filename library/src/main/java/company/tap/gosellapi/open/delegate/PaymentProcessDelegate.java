package company.tap.gosellapi.open.delegate;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.open.data_manager.PaymentResultDataManager;

import static company.tap.gosellapi.internal.api.enums.ChargeStatus.DECLINED;

public class PaymentProcessDelegate implements company.tap.gosellapi.open.interfaces.PaymentProcessDelegate {

   private Charge chargeOrAuthorize;
  private GoSellError goSellError;

  @NonNull
  @Override
  public void setPaymentResult(Charge _chargeOrAuthorizeOrSaveCard) {
   chargeOrAuthorize = _chargeOrAuthorizeOrSaveCard;
  }

  @NonNull
  @Override
  public Charge getPaymentResult() {
    return chargeOrAuthorize;
  }

  public void setCustomerID(Charge chargeOrAuthorizeOrSaveCard,Context context) {
    if(chargeOrAuthorizeOrSaveCard!=null){
      if(chargeOrAuthorizeOrSaveCard instanceof Charge){
        System.out.println(" customer id after charge or authorized or save card :" + chargeOrAuthorizeOrSaveCard.getCustomer().getIdentifier());
        PaymentResultDataManager paymentResultDataManager = PaymentResultDataManager.getInstance();
        paymentResultDataManager.saveCustomerReference(chargeOrAuthorizeOrSaveCard.getCustomer().getIdentifier(),context);
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
