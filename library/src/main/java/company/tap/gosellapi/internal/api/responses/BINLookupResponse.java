package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.tapcardvalidator_android.CardBrand;

public final class BINLookupResponse implements BaseResponse {

    @SerializedName("address_required")
    @Expose
    private boolean addressRequired;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("bank_logo")
    @Expose
    @Nullable private String bankLogo;

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("card_brand")
    @Expose
    private CardBrand cardBrand;

    @SerializedName("card_scheme")
    @Expose
    @Nullable private CardScheme scheme;

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
    public String getBankLogo() {
        return bankLogo;
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

    @Nullable public CardScheme getScheme() { return scheme; }

    /**
     * @return Card issuing country.
     */
    public String getCountry() {
        return country;
    }
}
