package company.tap.gosellapi.open.enums;

import com.google.gson.annotations.SerializedName;

public enum AppearanceMode {

    /**
     * Windowed mode with translucent
     */
    @SerializedName("WINDOWED_MODE")            WINDOWED_MODE,
    /**
     * Full screen mode
     */
    @SerializedName("FULLSCREEN_MODE")          FULLSCREEN_MODE,
}
