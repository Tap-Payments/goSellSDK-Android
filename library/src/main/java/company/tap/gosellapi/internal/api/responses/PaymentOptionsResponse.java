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
    ////////////////////////////////////////////////////////////////////////////////////////////


    public void setOrderID(@NonNull String orderID) {
        this.orderID = orderID;
    }

    public void setObject(@NonNull String object) {
        this.object = object;
    }

    public void setPaymentOptions(@NonNull ArrayList<PaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    public void setSupportedCurrencies(@NonNull ArrayList<AmountedCurrency> supportedCurrencies) {
        this.supportedCurrencies = supportedCurrencies;
    }

    public void setCards(@Nullable ArrayList<SavedCard> cards) {
        this.cards = cards;
    }
}
