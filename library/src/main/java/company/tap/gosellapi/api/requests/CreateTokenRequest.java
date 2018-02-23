package company.tap.gosellapi.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.api.model.Token;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Request to create {@link Token} from the card, with own {@link CardRequest} model and {@link Builder}
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateTokenRequest {
    @SerializedName("card")
    @Expose
    private CardRequest card;

    @SerializedName("muid")
    @Expose
    private String muid;

    @SerializedName("sid")
    @Expose
    private String sid;

    @SerializedName("client_ip")
    @Expose
    private String client_ip;

    @SerializedName("referrer")
    @Expose
    private String referrer;

    @SerializedName("payment_user_agent")
    @Expose
    private String payment_user_agent;

    private CreateTokenRequest(CardRequest card) {
        this.card = card;
    }

    /**
     * Builder to create {@link CreateTokenRequest} instance
     */
    public final static class Builder {
        private CreateTokenRequest createTokenRequest;

        /**
         * Builder constructor with necessary params
         * @param card Json card string with (optionally) encrypted sensitive data
         */
        public Builder(CardRequest card) {
            createTokenRequest = new CreateTokenRequest(card);
        }

        public Builder muid(String muid) {
            createTokenRequest.muid = muid;
            return this;
        }

        public Builder sid(String sid) {
            createTokenRequest.sid = sid;
            return this;
        }

        public Builder client_ip(String client_ip) {
            createTokenRequest.client_ip = client_ip;
            return this;
        }

        public Builder referrer(String referrer) {
            createTokenRequest.referrer = referrer;
            return this;
        }

        public Builder payment_user_agent(String payment_user_agent) {
            createTokenRequest.payment_user_agent = payment_user_agent;
            return this;
        }

        public CreateTokenRequest build() {
            return createTokenRequest;
        }
    }
}
