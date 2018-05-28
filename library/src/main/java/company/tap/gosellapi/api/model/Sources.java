package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Sources object (field inside Customer object)
 */

public final class Sources {
    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("data")
    @Expose
    private ArrayList<Source> data;

    @SerializedName("has_more")
    @Expose
    private boolean has_more;

    @SerializedName("total_count")
    @Expose
    private int total_count;

    @SerializedName("url")
    @Expose
    private String url;

    /**
     * @return String representing the object&#8217;s type. Objects of the same type share the same value. Always has the value &#34;list&#34;.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return The list contains all payment sources that have been attached to the customer.
     */
    public ArrayList<Source> getData() {
        return data;
    }

    /**
     * @return True if this list has another page of items after this one that can be fetched.
     */
    public boolean isHas_more() {
        return has_more;
    }

    /**
     * @return Total count.
     */
    public int getTotal_count() {
        return total_count;
    }

    /**
     * @return The URL where this list can be accessed.
     */
    public String getUrl() {
        return url;
    }
}
