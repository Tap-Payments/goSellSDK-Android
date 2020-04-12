package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.interfaces.CurrenciesSupport;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */
public final class PaymentOption implements Comparable<PaymentOption>, CurrenciesSupport, Serializable {
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

    @SerializedName("threeDS")
    @Expose
    private String threeDS;
    @SerializedName("asynchronous")
    @Expose
    private boolean asynchronous;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets source id.
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return (getBrand()!=null)? getBrand().getRawValue():"";
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public CardBrand getBrand() {
        return brand;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() { return image; }

    /**
     * Gets payment type.
     *
     * @return the payment type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Gets supported card brands.
     *
     * @return the supported card brands
     */
    public ArrayList<CardBrand> getSupportedCardBrands() {
        return supportedCardBrands;
    }

    /**
     * Gets extra fees.
     *
     * @return the extra fees
     */
    public ArrayList<ExtraFee> getExtraFees() {
        return extraFees;
    }

    public ArrayList<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    /**
     * Gets order by.
     *
     * @return the order by
     */
    public int getOrderBy() {
        return orderBy;
    }

    /**
     * Gets threeDS.
     *
     * @return the threeDS.
     */
    public String getThreeDS() {
        return threeDS;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    @Override
    public int compareTo(@NonNull PaymentOption o) {
        return orderBy - o.orderBy;
    }
}
