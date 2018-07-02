package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.tapcardvalidator_android.CardBrand;

public class TokenizedCard {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("last_four")
    @Expose
    private String lastFour;

    @SerializedName("exp_month")
    private int expirationMonth;

    @SerializedName("exp_year")
    private int expirationYear;

    @SerializedName("first_six")
    private String firstSix;

    @SerializedName("brand")
    private CardBrand brand;

    @SerializedName("funding")
    private String funding;

    @SerializedName("name")
    private String name;

    @SerializedName("customer")
    private String customer;

    @SerializedName("fingerprint")
    private String fingerprint;

    @SerializedName("address")
    private Address address;
}
