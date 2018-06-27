package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneNumber {

    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("number")
    @Expose
    private String number;

    public PhoneNumber(String ISDNumber, String number) {
        this.countryCode = ISDNumber;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }
}
