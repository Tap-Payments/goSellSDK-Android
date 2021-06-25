package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AhlaamK on 6/25/21.
 * <p>
 * Copyright (c) 2021    Tap Payments.
 * All rights reserved.
 **/
public class TopUpApplication {
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @SerializedName("currency")
    @Expose
    private String currency;

    public TopUpApplication(Integer amount , String currency){
        this.amount = amount;
        this.currency = currency;
    }

}