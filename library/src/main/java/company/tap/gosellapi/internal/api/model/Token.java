package company.tap.gosellapi.internal.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.responses.BaseResponse;
import company.tap.gosellapi.internal.api.responses.CardResponse;

/**
 Created by eugene.goltsev on 12.02.2018.
 <br>
 Model for Token object
 */

public final class Token implements BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("card")
    @Expose
    private CardResponse card;

    @SerializedName("client_ip")
    @Expose
    private String client_ip;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("livemode")
    @Expose
    private boolean livemode;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("used")
    @Expose
    private boolean used;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return String representing the object&#8217;s type. Objects of the same type share the same value.
     */
    public String getObject() {
        return object;
    }

    /**
     * @return {@link CardResponse} object used to make the charge
     */
    public CardResponse getCard() {
        return card;
    }

    /**
     * @return IP address of the client that generated the token.
     */
    public String getClient_ip() {
        return client_ip;
    }

    /**
     * @return Time at which the object was created. Measured in seconds since the Unix epoch.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Flag indicating whether the object exists in live mode or test mode.
     */
    public boolean isLivemode() {
        return livemode;
    }

    /**
     * @return Type of the token: card
     */
    public String getType() {
        return type;
    }

    /**
     * @return Whether this token has already been used (tokens can be used only once).
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * @return Three-letter ISO currency code, in lowercase. Must be a supported currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return Cardholder name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nToken {" +
                "\n    id =  '" + id + '\'' +
                "\n    object =  '" + object + '\'' +
                "\n    card =  " + card +
                "\n    client_ip =  '" + client_ip + '\'' +
                "\n    created =  " + created +
                "\n    livemode =  " + livemode +
                "\n    type =  '" + type + '\'' +
                "\n    used =  " + used +
                "\n    currency =  '" + currency + '\'' +
                "\n    name =  '" + name + '\'' +
                "\n}";
    }
}
