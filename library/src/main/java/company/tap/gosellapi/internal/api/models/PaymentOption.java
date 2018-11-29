package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.interfaces.CurrenciesSupport;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class PaymentOption implements Comparable<PaymentOption>, CurrenciesSupport {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private CardBrand brand;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("payment_type")
    @Expose
    private PaymentType paymentType;

    @SerializedName("source_id")
    @Expose
    private String sourceId;

    @SerializedName("supported_card_brands")
    @Expose
    private ArrayList<CardBrand> supportedCardBrands;

    @SerializedName("extra_fees")
    @Expose
    private ArrayList<ExtraFee> extraFees;

    @SerializedName("supported_currencies")
    @Expose
    private ArrayList<String> supportedCurrencies;

    @SerializedName("order_by")
    @Expose
    private int orderBy;

    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getName() { return getBrand().getRawValue(); }

    public CardBrand getBrand() {
        return brand;
    }

    public String getImage() { return image; }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public ArrayList<CardBrand> getSupportedCardBrands() {
        return supportedCardBrands;
    }

    public ArrayList<ExtraFee> getExtraFees() {
        return extraFees;
    }

    public ArrayList<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public int getOrderBy() {
        return orderBy;
    }

    @Override
    public int compareTo(@NonNull PaymentOption o) {
        return orderBy - o.orderBy;
    }
}
