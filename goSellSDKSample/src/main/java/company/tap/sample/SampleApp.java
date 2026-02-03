package company.tap.sample;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import company.tap.gosellapi.GoSellSDK;
import company.tap.gosellapi.open.controllers.ThemeObject;

public class SampleApp extends Application{
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();

          GoSellSDK.init(this, "sk_XXXXXXXXXXXXXXXXXXXXXXXX","company.tap.goSellSDKExample");                 // to be replaced by merchant
          GoSellSDK.setLocale("en");
    }
}
