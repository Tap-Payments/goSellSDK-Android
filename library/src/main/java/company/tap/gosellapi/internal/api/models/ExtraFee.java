package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class ExtraFee {
    @SerializedName("merchant_id")
    @Expose
    private String merchant_id;

    @SerializedName("fee")
    @Expose
    private double fee;

    @SerializedName("fee_type")
    @Expose
    private String fee_type;

    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    public String getMerchant_id() {
        return merchant_id;
    }

    public double getFee() {
        return fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public String getCurrency_code() {
        return currency_code;
    }
}
