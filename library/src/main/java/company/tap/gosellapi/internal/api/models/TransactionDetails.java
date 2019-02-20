package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Transaction details.
 */
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
     * Gets created.
     *
     * @return Receipt identifier.
     */
    public long getCreated() {
        return created;
    }

    /**
     * Gets timezone.
     *
     * @return Defines whether receipt email should be sent.
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Gets authorization id.
     *
     * @return the authorization id
     */
    @Nullable
    public String getAuthorizationID() {
        return authorizationID;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    @Nullable
    public String getUrl() {
        return url;
    }
}
