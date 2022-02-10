package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.SavedCard;

/**
 * Created by eugene.goltsev on 17.04.2018.
 * <br>
 * Model for {@link PaymentOptionsResponse} object
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PaymentOptionsResponse implements BaseResponse {

    @SerializedName("id")
    @Expose
    @NonNull private String id;

    @SerializedName("order_id")
    @Expose
    @NonNull private String orderID;

    @SerializedName("object")
    @Expose
    @NonNull private String object;

    @SerializedName("payment_methods")
    @Expose
    @NonNull private ArrayList<PaymentOption> paymentOptions;

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("supported_currencies")
    @Expose
    @NonNull private ArrayList<AmountedCurrency> supportedCurrencies;

    @SerializedName("cards")
    @Expose
    @Nullable private ArrayList<SavedCard> cards;

    @SerializedName("settlement_currency")
    @Expose
    @Nullable private String settlement_currency;

    @SerializedName("api_version")
    @Expose
    @Nullable private String api_version;

    @SerializedName("merchant")
    @Expose
    @Nullable private Merchant merchant;
    /**
     * Gets id.
     *
     * @return the id
     */
///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull public String getId() {
        return id;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    @NonNull public String getOrderID() {
        return orderID;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    @NonNull public String getObject() {
        return object;
    }

    /**
     * Gets payment options.
     *
     * @return the payment options
     */
    @NonNull public ArrayList<PaymentOption> getPaymentOptions() {
        return paymentOptions;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    @NonNull public String getCurrency() {
        return currency;
    }

    /**
     * Gets supported currencies.
     *
     * @return the supported currencies
     */
    @NonNull public ArrayList<AmountedCurrency> getSupportedCurrencies() { return supportedCurrencies; }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    @NonNull public ArrayList<SavedCard> getCards() {

        if ( cards == null ) {

            cards = new ArrayList<>();
        }

        return cards;
    }

    /**
     * Gets settlement_currency.
     *
     * @return the settlement_currency
     */
    @Nullable
    public String getSettlement_currency() {
        return settlement_currency;
    }

    /**
     * Gets api_version.
     *
     * @return the api_version
     */
    @Nullable
    public String getApi_version() {
        return api_version;
    }
    /**
     * Gets merchant.
     *
     * @return the merchant
     */
    @Nullable
    public Merchant getMerchant() {
        return merchant;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * The type Merchant.
     */
    public static final class Merchant {

        @SerializedName("name")
        @Expose
        private String name;


        @SerializedName("live_mode")
        @Expose
        private Boolean live_mode;

        @SerializedName("logo")
        @Expose
        private String logo;

        @SerializedName("object")
        @Expose
        private String object;

        @SerializedName("country_code")
        @Expose
        private String country_code;

        @SerializedName("contact")
        @Expose
        private Object contact;

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets logo.
         *
         * @return the logo
         */
        public String getLogo() {
            return logo;
        }

        /**
         * Gets live_mode.
         *
         * @return the live_mode
         */
        public Boolean getLive_mode() {
            return live_mode;
        }

        /**
         * Gets object.
         *
         * @return the object
         */
        public String getObject() {
            return object;
        }

        /**
         * Gets country_code.
         *
         * @return the country_code
         */
        public String getCountry_code() {
            return country_code;
        }

        /**
         * Gets contact.
         *
         * @return the contact
         */
        public Object getContact() {
            return contact;
        }
    }
}
