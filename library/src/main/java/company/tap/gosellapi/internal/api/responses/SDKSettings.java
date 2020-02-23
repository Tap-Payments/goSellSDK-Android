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

    /**
     * The type Data.
     */
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

        @SerializedName("verified_application")
        @Expose
        private boolean verified_application;

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Is livemode boolean.
         *
         * @return the boolean
         */
        public boolean isLivemode() {
            return livemode;
        }

        /**
         * Gets permissions.
         *
         * @return the permissions
         */
        public ArrayList<Permission> getPermissions() {
            return permissions;
        }

        /**
         * Gets encryption key.
         *
         * @return the encryption key
         */
        public String getEncryptionKey() {
            return encryptionKey;
        }

        /**
         * Gets merchant.
         *
         * @return the merchant
         */
        public Merchant getMerchant() {
            return merchant;
        }

        /**
         * Gets internal sdk settings.
         *
         * @return the internal sdk settings
         */
        public InternalSDKSettings getInternalSDKSettings() {
            return internalSDKSettings;
        }


        /**
         * Gets verified_application.
         *
         * @return the verified_application
         */
        public boolean isVerified_application() {
            return verified_application;
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * The type Merchant.
         */
        public static final class Merchant {

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("logo")
            @Expose
            private String logo;

            /**
             * Gets name.
             *
             * @return the name
             */
            public String getName() {
                return name;
            }

            /**
             * Gets logo.
             *
             * @return the logo
             */
            public String getLogo() {
                return logo;
            }
        }

        /**
         * The type Internal sdk settings.
         */
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

            /**
             * Gets status display duration.
             *
             * @return the status display duration
             */
            public double getStatusDisplayDuration() {
                return statusDisplayDuration;
            }

            /**
             * Gets otp resend interval.
             *
             * @return the otp resend interval
             */
            public double getOtpResendInterval() {
                return otpResendInterval;
            }

            /**
             * Gets otp resend attempts.
             *
             * @return the otp resend attempts
             */
            public int getOtpResendAttempts() {
                return otpResendAttempts;
            }
        }
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Data getData() {
        return data;
    }
}
