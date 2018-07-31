package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {

    @SerializedName("id")
    @Expose
    private String identifier;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("middle_name")
    @Expose
    private String middleName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private PhoneNumber phone;

    public CustomerInfo(String id) {
        this.identifier = id;
    }

    public CustomerInfo(String firstName, String lastName, String email, PhoneNumber phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public CustomerInfo(String firstName, String email, PhoneNumber phone) {
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "CustomerInfo {" +
                "\n        id =  '" + identifier + '\'' +
                "\n        email =  '" + email + '\'' +
                "\n        first_name =  '" + firstName + '\'' +
                "\n        middle_name =  '" + middleName + '\'' +
                "\n        last_name =  '" + lastName + '\'' +
                "\n        phone  country code =  '" + phone.getCountryCode() + '\'' +
                "\n        phone number =  '" + phone.getNumber() + '\'' +
                "\n    }";
    }
}
