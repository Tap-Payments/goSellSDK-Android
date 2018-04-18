package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.internal.api.api_service.GoSellAPI;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        GoSellAPI.setAuthToken(this, "YOUR_SANDBOX_SECRET_KEY");
    }
}
