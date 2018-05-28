package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.responses.BaseResponse;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Model for {@link Card} object
 */

public final class Card implements BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("address_city")
    @Expose
    private String address_city;

    @SerializedName("address_country")
    @Expose
    private String address_country;

    @SerializedName("address_line1")
    @Expose
    private String address_line1;

    @SerializedName("address_line2")
    @Expose
    private String address_line2;

    @SerializedName("address_zip")
    @Expose
    private String address_zip;

    @SerializedName("customer")
    @Expose
    private Object customer;

    @SerializedName("funding")
    @Expose
    private String funding;

    @SerializedName("fingerprint")
    @Expose
    private String fingerprint;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("exp_month")
    @Expose
    private String exp_month;

    @SerializedName("exp_year")
    @Expose
    private String exp_year;

    @SerializedName("last4")
    @Expose
    private String last4;

    @SerializedName("name")
    @Expose
    private String name;

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
     * @return String representing the object&#8217;s type. Objects of the same type share the same value.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return City/District/Suburb/Town/Village.
     */
    public String getAddress_city() {
        return address_city;
    }

    /**
     * @return Billing address country, if provided when creating card.
     */
    public String getAddress_country() {
        return address_country;
    }

    /**
     * @return Address line 1 (Street address/PO Box/Company name).
     */
    public String getAddress_line1() {
        return address_line1;
    }

    /**
     * @return Address line 2 (Apartment/Suite/Unit/Building).
     */
    public String getAddress_line2() {
        return address_line2;
    }

    /**
     * @return ZIP or postal code
     */
    public String getAddress_zip() {
        return address_zip;
    }

    /**
     * @return Card brand. Can be Visa, American Express, MasterCard, Discover, JCB, Diners Club, or Unknown.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @return The customer that this card belongs to. This attribute will not be in the card object if the card belongs to an account or recipient instead.
     */
    public Object getCustomer() {
        return customer;
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
     * @return Uniquely identifies this particular card number. You can use this attribute to check whether two customers who&#8217;ve signed up with you are using the same card number, for example.
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * @return Card funding type. Can be credit, debit, prepaid, or unknown.
     */
    public String getFunding() {
        return funding;
    }

    /**
     * @return The last 4 digits of the card.
     */
    public String getLast4() {
        return last4;
    }

    /**
     * @return Cardholder name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Card BIN number.
     */
    public String getBin() {
        return bin;
    }
}
