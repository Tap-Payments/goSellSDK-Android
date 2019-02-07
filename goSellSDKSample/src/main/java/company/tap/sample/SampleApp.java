package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY"); // original
//        GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY"); // from waqas just for test
//        GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY");                 // from Kalai just for test  // sandbox test
        GoSellSDK.init(this, "YOUR_PRODUCTION_SECRET_KEY"); // from Kalai production

    }
}
