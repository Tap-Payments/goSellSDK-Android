package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        GoSellSDK.init(this, "sk_test_0spETzD5rvIjOoM8mwqJA27i");
    }
}
