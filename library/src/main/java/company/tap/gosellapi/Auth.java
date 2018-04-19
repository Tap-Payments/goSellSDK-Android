package company.tap.gosellapi;

import android.content.Context;

import company.tap.gosellapi.internal.api.api_service.AppInfo;

public class Auth {
    public static void setAuthToken(Context context, String authToken) {
        AppInfo.setAuthToken(context, authToken);
    }

    public static void setLocale(String localeString) {
        AppInfo.setLocale(localeString);
    }
}
