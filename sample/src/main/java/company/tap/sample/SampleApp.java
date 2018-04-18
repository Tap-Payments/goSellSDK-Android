package company.tap.sample;

import android.app.Application;

import company.tap.gosellapi.internal.api.api_service.GoSellAPI;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        GoSellAPI.setAuthToken(this, "sk_test_0spETzD5rvIjOoM8mwqJA27i");
    }
}
