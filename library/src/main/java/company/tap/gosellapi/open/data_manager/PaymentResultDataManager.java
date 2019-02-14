package company.tap.gosellapi.open.data_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PaymentResultDataManager {
  private SharedPreferences pref = null;

  public static PaymentResultDataManager getInstance() {
    return PaymentResultHelper.INSTANCE;
  }


  public void saveCustomerReference(@Nullable String customerRef, @NonNull Context context) {

    if (pref == null)
      pref = context.getSharedPreferences("sdkPref", 0); // 0 - for private mode

    SharedPreferences.Editor editor = pref.edit();
    editor.putString("cust_ref", customerRef);
    editor.commit();
  }

  @Nullable
  public String getCustomerRef(Context context) {
    if (pref == null)
      pref = context.getSharedPreferences("sdkPref", 0); // 0 - for private mode

    return pref.getString("cust_ref", null);
  }


  private static class PaymentResultHelper {
    public static final PaymentResultDataManager INSTANCE = new PaymentResultDataManager();
  }


}
