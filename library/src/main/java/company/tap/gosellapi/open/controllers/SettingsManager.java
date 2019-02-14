package company.tap.gosellapi.open.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;


import company.tap.gosellapi.R;

public class SettingsManager {

    private SharedPreferences pref ;
    private Context context;

    public void setPref(Context ctx){
        context = ctx;
        if(pref==null) pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SettingsManager getInstance(){
        return SingleInstanceAdmin.instance;
    }

   //////////////////////////////////////   Get Appearance Settings ////////////////////////////////

    /**
     * @return Windowed - FullScreen - Default
     */
   public String getAppearanceMode(String key){
       String appearanceMode = pref.getString(key, "");
       return appearanceMode;
   }

   public boolean statusPopup(String key){
       boolean status = pref.getBoolean(key, false);
       return status;
   }

   public String getHeaderFont(String key){
       String appearanceMode = pref.getString(key, "");
       return appearanceMode;
   }

    public String getHeaderTextColor(String key){
        String appearanceMode = pref.getString(key, "");
        return appearanceMode;
    }

    public String getHeaderBackgroundColor(String key){
        String headerBackgroundColor = pref.getString(key, "");
        return headerBackgroundColor;
    }

    //////////////////////////////////////////  CARD INPUT FIELDS SECTION ////////////////////////

    public String getCardInputFieldsFont(String key){
        String font = pref.getString(key, "");
        return font;
    }


    public String getCardInputFieldsTextColor(String key){
        String textColor = pref.getString(key, "");
        return textColor;
    }

    public String getCardInputFieldsInvalidTextColor(String key){
        String color = pref.getString(key, "");
        return color;
    }


    public String getCardInputFieldsPlaceHolderTextColor(String key){
        String color = pref.getString(key, "");
        return color;
    }


    public String getCardInputFieldsDescriptionFont(String key){
        String font = pref.getString(key, "");
        return font;
    }


    public String getCardInputFieldsDescriptionColor(String key){
        String color = pref.getString(key, "");
        return color;
    }

    //////////////////////////////////////////  TAP BUTTON SECTION ////////////////////////

    public int getTapButtonEnabledBackgroundColor(String key){
        String color = pref.getString(key, "");
        return extractBackgroundColorCode(color);
    }


    public int getTapButtonDisabledBackgroundColor(String key){
        String color = pref.getString(key, "");
        return extractBackgroundColorCode(color);
    }


    public String getTapButtonFont(String key){
        String font = pref.getString(key, "");
        return font;
    }


    public int getTapButtonDisabledTitleColor(String key){
        String color = pref.getString(key, "");
        return  extractTitleColorCode(color);
    }



    public int getTapButtonEnabledTitleColor(String key){
        String color = pref.getString(key, "");
        return extractTitleColorCode(color);
    }

    public String getTapButtonHeight(String key){
        String height = pref.getString(key, "");
        return height;
    }



    private int extractBackgroundColorCode(String color) {
        if(color.trim().equalsIgnoreCase("")) return R.color.vibrant_green_pressed;
        return Color.parseColor(color.split("_")[1]);
    }

    private int extractTitleColorCode(String color) {
        if(color.trim().equalsIgnoreCase("")) return R.color.white;
        return Color.parseColor(color.split("_")[1]);
    }

























    private static class SingleInstanceAdmin{
        static SettingsManager instance = new SettingsManager();
    }
}
