package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.internal.api.model.Charge;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to update {@link Charge}, with {@link Builder}
 */

public final class UpdateChargeRequest {
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    @SerializedName("receipt_sms")
    @Expose
    private String receipt_sms;

    @SerializedName("receipt_email")
    @Expose
    private String receipt_email;

    private UpdateChargeRequest() {
    }

    /**
     * Builder to create {@link UpdateChargeRequest} instance
     */
    public final static class Builder {
        private UpdateChargeRequest createChargeRequest;

        /**
         * Builder constructor
         */
        public Builder() {
            createChargeRequest = new UpdateChargeRequest();
        }

        public Builder description(String description) {
            createChargeRequest.description = description;
            return this;
        }

        public Builder metadata(HashMap<String, String> metadata) {
            createChargeRequest.metadata = metadata;
            return this;
        }

        public Builder receipt_sms(String receipt_sms) {
            createChargeRequest.receipt_sms = receipt_sms;
            return this;
        }

        public Builder receipt_email(String receipt_email) {
            createChargeRequest.receipt_email = receipt_email;
            return this;
        }

        public UpdateChargeRequest build() {
            return createChargeRequest;
        }
    }
}
