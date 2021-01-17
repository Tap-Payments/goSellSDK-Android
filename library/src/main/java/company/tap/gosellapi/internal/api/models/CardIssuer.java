package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AhlaamK on 1/17/21.
 * <p>
 * Copyright (c) 2021    Tap Payments.
 * All rights reserved.
 **/
public final class CardIssuer implements Serializable {

    @SerializedName("id")
    @Expose
    @NonNull
    private String id;

    @SerializedName("name")
    @Expose
    @NonNull private String name;

    @SerializedName("country")
    @Expose
    @NonNull private String country;

    public String getName() {
        return name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getCountry() {
        return country;
    }
}
