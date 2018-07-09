package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails {
    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("timezone")
    @Expose
    private String timezone;

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
}
