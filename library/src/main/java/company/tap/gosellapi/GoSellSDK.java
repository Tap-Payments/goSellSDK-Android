package company.tap.gosellapi;

import android.content.Context;

import org.jetbrains.annotations.Nullable;

import company.tap.gosellapi.internal.api.api_service.AppInfo;

/**
 * The type Go sell sdk.
 */
public class GoSellSDK {
    /**
     * Init.
     *
     * @param context   the context
     * @param authToken the auth token
     */
    public static void init(Context context, String authToken,String packageId) {
        AppInfo.setAuthToken(context, authToken,packageId);
    }

    /**
     * Sets locale.
     *
     * @param localeString the locale string
     */
    public static void setLocale(String localeString) {
        AppInfo.setLocale(localeString);
    }
}
