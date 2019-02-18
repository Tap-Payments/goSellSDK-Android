package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthenticationRequirer;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.AuthenticationType;

public final class Authenticate {

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
     * @return Unique authentication identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Object type. Here "authenticate" always.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return Authentication type.
     */
    public AuthenticationType getType() {
        return type;
    }

    /**
     * @return Defines the initiator of the authentication.
     */
    public AuthenticationRequirer getBy() {
        return by;
    }

    /**
     * @return Authentication status.
     */
    public AuthenticationStatus getStatus() {
        return status;
    }

    /**
     * @return Number of attempts per code.
     */
    public int getRetryAttempt() {
        return retryAttempt;
    }

    /**
     * @return The URL to perform the authentication.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return Authentication creation date.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Authentication date (when was authenticated)
     */
    public long getAuthenticated() {
        return authenticated;
    }

    /**
     * @return Number of used authentication attempts.
     */
    public int getCount() {
        return count;
    }

    /**
     * @return In case of OTP - masked mobile number where SMS was dispatched.
     */
    public String getValue() {
        return value;
    }
}
