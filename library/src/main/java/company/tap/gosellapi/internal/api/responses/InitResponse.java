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

        @SerializedName("verified_application")
        @Expose
        private String verified_application;

        @SerializedName("encryption_key")
        @Expose
        private String encryption_key;

        public boolean isLivemode() {
            return livemode;
        }

        public ArrayList<String> getPermissions() {
            return permissions;
        }

        public String getVerified_application() {
            return verified_application;
        }

        public String getEncryption_key() {
            return encryption_key;
        }
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
