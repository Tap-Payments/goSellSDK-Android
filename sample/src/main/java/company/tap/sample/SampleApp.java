package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.Auth;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Auth.setAuthToken(this, "sk_test_0spETzD5rvIjOoM8mwqJA27i");
    }
}
