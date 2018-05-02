package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
    private String payment_type;

    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    @SerializedName("supported_card_brands")
    @Expose
    private ArrayList<String> supported_card_brands;

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

    public String getPayment_type() {
        return payment_type;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public ArrayList<String> getSupported_card_brands() {
        return supported_card_brands;
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
