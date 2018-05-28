package company.tap.gosellapi.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.model.Customer;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to create {@link Customer}, with {@link Builder}
 */

public final class CustomerRequest {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    private CustomerRequest(String name, String phone, String email) {
        this.phone = phone;
        this.name = name;
        this.email = email;
    }

    /**
     * Created by eugene.goltsev on 14.02.2018.
     * <br>
     * Builder for customer fields inside {@link CustomerRequest}
     */
    public final static class Builder {
        private CustomerRequest customerRequest;

        /**
         * Builder constructor with necessary params
         * @param name Customer name
         * @param phone Customer phone (including extension).
         * @param email The customer&#8217;s email address.
         */
        public Builder(String name, String phone, String email) {
            customerRequest = new CustomerRequest(name, phone, email);
        }

        public Builder description(String description) {
            customerRequest.description = description;
            return this;
        }

        public Builder currency(String currency) {
            customerRequest.currency = currency;
            return this;
        }

        public Builder metadata(HashMap<String, String> metadata) {
            customerRequest.metadata = metadata;
            return this;
        }

        public CustomerRequest build() {
            return customerRequest;
        }
    }
}
