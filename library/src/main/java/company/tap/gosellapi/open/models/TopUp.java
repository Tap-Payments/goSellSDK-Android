package company.tap.gosellapi.open.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by AhlaamK on 6/25/21.
 * <p>
 * Copyright (c) 2021    Tap Payments.
 * All rights reserved.
 **/
public final class TopUp implements Serializable {
    @SerializedName("id")
    @Expose
    String Id;
    @SerializedName("wallet_id")
    @Expose
    String walletId;
    @SerializedName("created")
    @Expose
    @Nullable  Long created;
    @SerializedName("amount")
    @Expose
    @Nullable  BigDecimal amount;
    @SerializedName("currency")
    @Expose
    @Nullable  String currency;
    @SerializedName("charge")
    @Expose
    @Nullable  TopchargeModel charge;
    @SerializedName("customer")
    @Expose
    @Nullable  TopCustomerModel customer;
    @SerializedName("reference")
    @Expose
    TopUpReference topUpReference;
    @SerializedName("application")
    @Expose
    TopUpApplication application;

    public String getId() {
        return Id;
    }

    public String getWalletId() {
        return walletId;
    }

    @Nullable
    public Long getCreated() {
        return created;
    }

    @Nullable
    public BigDecimal getAmount() {
        return amount;
    }

    @Nullable
    public String getCurrency() {
        return currency;
    }

    @Nullable
    public TopchargeModel getCharge() {
        return charge;
    }

    @Nullable
    public TopCustomerModel getCustomer() {
        return customer;
    }

    public TopUpReference getTopUpReference() {
        return topUpReference;
    }

    public TopUpApplication getApplication() {
        return application;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    @SerializedName("metadata")
    @Expose
    MetaData metadata;


    //  Constructor is private to prevent access from client app, it must be through inner Builder class only
    public TopUp(@Nullable String Id , String walletId, @Nullable Long created, BigDecimal amount , String currency, @Nullable TopchargeModel charge , @Nullable TopCustomerModel customer, @Nullable TopUpReference topUpReference, TopUpApplication topUpApplication, @Nullable MetaData metaData
    ) {

        this.Id = Id;
        this.walletId = walletId;
        this.created = created;
        this.amount = amount;
        this.currency = currency;
        this.charge = charge;
        this.customer = customer;
        this.topUpReference = topUpReference;
        this.application = topUpApplication;
        this.metadata = metaData;
    }
}
