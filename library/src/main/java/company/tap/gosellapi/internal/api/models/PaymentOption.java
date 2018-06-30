package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class PaymentOption implements Comparable<PaymentOption>{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("payment_type")
    @Expose
    private PaymentType paymentType;

    @SerializedName("supported_card_brands")
    @Expose
    private ArrayList<CardBrand> supportedCardBrands;

    @SerializedName("extra_fees")
    @Expose
    private ArrayList<ExtraFee> extra_fees;

    @SerializedName("supported_currencies")
    @Expose
    private ArrayList<String> supported_currencies;

    @SerializedName("order_by")
    @Expose
    private int order_by;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public ArrayList<CardBrand> getSupportedCardBrands() {
        return supportedCardBrands;
    }

    public ArrayList<ExtraFee> getExtra_fees() {
        return extra_fees;
    }

    public ArrayList<String> getSupported_currencies() {
        return supported_currencies;
    }

    public int getOrder_by() {
        return order_by;
    }

    @Override
    public int compareTo(@NonNull PaymentOption o) {
        return order_by - o.order_by;
    }
}
