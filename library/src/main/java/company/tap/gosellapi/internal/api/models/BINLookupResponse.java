package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.tapcardvalidator_android.CardBrand;

public class BINLookupResponse {

    @SerializedName("address_required")
    @Expose
    @NonNull private boolean addressRequired;

    @SerializedName("bank")
    @Expose
    @Nullable private String bank;

    @SerializedName("bank_logo")
    @Expose
    @Nullable private String bank_logo;

    @SerializedName("bin")
    @Expose
    @NonNull private String bin;

    @SerializedName("card_brand")
    @Expose
    @NonNull private CardBrand cardBrand;

    @SerializedName("card_scheme")
    @Expose
    @Nullable private CardScheme scheme;

    @SerializedName("country")
    @Expose
    @Nullable private String country;

    /**
     * Defines if a card with the given bin_number requires address fields to be passed to
     * Charge API.
     * @return flag is address required.
     */
    @NonNull public boolean isAddressRequired() {
        return addressRequired;
    }

    /**
     * @return Bank name.
     */
    @Nullable public String getBank() {
        return bank;
    }

    /**
     * @return Bank logo URL.
     */
    @Nullable public String getBank_logo() {
        return bank_logo;
    }

    /**
     * @return Bin number.
     */
    @NonNull public String getBin() {
        return bin;
    }

    /**
     * @return Card brand.
     */
    @NonNull public CardBrand getCardBrand() {
        return cardBrand;
    }

    @Nullable public CardScheme getScheme() { return scheme; }

    /**
     * @return Card issuing country.
     */
    @Nullable public String getCountry() {
        return country;
    }
}
