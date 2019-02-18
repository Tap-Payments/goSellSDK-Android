package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.Permission;

/**
 * Created by eugene.goltsev on 17.04.2018.
 * <br>
 * Model for {@link SDKSettings} object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class SDKSettings implements BaseResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public static final class Data {
        @SerializedName("live_mode")
        @Expose
        private boolean livemode;

        @SerializedName("permissions")
        @Expose
        private ArrayList<Permission> permissions;

        @SerializedName("encryption_key")
        @Expose
        private String encryptionKey;

        @SerializedName("merchant")
        @Expose
        private Merchant merchant;

        @SerializedName("sdk_settings")
        @Expose
        private InternalSDKSettings internalSDKSettings;

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        public boolean isLivemode() {
            return livemode;
        }

        public ArrayList<Permission> getPermissions() {
            return permissions;
        }

        public String getEncryptionKey() {
            return encryptionKey;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public InternalSDKSettings getInternalSDKSettings() {
            return internalSDKSettings;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        public static final class Merchant {

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("logo")
            @Expose
            private String logo;

            public String getName() {
                return name;
            }

            public String getLogo() {
                return logo;
            }
        }

        public static final class InternalSDKSettings {

            @SerializedName("status_display_duration")
            @Expose
            private int statusDisplayDuration;

            @SerializedName("otp_resend_interval")
            @Expose
            private double otpResendInterval;

            @SerializedName("otp_resend_attempts")
            @Expose
            private int otpResendAttempts;

            public double getStatusDisplayDuration() {
                return statusDisplayDuration;
            }

            public double getOtpResendInterval() {
                return otpResendInterval;
            }

            public int getOtpResendAttempts() {
                return otpResendAttempts;
            }
        }
    }

    public Data getData() {
        return data;
    }
}
