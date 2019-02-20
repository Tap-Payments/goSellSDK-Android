package company.tap.gosellapi.open.controllers;



        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.preference.PreferenceManager;


        import company.tap.gosellapi.R;

/**
 * The type Settings manager.
 */
public class SettingsManager {

        private SharedPreferences pref ;
        private Context context;

        /**
         * Set pref.
         *
         * @param ctx the ctx
         */
        public void setPref(Context ctx){
        context = ctx;
        if(pref==null) pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        /**
         * Get instance settings manager.
         *
         * @return the settings manager
         */
        public static SettingsManager getInstance(){
        return SingleInstanceAdmin.instance;
        }

        //////////////////////////////////////   Get Appearance Settings ////////////////////////////////

        /**
         * Get appearance mode string.
         *
         * @param key the key
         * @return Windowed - FullScreen - Default
         */
        public String getAppearanceMode(String key){
        String appearanceMode = pref.getString(key, "");
        return appearanceMode;
        }

        /**
         * Status popup boolean.
         *
         * @param key the key
         * @return the boolean
         */
        public boolean statusPopup(String key){
        boolean status = pref.getBoolean(key, false);
        return status;
        }

        /**
         * Get header font string.
         *
         * @param key the key
         * @return the string
         */
        public String getHeaderFont(String key){
        String appearanceMode = pref.getString(key, "");
        return appearanceMode;
        }

        /**
         * Get header text color string.
         *
         * @param key the key
         * @return the string
         */
        public String getHeaderTextColor(String key){
        String appearanceMode = pref.getString(key, "");
        return appearanceMode;
        }

        /**
         * Get header background color string.
         *
         * @param key the key
         * @return the string
         */
        public String getHeaderBackgroundColor(String key){
        String headerBackgroundColor = pref.getString(key, "");
        return headerBackgroundColor;
        }

        //////////////////////////////////////////  CARD INPUT FIELDS SECTION ////////////////////////

        /**
         * Get card input fields font string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsFont(String key){
        String font = pref.getString(key, "");
        return font;
        }


        /**
         * Get card input fields text color string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsTextColor(String key){
        String textColor = pref.getString(key, "");
        return textColor;
        }

        /**
         * Get card input fields invalid text color string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsInvalidTextColor(String key){
        String color = pref.getString(key, "");
        return color;
        }


        /**
         * Get card input fields place holder text color string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsPlaceHolderTextColor(String key){
        String color = pref.getString(key, "");
        return color;
        }


        /**
         * Get card input fields description font string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsDescriptionFont(String key){
        String font = pref.getString(key, "");
        return font;
        }


        /**
         * Get card input fields description color string.
         *
         * @param key the key
         * @return the string
         */
        public String getCardInputFieldsDescriptionColor(String key){
        String color = pref.getString(key, "");
        return color;
        }






















        private static class SingleInstanceAdmin{
                /**
                 * The Instance.
                 */
                static SettingsManager instance = new SettingsManager();
        }
        }

