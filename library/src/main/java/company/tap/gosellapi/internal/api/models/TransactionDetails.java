package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class TransactionDetails {

    @SerializedName("created")
    @Expose
    @NonNull private long created;

    @SerializedName("timezone")
    @Expose
    @NonNull private String timezone;

    @SerializedName("authorization_id")
    @Expose
    @Nullable private String authorizationID;

    @SerializedName("url")
    @Expose
    @Nullable private String url;

    /**
     * @return Receipt identifier.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Defines whether receipt email should be sent.
     */
    public String getTimezone() {
        return timezone;
    }

    @Nullable
    public String getAuthorizationID() {
        return authorizationID;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
