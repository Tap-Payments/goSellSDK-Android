package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.GoSellSDK;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        GoSellSDK.init(this, "sk_test_0spETzD5rvIjOoM8mwqJA27i"); // original
//        GoSellSDK.init(this, "sk_test_XKokBfNWv6FIYuTMg5sLPjhJ"); // from waqas just for test
        GoSellSDK.init(this, "sk_test_kovrMB0mupFJXfNZWx6Etg5y");                 // from Kalai just for test  // sandbox test
//        GoSellSDK.init(this, "sk_live_QglH8V7Fw6NPAom4qRcynDK2"); // from Kalai production

    }
}
