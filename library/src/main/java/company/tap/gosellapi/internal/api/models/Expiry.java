package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final  class Expiry implements Serializable {

        @SerializedName  ("period")
        @Expose
        private double period;

        @SerializedName("type")
        @Expose
        private String type;

    public double getPeriod() {
        return period;
    }

    public String getType() {
        return type;
    }
}

