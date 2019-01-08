package company.tap.gosellapi.internal.api.api_service;

import android.content.Context;
import android.os.Build;

import java.util.LinkedHashMap;
import java.util.Map;

import company.tap.gosellapi.BuildConfig;
import company.tap.gosellapi.internal.logger.lo;

public class AppInfo {
    //auth information for headers
    private static String authToken;
    private static LinkedHashMap<Object, Object> applicationInfo;
    private static String localeString = "en";

    public static void setAuthToken(Context context, String authToken) {
        AppInfo.authToken = authToken;
        initApplicationInfo(context.getPackageName());

        lo.init(context);
    }

    static String getAuthToken() {
        return authToken;
    }

    public static void setLocale(String locale) {
        AppInfo.localeString = locale.length() < 2 ? locale : locale.substring(0, 2);
        AppInfo.applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
    }

    private static void initApplicationInfo(String applicationId) {
        applicationInfo = new LinkedHashMap<>();

//        applicationInfo.put("app_id", applicationId); // updated based on Kalai resp.
        applicationInfo.put("app_id", "company.tap.goSellSDKExample");
        applicationInfo.put("requirer", "SDK");
        applicationInfo.put("requirer_version", BuildConfig.VERSION_NAME);
        applicationInfo.put("requirer_os", "Android");
        applicationInfo.put("requirer_os_version", Build.VERSION.RELEASE);
        applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
    }

    public static String getLocaleString() {
        return localeString;
    }

    static String getApplicationInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : applicationInfo.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("|");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private enum SupportedLocales {
        EN("en");

        private String language;

        SupportedLocales(String language) {
            this.language = language;
        }

        static SupportedLocales findByString(String localeString) {
            for (SupportedLocales locale : values()) {
                if (locale.language.equalsIgnoreCase(localeString)) {
                    return locale;
                }
            }

            return EN;
        }
    }
}
