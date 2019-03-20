package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Phone number.
 */
public class PhoneNumber implements Serializable {

    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("number")
    @Expose
    private String number;

    /**
     * Instantiates a new Phone number.
     *
     * @param ISDNumber the isd number
     * @param number    the number
     */
    public PhoneNumber(String ISDNumber, String number) {
        this.countryCode = ISDNumber;
        this.number = number;
    }

    /**
     * Gets country code.
     *
     * @return the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }
}
