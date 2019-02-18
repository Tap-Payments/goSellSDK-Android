package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.tapcardvalidator_android.CardBrand;

public final class TokenizedCard {

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
    @Nullable private String customer;

    @SerializedName("fingerprint")
    @Expose
    private String fingerprint;

    @SerializedName("address")
    @Nullable private Address address;

    public String getFirstSix() {
        return firstSix;
    }

    @NonNull
    public String getFingerprint() {
        return fingerprint;
    }
}
