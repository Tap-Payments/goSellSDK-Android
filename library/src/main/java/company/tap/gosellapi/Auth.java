package company.tap.gosellapi;

import android.content.Context;

import company.tap.gosellapi.internal.api.api_service.GoSellAPI;

public class Auth {
    public static void setAuthToken(Context context, String authToken) {
        GoSellAPI.setAuthToken(context, authToken);
    }
}
