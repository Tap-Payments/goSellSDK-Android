package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.URLStatus;

/**
 * The type Tracking url.
 */
public class TrackingURL {

    /**
     * The Status.
     */
    @SerializedName("status")
    @Expose
    @NonNull URLStatus status = URLStatus.PENDING;

    /**
     * The Url.
     */
    @SerializedName("url")
    @Expose
    @Nullable String url;

    /**
     * Instantiates a new Tracking url.
     *
     * @param url the url
     */
    public TrackingURL(@NonNull String url) {

        this.url = url;
        this.status = URLStatus.PENDING;
    }
}
