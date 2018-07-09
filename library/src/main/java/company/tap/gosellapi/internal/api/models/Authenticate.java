package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthenticationRequirer;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.AuthenticationType;

public class Authenticate {

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
    private String url;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("authenticated")
    @Expose
    private long authenticated;
}
