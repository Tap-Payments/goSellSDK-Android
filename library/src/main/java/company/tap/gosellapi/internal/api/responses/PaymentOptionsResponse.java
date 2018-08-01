package company.tap.gosellapi.internal.api.responses;

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
    private String id;

    @SerializedName("order_id")
    @Expose
    private String orderID;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("payment_methods")
    @Expose
    private ArrayList<PaymentOption> paymentOptions;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("supported_currencies")
    @Expose
    private ArrayList<AmountedCurrency> supportedCurrencies;

    @SerializedName("cards")
    @Expose
    @Nullable private ArrayList<SavedCard> cards;

    public String getId() {
        return id;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getObject() {
        return object;
    }

    public ArrayList<PaymentOption> getPaymentOptions() {
        return paymentOptions;
    }

    public String getCurrency() {
        return currency;
    }

    public ArrayList<AmountedCurrency> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    @Nullable public ArrayList<SavedCard> getCards() {
        return cards;
    }
}
