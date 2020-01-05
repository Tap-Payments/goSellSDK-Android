package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Receipt.
 */
public final class Receipt implements Serializable {

    @SerializedName("id")
    @Expose
    @Nullable private String id;

    @SerializedName("email")
    @Expose
    private boolean email;

    @SerializedName("sms")
    @Expose
    private boolean sms;

    /**
     * Instantiates a new Receipt.
     *
     * @param email the email
     * @param sms   the sms
     */
    public Receipt(boolean email, boolean sms) {
        this.email = email;
        this.sms = sms;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Is email boolean.
     *
     * @return the boolean
     */
    public boolean isEmail() {
        return email;
    }

    /**
     * Is sms boolean.
     *
     * @return the boolean
     */
    public boolean isSms() {
        return sms;
    }
}
