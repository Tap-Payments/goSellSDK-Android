package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Transaction details.
 */
public final class TransactionDetails implements Serializable {

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
    @SerializedName("order")
    @Expose
    @Nullable private Order order;
    @SerializedName("expiry")
    @Expose
    @Nullable private Expiry expiry;
    @SerializedName("asynchronous")
    @Expose
    @Nullable private boolean asynchronous;

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
    /**
     * Gets created.
     *
     * @return Order order.
     */
    @Nullable
    public Order getOrder() {
        return order;
    }
    /**
     * Gets created.
     *
     * @return Expiry expiry.
     */
    @Nullable
    public Expiry getExpiry() {
        return expiry;
    }
    /**
     * Gets created.
     *
     * @return boolean asynchronous.
     */
    @Nullable
    public boolean isAsynchronous() {
        return asynchronous;
    }

}
