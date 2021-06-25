package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AhlaamK on 6/25/21.
 * <p>
 * Copyright (c) 2021    Tap Payments.
 * All rights reserved.
 **/
public class TopUpReference {
    public String getOrder() {
        return order;
    }

    public String getTransaction() {
        return transaction;
    }

    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("transaction")
    @Expose
    private String transaction;

    public TopUpReference(String order ,String transaction){
        this.order = order;
        this.transaction = transaction;
    }

}