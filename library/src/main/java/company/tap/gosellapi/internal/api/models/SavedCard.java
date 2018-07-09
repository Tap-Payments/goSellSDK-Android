package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.tapcardvalidator_android.CardBrand;

public class SavedCard {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("last_four")
    @Expose
    private String lastFour;

    @SerializedName("expiry")
    @Expose
    private ExpirationDate expiry;

    @SerializedName("brand")
    @Expose
    private CardBrand brand;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("first_six")
    @Expose
    private String firstSix;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("order_by")
    @Expose
    private int orderBy;

    /**
     * @return Card identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Object type.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return Last 4 digits of the card.
     */
    public String getLastFour() {
        return lastFour;
    }

    /**
     * @return Expiration date.
     */
    public ExpirationDate getExpiry() {
        return expiry;
    }

    /**
     * @return Card brand.
     */
    public CardBrand getBrand() {
        return brand;
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
    public String getFirstSix() {
        return firstSix;
    }

    /**
     * @return Card currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return Ordering field.
     */
    public int getOrderBy() {
        return orderBy;
    }
}
