package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.responses.BaseResponse;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Model for Customer object
 */

public final class Customer implements BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("live_mode")
    @Expose
    private boolean livemode;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    @SerializedName("currency")
    @Expose
    private String currency;

    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return String representing the object&#8217;s type. Objects of the same type share the same value.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return Customer name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Three-letter ISO code for the currency the customer can be charged
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return An arbitrary string attached to the object. Often useful for displaying to users.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The customer&#8217;s email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Customer phone (including extension).
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return HashMap of key/value pairs that you can attach to an object. It can be useful for storing additional information about the object in a structured format.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    /**
     * @return Time at which the object was created. Measured in seconds since the Unix epoch.customer.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Flag indicating whether the object exists in live mode or test mode.
     */
    public boolean isLivemode() {
        return livemode;
    }
}
