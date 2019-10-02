package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
public final class Source implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

//    @SerializedName("object")
//    @Expose
//    @Nullable private SourceObject object;

    @SerializedName("object")
    @Expose
    @Nullable private String object;

    @SerializedName("type")
    @Expose
    @Nullable private SourceType type;

//    @SerializedName("payment_type")
//    @Expose
//    @Nullable private SourcePaymentType paymentType;

    @SerializedName("payment_type")
    @Expose
    @Nullable private String paymentType;


    @SerializedName("payment_method")
    @Expose
    @Nullable private CardBrand paymentMethod;

    @SerializedName("channel")
    @Expose
    @Nullable private SourceChannel channel;

//    @Nullable
//    public SourceObject getObject() {
//        return object;
//    }


    @Nullable
    public String getObject() {
        return object;
    }

    @Nullable
    public SourceType getType() {
        return type;
    }

//    @Nullable
//    public SourcePaymentType getPaymentType() {
//        return paymentType;
//    }

    @Nullable
    public String getPaymentType() {
        return paymentType;
    }


    @Nullable
    public CardBrand getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodStringValue(){
        if(paymentMethod ==null) return "";
        return paymentMethod.getRawValue();
    }

    @Nullable
    public SourceChannel getChannel() {
        return channel;
    }

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
