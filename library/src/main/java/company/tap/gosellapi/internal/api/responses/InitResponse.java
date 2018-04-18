package company.tap.gosellapi.internal.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Model for {@link InitResponse} object
 */

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
    }
}
