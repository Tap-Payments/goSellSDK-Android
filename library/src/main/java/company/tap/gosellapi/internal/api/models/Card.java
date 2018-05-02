package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class Card {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("last4")
    @Expose
    private String last4;

    @SerializedName("exp_month")
    @Expose
    private String exp_month;

    @SerializedName("exp_year")
    @Expose
    private String exp_year;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("bin")
    @Expose
    private String bin;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public String getLast4() {
        return last4;
    }

    public String getExp_month() {
        return exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getBin() {
        return bin;
    }
}
