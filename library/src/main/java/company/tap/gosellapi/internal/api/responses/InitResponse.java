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
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
