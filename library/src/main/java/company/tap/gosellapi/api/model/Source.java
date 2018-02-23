package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Source object
 */

public final class Source {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object_type")
    @Expose
    private String object_type;

    @SerializedName("exp_month")
    @Expose
    private String exp_month;

    @SerializedName("exp_year")
    @Expose
    private String exp_year;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("cvc")
    @Expose
    private String cvc;

    /**
     * Constructor with all fields, except id
     */
    public Source(String object_type, String exp_month, String exp_year, String number, String cvc) {
        this.object_type = object_type;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.number = number;
        this.cvc = cvc;
    }

    /**
     * Constructor with id field only (token id, card id etc.)
     */
    public Source(String id) {
        this.id = id;
    }

    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Two digit number representing the card&#8217;s expiration month.
     */
    public String getExp_month() {
        return exp_month;
    }

    /**
     * @return Four digit number representing the card&#8217;s expiration year.
     */
    public String getExp_year() {
        return exp_year;
    }

    /**
     * @return The card number, as a string without any separators.
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return Card security code.
     */
    public String getCvc() {
        return cvc;
    }
}
