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

          GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY","company.tap.goSellSDKExample");                 // to be replaced by merchant
          GoSellSDK.setLocale(ThemeObject.getInstance().getSdkLanguage());

    }
}
