package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AddressFormat;
import company.tap.gosellapi.internal.api.enums.AddressType;

/**
 * The type Address.
 */
public final class Address {

    @SerializedName("format")
    @Expose
    private AddressFormat format;

    @SerializedName("type")
    @Expose
    private AddressType type;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("line1")
    @Expose
    private String line1;

    @SerializedName("line2")
    @Expose
    private String line2;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("zip_code")
    @Expose
    private String zipCode;

    @SerializedName("country_governorate")
    @Expose
    private String countryGovernorate;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("block")
    @Expose
    private String block;

    @SerializedName("avenue")
    @Expose
    private String avenue;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("building_house")
    @Expose
    private String buildingHouse;

    @SerializedName("floor")
    @Expose
    private String floor;

    @SerializedName("office")
    @Expose
    private String office;

    @SerializedName("po_box")
    @Expose
    private String poBox;

    @SerializedName("postal_code")
    @Expose
    private String postalCode;
}
