package company.tap.gosellapi.open.data_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The type Payment result data manager.
 */
public class PaymentResultDataManager {
  private SharedPreferences pref = null;

  /**
   * Instantiate single instance from PaymentResultDataManager using Sigleton Builder
   *
   * @return instance
   */
  public static PaymentResultDataManager getInstance() {
    return PaymentResultHelper.INSTANCE;
  }


  /**
   * save customer reference after payment is finished
   *
   * @param customerRef the customer ref
   * @param context     the context
   */
  public void saveCustomerReference(@Nullable String customerRef, @NonNull Context context) {

    if (pref == null)
      pref = context.getSharedPreferences("sdkPref", 0); // 0 - for private mode

    SharedPreferences.Editor editor = pref.edit();
    editor.putString("cust_ref", customerRef);
    editor.commit();
  }

  /**
   * retrieve customer reference
   *
   * @param context the context
   * @return customer ref
   */
  @Nullable
  public String getCustomerRef(Context context) {
    if (pref == null)
      pref = context.getSharedPreferences("sdkPref", 0); // 0 - for private mode

    return pref.getString("cust_ref", null);
  }


  private static class PaymentResultHelper {
    /**
     * The constant INSTANCE.
     */
    public static final PaymentResultDataManager INSTANCE = new PaymentResultDataManager();
  }


}
