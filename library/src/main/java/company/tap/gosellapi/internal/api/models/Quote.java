package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("currency")
    @Expose
    public String currency;

    @SerializedName("preferred_provider")
    @Expose
    public String preferredProvider;

    @SerializedName("average")
    @Expose
    public String average;

    @SerializedName("ceiling")
    @Expose
    public String ceiling;

    @SerializedName("floor")
    @Expose
    public String floor;

}
