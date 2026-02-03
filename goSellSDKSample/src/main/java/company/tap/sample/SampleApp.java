package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;
import com.crashlytics.android.Crashlytics;

import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import io.fabric.sdk.android.Fabric;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

          GoSellSDK.init(this, "sk_XXXXXXXXXX");                 // from Kalai just for test  // sandbox test
          GoSellSDK.setLocale(ThemeObject.getInstance().getSdkLanguage());



    }
}
