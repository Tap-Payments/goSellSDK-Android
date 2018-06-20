package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.PaymentOption;

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

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("payment_options")
    @Expose
    private ArrayList<PaymentOption> payment_options;

    @SerializedName("currency")
    @Expose
    private String iso_code;

    @SerializedName("supported_currencies")
    @Expose
    private ArrayList<AmountedCurrency> supported_currencies;

    @SerializedName("cards")
    @Expose
    private ArrayList<Card> cards;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public ArrayList<PaymentOption> getPayment_options() {
        return payment_options;
    }

    public String getCurrencyIsoCode() {
        return iso_code;
    }

    public ArrayList<AmountedCurrency> getSupported_currencies() {
        return supported_currencies;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
