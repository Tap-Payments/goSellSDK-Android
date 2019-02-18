package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.URLStatus;

public class TrackingURL {

    @SerializedName("status")
    @Expose
    @NonNull URLStatus status = URLStatus.PENDING;

    @SerializedName("url")
    @Expose
    @Nullable String url;

    public TrackingURL(@NonNull String url) {

        this.url = url;
        this.status = URLStatus.PENDING;
    }
}
