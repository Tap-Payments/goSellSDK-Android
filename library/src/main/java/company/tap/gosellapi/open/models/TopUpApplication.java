package company.tap.gosellapi.open.models;

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
public class TopUpApplication implements Serializable {
    @SerializedName("amount")
    @Expose
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @SerializedName("currency")
    @Expose
    private String currency;

    public TopUpApplication(BigDecimal amount , String currency){
        this.amount = amount;
        this.currency = currency;
    }

}