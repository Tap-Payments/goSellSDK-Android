package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Markup {

    @SerializedName("value")
    @Expose
    public String value;

    @SerializedName("quote")
    @Expose
    public String quote;

    @SerializedName("rate")
    @Expose
    public String rate;

}
