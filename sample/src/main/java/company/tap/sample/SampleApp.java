package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY");
    }
}
