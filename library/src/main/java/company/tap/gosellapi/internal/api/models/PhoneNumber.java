package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneNumber {

    @SerializedName("ISDNumber")
    @Expose
    private String ISDNumber;

    @SerializedName("number")
    @Expose
    private String number;

    public PhoneNumber(String ISDNumber, String number) {
        this.ISDNumber = ISDNumber;
        this.number = number;
    }

    public String getISDNumber() {
        return ISDNumber;
    }

    public String getNumber() {
        return number;
    }
}
