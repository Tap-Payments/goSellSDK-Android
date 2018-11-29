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

    @NonNull public String getId() {
        return id;
    }

    @NonNull public String getOrderID() {
        return orderID;
    }

    @NonNull public String getObject() {
        return object;
    }

    @NonNull public ArrayList<PaymentOption> getPaymentOptions() {
        return paymentOptions;
    }

    @NonNull public String getCurrency() {
        return currency;
    }

    @NonNull public ArrayList<AmountedCurrency> getSupportedCurrencies() { return supportedCurrencies; }

    @NonNull public ArrayList<SavedCard> getCards() {

        if ( cards == null ) {

            cards = new ArrayList<>();
        }

        return cards;
    }
}
