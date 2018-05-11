package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.api.responses.BaseResponse;

/**
 * Created by eugene.goltsev on 11.05.2018.
 * <br>
 * BIN response
 */

public final class BIN implements BaseResponse{
    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("card_brand")
    @Expose
    private String card_brand;

    @SerializedName("card_type")
    @Expose
    private String card_type;

    @SerializedName("card_category")
    @Expose
    private String card_category;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("phone")
    @Expose
    private String phone;

    /**
     * @return Card BIN number.
     */
    public String getBin() {
        return bin;
    }

    /**
     * @return Card issuer bank.
     */
    public String getBank() {
        return bank;
    }

    /**
     * @return Card brand.
     */
    public String getCard_brand() {
        return card_brand;
    }

    /**
     * @return Card type.
     */
    public String getCard_type() {
        return card_type;
    }

    /**
     * @return Card category.
     */
    public String getCard_category() {
        return card_category;
    }

    /**
     * @return Card issuing country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return Country code.
     */
    public String getCountry_code() {
        return country_code;
    }

    /**
     * @return Website.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @return Phone number.
     */
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "\nBIN {" +
                "\n    bin =  '" + bin + '\'' +
                "\n    bank =  '" + bank + '\'' +
                "\n    card_brand =  " + card_brand +
                "\n    card_type =  " + card_type +
                "\n    card_category =  " + card_category +
                "\n    country =  '" + country + '\'' +
                "\n    country_code =  '" + country_code + '\'' +
                "\n    website =  '" + website + '\'' +
                "\n    phone =  '" + phone + '\'' +
                "\n}";
    }
}
