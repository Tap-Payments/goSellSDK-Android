package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class Customer {
    @SerializedName("email_address")
    @Expose
    private String email_address;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    /**
     * Constructor with all fields
     */
    public Customer(String email_address, String phone_number, String first_name, String last_name) {
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Customer(String email_address, String phone_number, String first_name) {
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.first_name = first_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "\n        email_address =  '" + email_address + '\'' +
                "\n        phone_number =  '" + phone_number + '\'' +
                "\n        first_name =  '" + first_name + '\'' +
                "\n        last_name =  '" + last_name + '\'' +
                "\n    }";
    }
}
