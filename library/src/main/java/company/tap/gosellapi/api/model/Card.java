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


    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("cvc_check")
    @Expose
    private int cvc_check;

    @SerializedName("dynamic_last4")
    @Expose
    private int dynamic_last4;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    @SerializedName("tokenization_method")
    @Expose
    private String tokenization_method;

    @SerializedName("address_line1_check")
    @Expose
    private String address_line1_check;

    @SerializedName("address_state")
    @Expose
    private String address_state;

    @SerializedName("address_zip_check")
    @Expose
    private String address_zip_check;

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
     * @return If address_line1 was provided, results of the check: pass, fail, unavailable, or unchecked.
     */
    public String getAddress_line1_check() {
        return address_line1_check;
    }

    /**
     * @return Address line 2 (Apartment/Suite/Unit/Building).
     */
    public String getAddress_line2() {
        return address_line2;
    }

    /**
     * @return State/County/Province/Region.
     */
    public String getAddress_state() {
        return address_state;
    }

    /**
     * @return ZIP or postal code
     */
    public String getAddress_zip() {
        return address_zip;
    }

    /**
     * @return If address_zip was provided, results of the check: pass, fail, unavailable, or unchecked.
     */
    public String getAddress_zip_check() {
        return address_zip_check;
    }

    /**
     * @return Card brand. Can be Visa, American Express, MasterCard, Discover, JCB, Diners Club, or Unknown.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @return Two-letter ISO code representing the country of the card. You could use this attribute to get a sense of the international breakdown of cards you&#8217;ve collected.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return The customer that this card belongs to. This attribute will not be in the card object if the card belongs to an account or recipient instead.
     */
    public Object getCustomer() {
        return customer;
    }

    /**
     * @return If a CVC was provided, results of the check: pass, fail, unavailable, or unchecked.
     */
    public int getCvc_check() {
        return cvc_check;
    }

    /**
     * @return (For tokenized numbers only.) The last four digits of the device account number.
     */
    public int getDynamic_last4() {
        return dynamic_last4;
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
     * @return HashMap of key/value pairs that you can attach to an object. It can be useful for storing additional information about the object in a structured format.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    /**
     * @return Cardholder name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return If the card number is tokenized, this is the method that was used. Can be apple_pay or android_pay.
     */
    public String getTokenization_method() {
        return tokenization_method;
    }

    /**
     * @return Card BIN number.
     */
    public String getBin() {
        return bin;
    }
}
