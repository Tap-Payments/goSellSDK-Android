package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import java.io.Serializable;

import company.tap.gosellapi.internal.api.models.PhoneNumber;

/**
 * The type Customer.
 */
public final class Customer implements Serializable {

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

  /**
   * The Meta data.
   */
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


  /**
   * Gets identifier.
   *
   * @return the identifier
   */
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  @Nullable
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Gets middle name.
   *
   * @return the middle name
   */
  @Nullable
  public String getMiddleName() {
    return this.middleName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  @Nullable
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  @Nullable
  public String getEmail() {
    return this.email;
  }

  /**
   * Gets phone.
   *
   * @return the phone
   */
  @Nullable
  public PhoneNumber getPhone() {
    return this.phone;
  }

  /**
   * Gets meta data.
   *
   * @return the meta data
   */
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

  /**
   * The type Customer builder.
   */
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
     *
     * @param innerId the inner id
     */
    public CustomerBuilder(String innerId) {
      this.nestedIdentifier = innerId;
    }

    /**
     * First name customer builder.
     *
     * @param innerFirstName the inner first name
     * @return the customer builder
     */
    public CustomerBuilder firstName(String innerFirstName) {
      this.nestedFirstName = innerFirstName;
      return this;
    }

    /**
     * Middle name customer builder.
     *
     * @param innerMiddle the inner middle
     * @return the customer builder
     */
    public CustomerBuilder middleName(String innerMiddle) {
      this.nestedMiddleName = innerMiddle;
      return this;
    }

    /**
     * Last name customer builder.
     *
     * @param innerLastName the inner last name
     * @return the customer builder
     */
    public CustomerBuilder lastName(String innerLastName) {
      this.nestedLastName = innerLastName;
      return this;
    }

    /**
     * Email customer builder.
     *
     * @param innerEmail the inner email
     * @return the customer builder
     */
    public CustomerBuilder email(String innerEmail) {
      this.nestedEmail = innerEmail;
      return this;
    }

    /**
     * Phone customer builder.
     *
     * @param innerPhone the inner phone
     * @return the customer builder
     */
    public CustomerBuilder phone(PhoneNumber innerPhone) {
      this.nestedPhone = innerPhone;
      return this;
    }

    /**
     * Metadata customer builder.
     *
     * @param innerMetadata the inner metadata
     * @return the customer builder
     */
    public CustomerBuilder metadata(String innerMetadata) {
      this.nestedMetaData = innerMetadata;
      return this;
    }

    /**
     * Build customer.
     *
     * @return the customer
     */
    public Customer build() {
      return new Customer(nestedIdentifier, nestedFirstName, nestedMiddleName, nestedLastName,
          nestedEmail, nestedPhone, nestedMetaData);
    }
  }
  ////////////////////////// ############################ End of Builder Region ########################### ///////////////////////
}
