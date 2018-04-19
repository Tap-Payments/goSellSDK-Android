package company.tap.gosellapi;

import android.content.Context;

import company.tap.gosellapi.internal.api.api_service.RetrofitHelper;

public class Auth {
    public static void setAuthToken(Context context, String authToken) {
        RetrofitHelper.setAuthToken(context, authToken);
    }
}
