package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.SourceChannel;
import company.tap.gosellapi.internal.api.enums.SourceObject;
import company.tap.gosellapi.internal.api.enums.SourcePaymentType;
import company.tap.gosellapi.internal.api.enums.SourceType;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Source object
 */
public final class Source {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    @Nullable private SourceObject object;

    @SerializedName("type")
    @Expose
    @Nullable private SourceType sourceType;

    @SerializedName("payment_type")
    @Expose
    @Nullable private SourcePaymentType paymentType;

    @SerializedName("payment_method")
    @Expose
    @Nullable private CardBrand paymentMethod;

    @SerializedName("channel")
    @Expose
    @Nullable private SourceChannel channel;

    /**
     * Constructor with id field only (token id, card id etc.)
     *
     * @param id the id
     */
    public Source(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Source {" +
                "\n        id =  '" + id + '\'' +
                "\n    }";
    }
}
