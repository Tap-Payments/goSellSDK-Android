package company.tap.gosellapi.internal.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.tapcardvalidator_android.CardBrand;

public class BINLookupResponse implements BaseResponse {
    @SerializedName("address_required")
    @Expose
    private boolean addressRequired;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("bank_logo")
    @Expose
    private String bank_logo;

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("card_brand")
    @Expose
    private CardBrand cardBrand;

    @SerializedName("country")
    @Expose
    private String country;

    /**
     * Defines if a card with the given bin_number requires address fields to be passed to
     * Charge API.
     * @return flag is address required.
     */
    public boolean isAddressRequired() {
        return addressRequired;
    }

    /**
     * @return Bank name.
     */
    public String getBank() {
        return bank;
    }

    /**
     * @return Bank logo URL.
     */
    public String getBank_logo() {
        return bank_logo;
    }

    /**
     * @return Bin number.
     */
    public String getBin() {
        return bin;
    }

    /**
     * @return Card brand.
     */
    public CardBrand getCardBrand() {
        return cardBrand;
    }

    /**
     * @return Card issuing country.
     */
    public String getCountry() {
        return country;
    }
}
