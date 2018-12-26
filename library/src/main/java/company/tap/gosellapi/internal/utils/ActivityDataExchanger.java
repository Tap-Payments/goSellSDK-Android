package company.tap.gosellapi.internal.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;

public class ActivityDataExchanger {

  private Class<? extends Activity> clientActivity;

  public static ActivityDataExchanger getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public @Nullable Object getExtra(Intent intent, String key) {
        System.out.println("ActivityDataExchanger >>> intentData : "+ key);
        System.out.println("ActivityDataExchanger >>> intent : "+ intent.getDataString());

        Map<String, Object> intentData = getStorage().get(intent);

        System.out.println(" ActivityDataExchanger >>> intentData : "+intentData );

        if ( intentData == null ) {

            return null;
        }

        return intentData.get(key);
    }

    public void putExtra(@Nullable Object data, String key, Intent intent) {

        Map<String, Object> intentData = getStorage().get(intent);
        if ( intentData == null ) {

            if ( data == null ) { return; }

            intentData = new HashMap<>();
        }

        if ( intentData.containsKey(key) ) {

            if ( data == null ) {

                intentData.remove(key);
            }
            else {

                intentData.put(key, data);
            }
        }
        else {

            if ( data == null ) { return; }

            intentData.put(key, data);
        }

        getStorage().put(intent, intentData);
    }

    public void clearData(Intent intent) {

        getStorage().remove(intent);
    }

  public void saveClientActivity(Class<? extends Activity> callingActivity) {
      clientActivity = callingActivity;
  }

  public Class<? extends Activity> getClientActivity() {
    return clientActivity;
  }

  private static class SingletonHolder {

        private static final ActivityDataExchanger INSTANCE = new ActivityDataExchanger();
    }

    private ActivityDataExchanger() {

        storage = new HashMap<>();
    }

    private Map<Intent, Map<String, Object>> storage;

    private Map<Intent, Map<String, Object>> getStorage() {

        return storage;
    }

    private WebPaymentViewModel webPaymentViewModel;

    private CardCredentialsViewModel  cardCredentialsViewModel;

    public  void setWebPaymentViewModel(WebPaymentViewModel model){
        this.webPaymentViewModel = model;
    }

    public void setCardCredentialsViewModel(CardCredentialsViewModel cardCredentialsViewModel){
      this.cardCredentialsViewModel =cardCredentialsViewModel;
    }



    public WebPaymentViewModel getWebPaymentViewModel() {
       return this.webPaymentViewModel;
    }

  public CardCredentialsViewModel getCardCredentialsViewModel() {
    return cardCredentialsViewModel;
  }
}
