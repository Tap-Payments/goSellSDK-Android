package company.tap.gosellapi.open.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by AhlaamK on 7/7/21.
 * <p>
 * Copyright (c) 2021    Tap Payments.
 * All rights reserved.
 **/
public class TopupPost {
    @SerializedName("url")
    @Expose
    @Nullable
    private String url;

    public TopupPost(String url ){
        this.url = url;

    }
    public String getUrl() {
        return url;
    }
}
