package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class ExpirationDate {

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("year")
    @Expose
    private String year;

    /**
     * @return Expiration month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @return Expiration year
     */
    public String getYear() {
        return year;
    }
}
