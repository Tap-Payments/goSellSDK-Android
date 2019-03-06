package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import company.tap.gosellapi.internal.api.enums.AuthenticationRequirer;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.AuthenticationType;

/**
 * The type Authenticate.
 */
public final class Authenticate implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("type")
    @Expose
    private AuthenticationType type;

    @SerializedName("by")
    @Expose
    private AuthenticationRequirer by;

    @SerializedName("status")
    @Expose
    private AuthenticationStatus status;

    @SerializedName("retry_attempt")
    @Expose
    private int retryAttempt;

    @SerializedName("url")
    @Expose
    @Nullable private String url;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("authenticated")
    @Expose
    @Nullable private Long authenticated;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("value")
    @Expose
    private String value;

    /**
     * Gets id.
     *
     * @return Unique authentication identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets object.
     *
     * @return Object type. Here "authenticate" always.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets type.
     *
     * @return Authentication type.
     */
    public AuthenticationType getType() {
        return type;
    }

    /**
     * Gets by.
     *
     * @return Defines the initiator of the authentication.
     */
    public AuthenticationRequirer getBy() {
        return by;
    }

    /**
     * Gets status.
     *
     * @return Authentication status.
     */
    public AuthenticationStatus getStatus() {
        return status;
    }

    /**
     * Gets retry attempt.
     *
     * @return Number of attempts per code.
     */
    public int getRetryAttempt() {
        return retryAttempt;
    }

    /**
     * Gets url.
     *
     * @return The URL to perform the authentication.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets created.
     *
     * @return Authentication creation date.
     */
    public long getCreated() {
        return created;
    }

    /**
     * Gets authenticated.
     *
     * @return Authentication date (when was authenticated)
     */
    public long getAuthenticated() {
        return authenticated;
    }

    /**
     * Gets count.
     *
     * @return Number of used authentication attempts.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets value.
     *
     * @return In case of OTP - masked mobile number where SMS was dispatched.
     */
    public String getValue() {
        return value;
    }
}
