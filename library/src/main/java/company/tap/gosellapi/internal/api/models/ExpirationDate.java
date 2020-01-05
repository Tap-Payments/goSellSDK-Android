package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Expiration date.
 */
public final class ExpirationDate implements Serializable {

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("year")
    @Expose
    private String year;

    /**
     * Gets month.
     *
     * @return Expiration month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Gets year.
     *
     * @return Expiration year
     */
    public String getYear() {
        return year;
    }
}
