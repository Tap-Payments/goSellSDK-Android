package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import company.tap.gosellapi.internal.api.enums.TokenType;
import company.tap.gosellapi.internal.api.responses.BaseResponse;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Model for Token object
 */
//@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class Token implements BaseResponse , Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("card")
    @Expose
    private TokenizedCard card;

    @SerializedName("type")
    @Expose
    private TokenType type;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("client_ip")
    @Expose
    @Nullable private String client_ip;

    @SerializedName("livemode")
    @Expose
    private boolean livemode;

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
     * Gets id.
     *
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets object.
     *
     * @return String representing the object&#8217;s type. Objects of the same type share the same value.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets card.
     *
     * @return {@link TokenizedCard} object used to make the charge
     */
    public TokenizedCard getCard() {
        return card;
    }

    /**
     * Gets client ip.
     *
     * @return IP address of the client that generated the token.
     */
    public String getClient_ip() {
        return client_ip;
    }

    /**
     * Gets created.
     *
     * @return Time at which the object was created. Measured in seconds since the Unix epoch.
     */
    public long getCreated() {
        return created;
    }

    /**
     * Is livemode boolean.
     *
     * @return Flag indicating whether the object exists in live mode or test mode.
     */
    public boolean isLivemode() {
        return livemode;
    }

    /**
     * Gets type.
     *
     * @return Type of the token: card
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Is used boolean.
     *
     * @return Whether this token has already been used (tokens can be used only once).
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Gets currency.
     *
     * @return Three -letter ISO currency code, in lowercase. Must be a supported currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets name.
     *
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
