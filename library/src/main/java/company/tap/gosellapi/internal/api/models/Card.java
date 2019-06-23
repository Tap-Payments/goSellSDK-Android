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

    @SerializedName("first_six")
    @Expose
    private String firstSix;

    @SerializedName("last_four")
    @Expose
    private String lastFour;

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

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets first 6.
     *
     * @return the first 6
     */
    public String getFirstSix() {
        return firstSix;
    }


    /**
     * Gets last 4.
     *
     * @return the last 4
     */
    public String getLast4() {
        return lastFour;
    }

    /**
     * Gets exp month.
     *
     * @return the exp month
     */
    public String getExp_month() {
        return exp_month;
    }

    /**
     * Gets exp year.
     *
     * @return the exp year
     */
    public String getExp_year() {
        return exp_year;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets bin.
     *
     * @return the bin
     */
    public String getBin() {
        return bin;
    }
}
