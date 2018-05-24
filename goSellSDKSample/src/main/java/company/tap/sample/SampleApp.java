package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        GoSellSDK.init(this, "YOUR_SANDBOX_SECRET_KEY");
    }
}
