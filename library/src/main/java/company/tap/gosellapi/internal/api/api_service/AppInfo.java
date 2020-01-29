package company.tap.gosellapi.internal.api.api_service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.LinkedHashMap;
import java.util.Map;

import company.tap.gosellapi.BuildConfig;
import company.tap.gosellapi.internal.logger.lo;

/**
 * The type App info.
 */
public class AppInfo {
    //auth information for headers
    private static String authToken;
    private static LinkedHashMap<Object, Object> applicationInfo;
    private static String localeString = "en";
    private static TelephonyManager manager;
    private static String deviceName;

    /**
     * Sets auth token.
     *
     * @param context   the context
     * @param authToken the auth token
     */
    public static void setAuthToken(Context context, String authToken,String appId) {
        AppInfo.authToken = authToken;
        //System.out.println("appId : "+appId);
        initApplicationInfo(appId);
        manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        //deviceName =  Settings.System.getString(context.getContentResolver(), "device_name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            deviceName =  Settings.Global.getString(context.getContentResolver(), Settings.Global.DEVICE_NAME);
        } else{
            deviceName =  Settings.System.getString(context.getContentResolver(), "device_name");
        }
        lo.init(context);
    }

    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    static String getAuthToken() {
        return authToken;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public static void setLocale(String locale) {
        AppInfo.localeString = locale.length() < 2 ? locale : locale.substring(0, 2);
        AppInfo.applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
    }

    private static void initApplicationInfo(String applicationId) {

        applicationInfo = new LinkedHashMap<>();

        applicationInfo.put("app_id", applicationId); // updated based on Kalai resp.
//        applicationInfo.put("app_id", "company.tap.goSellSDKExample");
        applicationInfo.put("requirer", "SDK");
        applicationInfo.put("requirer_version", BuildConfig.VERSION_NAME);
        applicationInfo.put("requirer_os", "Android");
        applicationInfo.put("requirer_os_version", Build.VERSION.RELEASE);
        applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
        applicationInfo.put("requirer_device_name",deviceName);
        applicationInfo.put("requirer_device_type",Build.BRAND);
        applicationInfo.put("requirer_device_model",Build.MODEL);
        applicationInfo.put("sdk_version","2.4.13");
        if(manager!=null) {
            applicationInfo.put("requirer_sim_network_name", manager.getSimOperatorName());
            applicationInfo.put("requirer_sim_country_iso", manager.getSimCountryIso());
        }

    }

    /**
     * Gets locale string.
     *
     * @return the locale string
     */
    public static String getLocaleString() {
        return localeString;
    }

    /**
     * Gets application info.
     *
     * @return the application info
     */
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
        /**
         * En supported locales.
         */
        EN("en"), AR("ar");

        private String language;

        SupportedLocales(String language) {
            this.language = language;
        }

        /**
         * Find by string supported locales.
         *
         * @param localeString the locale string
         * @return the supported locales
         */
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
