package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eugene.goltsev on 17.04.2018.
 * <br>
 * Model for {@link InitResponse} object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class InitResponse implements BaseResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private Data data;

    public static final class Data {
        @SerializedName("livemode")
        @Expose
        private boolean livemode;

        @SerializedName("permissions")
        @Expose
        private ArrayList<String> permissions;

        @SerializedName("encryption_key")
        @Expose
        private String encryption_key;

        @SerializedName("merchant")
        @Expose
        private Merchant merchant;

        @SerializedName("sdk_settings")
        @Expose
        private SDK_Settings sdk_settings;

        public boolean isLivemode() {
            return livemode;
        }

        public ArrayList<String> getPermissions() {
            return permissions;
        }

        public String getEncryption_key() {
            return encryption_key;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public SDK_Settings getSdk_settings() {
            return sdk_settings;
        }

        public static final class Merchant {
            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("logo")
            @Expose
            private String logo;

            @SerializedName("currency_code")
            @Expose
            private String currency_code;

            @SerializedName("supported_currencies")
            @Expose
            private ArrayList<String> supported_currencies;

            public String getName() {
                return name;
            }

            public String getLogo() {
                return logo;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public ArrayList<String> getSupported_currencies() {
                return supported_currencies;
            }
        }

        public static final class SDK_Settings {
            @SerializedName("status_popup_duration")
            @Expose
            private int status_popup_duration;

            @SerializedName("resend_interval")
            @Expose
            private int resend_interval;

            @SerializedName("resend_number_attempts")
            @Expose
            private int resend_number_attempts;

            public int getStatus_popup_duration() {
                return status_popup_duration;
            }

            public int getResend_interval() {
                return resend_interval;
            }

            public int getResend_number_attempts() {
                return resend_number_attempts;
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
