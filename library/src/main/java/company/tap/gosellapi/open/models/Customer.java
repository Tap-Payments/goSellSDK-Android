package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import company.tap.gosellapi.internal.api.models.PhoneNumber;

public final class Customer {

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

  @SerializedName("metadata")
  String metaData;

//  Constructor is private to prevent access from client app, it must be through inner Builder class only
  private Customer(String identifier, String firstName, String middleName, String lastName,
                   String email, PhoneNumber phone, String metaData) {
    this.identifier = identifier;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.metaData = metaData;
  }


  public String getIdentifier() {
    return this.identifier;
  }

  @Nullable
  public String getFirstName() {
    return this.firstName;
  }

  @Nullable
  public String getMiddleName() {
    return this.middleName;
  }

  @Nullable
  public String getLastName() {
    return this.lastName;
  }

  @Nullable
  public String getEmail() {
    return this.email;
  }

  @Nullable
  public PhoneNumber getPhone() {
    return this.phone;
  }

  @Nullable
  public String getMetaData() {
    return this.metaData;
  }

  @Override
  public String toString() {
    return "Customer {" +
        "\n        id =  '" + identifier + '\'' +
        "\n        email =  '" + email + '\'' +
        "\n        first_name =  '" + firstName + '\'' +
        "\n        middle_name =  '" + middleName + '\'' +
        "\n        last_name =  '" + lastName + '\'' +
        "\n        phone  country code =  '" + phone.getCountryCode() + '\'' +
        "\n        phone number =  '" + phone.getNumber() + '\'' +
        "\n        metadata =  '" + metaData + '\'' +
        "\n    }";
  }

  ////////////////////////// ############################ Start of Builder Region ########################### ///////////////////////

  public static class CustomerBuilder {

    private String nestedIdentifier;
    private String nestedFirstName;
    private String nestedMiddleName;
    private String nestedLastName;
    private String nestedEmail;
    private PhoneNumber nestedPhone;
    private String nestedMetaData;

    /**
     * Client app can create a customer object with only customer id
     */
    public CustomerBuilder(String innerId) {
      this.nestedIdentifier = innerId;
    }

    public CustomerBuilder firstName(String innerFirstName) {
      this.nestedFirstName = innerFirstName;
      return this;
    }

    public CustomerBuilder middleName(String innerMiddle) {
      this.nestedMiddleName = innerMiddle;
      return this;
    }

    public CustomerBuilder lastName(String innerLastName) {
      this.nestedLastName = innerLastName;
      return this;
    }

    public CustomerBuilder email(String innerEmail) {
      this.nestedEmail = innerEmail;
      return this;
    }

    public CustomerBuilder phone(PhoneNumber innerPhone) {
      this.nestedPhone = innerPhone;
      return this;
    }

    public CustomerBuilder metadata(String innerMetadata) {
      this.nestedMetaData = innerMetadata;
      return this;
    }

    public Customer build() {
      return new Customer(nestedIdentifier, nestedFirstName, nestedMiddleName, nestedLastName,
          nestedEmail, nestedPhone, nestedMetaData);
    }
  }
  ////////////////////////// ############################ End of Builder Region ########################### ///////////////////////
}
