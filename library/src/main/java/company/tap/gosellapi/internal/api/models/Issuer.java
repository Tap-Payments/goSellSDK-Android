package company.tap.gosellapi.internal.api.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class Issuer implements Serializable {

    @SerializedName("id")
    @Nullable
    @Expose
    private String id;


    @SerializedName("bank")
    @Nullable
    @Expose
    private String bank;
    @SerializedName("country")
    @Nullable
    @Expose
    private String country;

    public Issuer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
