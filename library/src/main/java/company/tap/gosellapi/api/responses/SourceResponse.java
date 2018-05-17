package company.tap.gosellapi.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Source object
 */

public final class SourceResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("exp_month")
    @Expose
    private String exp_month;

    @SerializedName("exp_year")
    @Expose
    private String exp_year;

    @SerializedName("last4")
    @Expose
    private String last4;

    @SerializedName("address_city")
    @Expose
    private String address_city;

    @SerializedName("address_country")
    @Expose
    private String address_country;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("bin")
    @Expose
    private String bin;

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
     * @return Last 4 digits.
     */
    public String getLast4() {
        return last4;
    }

    /**
     * @return Address city of a card.
     */
    public String getAddress_city() {
        return address_city;
    }

    /**
     * @return Address country of a card.
     */
    public String getAddress_country() {
        return address_country;
    }

    /**
     * @return Brand of a card.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @return BIN number of a card.
     */
    public String getBin() {
        return bin;
    }

    @Override
    public String toString() {
        return "Source {" +
                "\n        id =  '" + id + '\'' +
                "\n        object =  '" + object + '\'' +
                "\n        exp_month =  '" + exp_month + '\'' +
                "\n        exp_year =  '" + exp_year + '\'' +
                "\n        last4 =  '" + last4 + '\'' +
                "\n        address_city =  '" + address_city + '\'' +
                "\n        address_country =  '" + address_country + '\'' +
                "\n        brand =  '" + brand + '\'' +
                "\n        bin =  '" + bin + '\'' +
                "\n    }";
    }
}
