package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.api.interfaces.CurrenciesSupport;
import company.tap.tapcardvalidator_android.CardBrand;

public class SavedCard implements Comparable<SavedCard>, CurrenciesSupport {

    @SerializedName("id")
    @Expose
    @Nullable private String id;

    @SerializedName("object")
    @Expose
    @NonNull private String object;

    @SerializedName("first_six")
    @Expose
    @NonNull private String firstSix;

    @SerializedName("last_four")
    @Expose
    @NonNull private String lastFour;

    @SerializedName("brand")
    @Expose
    @NonNull private CardBrand brand;

    @SerializedName("payment_method_id")
    @Expose
    @Nullable private String paymentOptionIdentifier;

    @SerializedName("expiry")
    @Expose
    @Nullable private ExpirationDate expiry;

    @SerializedName("name")
    @Expose
    @Nullable private String cardholderName;

    @SerializedName("currency")
    @Expose
    @Nullable private String currency;

    @SerializedName("scheme")
    @Expose
    @Nullable private CardScheme scheme;

    @SerializedName("supported_currencies")
    @Expose
    @Nullable private ArrayList<String> supportedCurrencies;

    @SerializedName("order_by")
    @Expose
    @NonNull private int orderBy;

    @SerializedName("image")
    @Nullable private String image;
    /**
     * @return Card identifier.
     */
    @Nullable public String getId() {
        return id;
    }

    /**
     * @return Object type.
     */
    @NonNull public String getObject() {
        return object;
    }

    /**
     * @return Last 4 digits of the card.
     */
    @NonNull public String getLastFour() {
        return lastFour;
    }

    /**
     * @return Expiration date.
     */
    @Nullable public ExpirationDate getExpiry() {
        return expiry;
    }

    /**
     * @return Card brand.
     */
    @NonNull public CardBrand getBrand() {
        return brand;
    }

    /**
     * @return Cardholder name.
     */
    @Nullable public String getCardholderName() {
        return cardholderName;
    }

    /**
     * @return Card BIN number.
     */

    @NonNull public String getFirstSix() {
        return firstSix;
    }

    /**
     * @return Card currency.
     */
    @Nullable public String getCurrency() {
        return currency;
    }

    @Nullable public String getPaymentOptionIdentifier() { return paymentOptionIdentifier; }

    @Nullable public CardScheme getScheme() { return scheme; }

    @Nullable public ArrayList<String> getSupportedCurrencies() { return supportedCurrencies; }

    @Nullable public String getImage(){
        return image;
    }
    /**
     * @return Ordering field.
     */

    @NonNull public int getOrderBy() {
        return orderBy;
    }

    @Override
    public int compareTo(@NonNull SavedCard o) {
        return getOrderBy() - o.getOrderBy();
    }
}
