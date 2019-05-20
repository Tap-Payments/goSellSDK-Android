package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;

import company.tap.gosellapi.open.controllers.ThemeObject;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

          GoSellSDK.init(this, "sk_test_kovrMB0mupFJXfNZWx6Etg5y","company.tap.goSellSDKExample");                 // to be replaced by merchant
          GoSellSDK.setLocale(ThemeObject.getInstance().getSdkLanguage());

    }
}
