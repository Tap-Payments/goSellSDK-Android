package company.tap.gosellapi.internal.api.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.From;
import company.tap.gosellapi.internal.api.models.Quote;
import company.tap.gosellapi.internal.api.models.Rate;
import company.tap.gosellapi.internal.api.models.To;

/**
 * Created by h.sheshtawy on 8.10.2019.
 * <br>
 * Model for {@link SupportedCurreciesResponse} object
 */

public final class SupportedCurreciesResponse implements BaseResponse {

    @SerializedName("object")
    @Expose
    public String object;

    @SerializedName("live_mode")
    @Expose
    public Boolean liveMode;

    @SerializedName("api_version")
    @Expose
    public String apiVersion;

    @SerializedName("feature_version")
    @Expose
    public String featureVersion;

    @SerializedName("created")
    @Expose
    public Integer created;

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("from")
    @Expose
    public From from;

    @SerializedName("to")
    @Expose
    public ArrayList<To> to = null;

    @SerializedName("by")
    @Expose
    public String by;

    @SerializedName("quote")
    @Expose
    public ArrayList<Quote> quote = null;

    @SerializedName("source")
    @Expose
    public ArrayList<Source> source = null;

    @SerializedName("rates")
    @Expose
    public ArrayList<Rate> rates = null;


///////////////////////////////////////////////////////////////////////////////////////////////

    public String getObject() {
        return object;
    }

    public Boolean getLiveMode() {
        return liveMode;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getFeatureVersion() {
        return featureVersion;
    }

    public Integer getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public From getFrom() {
        return from;
    }

    public ArrayList<To> getTo() {
        return to;
    }

    public String getBy() {
        return by;
    }

    public ArrayList<Quote> getQuote() {
        return quote;
    }

    public ArrayList<Source> getSource() {
        return source;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }
}



class Source {

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

