package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.Auth;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Auth.setAuthToken(this, "YOUR_SANDBOX_SECRET_KEY");
    }
}
