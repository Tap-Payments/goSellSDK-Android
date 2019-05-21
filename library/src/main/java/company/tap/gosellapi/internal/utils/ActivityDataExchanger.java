package company.tap.gosellapi.internal.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;

/**
 * The type Activity data exchanger.
 */
public class ActivityDataExchanger {

  private Class<? extends Activity> clientActivity;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ActivityDataExchanger getInstance() {

        return SingletonHolder.INSTANCE;
    }

    /**
     * Gets extra.
     *
     * @param intent the intent
     * @param key    the key
     * @return the extra
     */
    public @Nullable Object getExtra(Intent intent, String key) {
//        System.out.println("ActivityDataExchanger >>> intentData : "+ key);
//        System.out.println("ActivityDataExchanger >>> intent : "+ intent.getDataString());

        Map<String, Object> intentData = getStorage().get(intent);

//        System.out.println(" ActivityDataExchanger >>> intentData : "+intentData );

        if ( intentData == null ) {

            return null;
        }

        return intentData.get(key);
    }

    /**
     * Put extra.
     *
     * @param data   the data
     * @param key    the key
     * @param intent the intent
     */
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

    /**
     * Clear data.
     *
     * @param intent the intent
     */
    public void clearData(Intent intent) {

        getStorage().remove(intent);
    }

    /**
     * Save client activity.
     *
     * @param callingActivity the calling activity
     */
    public void saveClientActivity(Class<? extends Activity> callingActivity) {
      clientActivity = callingActivity;
  }

    /**
     * Gets client activity.
     *
     * @return the client activity
     */
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

    /**
     * Set web payment view model.
     *
     * @param model the model
     */
    public  void setWebPaymentViewModel(WebPaymentViewModel model){
        this.webPaymentViewModel = model;
    }

    /**
     * Set card credentials view model.
     *
     * @param cardCredentialsViewModel the card credentials view model
     */
    public void setCardCredentialsViewModel(CardCredentialsViewModel cardCredentialsViewModel){
      this.cardCredentialsViewModel =cardCredentialsViewModel;
    }


    /**
     * Gets web payment view model.
     *
     * @return the web payment view model
     */
    public WebPaymentViewModel getWebPaymentViewModel() {
       return this.webPaymentViewModel;
    }

    /**
     * Gets card credentials view model.
     *
     * @return the card credentials view model
     */
    public CardCredentialsViewModel getCardCredentialsViewModel() {
    return cardCredentialsViewModel;
  }
}
