package company.tap.gosellapi.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.model.Charge;
import company.tap.gosellapi.api.model.Redirect;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to create {@link Charge}, with {@link Builder}
 */

public final class CreateChargeRequest {
    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("threeDSecure")
    @Expose
    private boolean threeDSecure;

    @SerializedName("transaction_reference")
    @Expose
    private String transaction_reference;

    @SerializedName("order_reference")
    @Expose
    private String order_reference;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("receipt")
    @Expose
    private Receipt receipt;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    private CreateChargeRequest(double amount, String currency, Customer customer, Redirect redirect) {
        this.amount = amount;
        this.currency = currency;
        this.customer = customer;
        this.redirect = redirect;
    }

    /**
     * Builder to create {@link CreateChargeRequest} instance
     */
    public final static class Builder {
        private CreateChargeRequest createChargeRequest;

        /**
         * Builder constructor with necessary params
         * @param amount A positive decimal amount representing how much to charge the card. The minimum amount is KD 0.100 or equivalent in charge currency.
         * @param currency 3-letter ISO code for currency.
         * @param customer {@link Customer} Charge Request Model.
         * @param redirect Information related to the {@link Redirect}. For KNET and 3DSecure transactions, required return url
         */
        public Builder(double amount, String currency, Customer customer, Redirect redirect) {
            createChargeRequest = new CreateChargeRequest(amount, currency, customer, redirect);
        }

        /**
         * @param threeDSecure Whether 3DSecure applied for this transaction or not (can be dicided by business, however the final decision will be taken by Tap)
         */
        public Builder threeDSecure(boolean threeDSecure) {
            createChargeRequest.threeDSecure = threeDSecure;
            return this;
        }

        /**
         * @param transaction_reference Merchant Transaction Reference Number
         */
        public Builder transaction_reference(String transaction_reference) {
            createChargeRequest.transaction_reference = transaction_reference;
            return this;
        }

        /**
         * @param order_reference Merchant Order Reference Number
         */
        public Builder order_reference(String order_reference) {
            createChargeRequest.order_reference = order_reference;
            return this;
        }

        /**
         *
         * @param statement_descriptor An arbitrary string to be displayed on your customer&#8217;s credit card statement. This may be up to 22 characters. As an example, if your website is RunClub and the item you&#8217;re charging for is a race ticket, you may want to specify a statement_descriptor of RunClub 5K race ticket. The statement description must contain at least one letter, may not include &lt;&gt;&#34;&#8217; characters, and will appear on your customer&#8217;s statement in capital letters. Non-ASCII characters are automatically stripped. While most banks and card issuers display this information consistently, some may display it incorrectly or not at all.
         */
        public Builder statement_descriptor(String statement_descriptor) {
            createChargeRequest.statement_descriptor = statement_descriptor;
            return this;
        }

        /**
         * @param receipt Whether Receipt email and sms need to be sent or not, default will be true (if customer emil and phone info available, then receipt will be sent)
         */
        public Builder receipt(Receipt receipt) {
            createChargeRequest.receipt = receipt;
            return this;
        }

        /**
         * @param source A payment source to be charged, the source you provide must either be a token id, card id or source id. If you do not pass source, Tap check out url will be provided.
        <br>
        If source is null then, default Tap payment page link will be provided.
        <br>
        if source.id = "src_kw.knet" then KNET payment page link will be provided.
        <br>
        if source.id = "src_card" then Credit Card payment page link will be provided.
        <br>
        if source.id = "Card Token ID or Card ID" then Credit Card payment processing page link will be provided.
         */
        public Builder source(Source source) {
            createChargeRequest.source = source;
            return this;
        }

        /**
         * @param description An arbitrary string which you can attach to a Charge object. It is displayed when in the web interface alongside the charge. Note that if you use Tap to send automatic email receipts to your customers, your receipt emails will include the description of the charge(s) that they are describing. optional, default is None
         */
        public Builder description(String description) {
            createChargeRequest.description = description;
            return this;
        }

        /**
         * @param metadata HashMap of key/value pairs that you can attach to an object. It can be useful for storing additional information about the object in a structured format. Individual keys can be unset by posting an empty value to them. All keys can be unset by posting an empty value to metadata.
         */
        public Builder metadata(HashMap<String, String> metadata) {
            createChargeRequest.metadata = metadata;
            return this;
        }

        public CreateChargeRequest build() {
            return createChargeRequest;
        }
    }

    /**
     * Whether Receipt email and sms need to be sent or not, default will be true (if customer emil and phone info available, then receipt will be sent)
     */
    private static final class Receipt {
        @SerializedName("email")
        @Expose
        private boolean email;

        @SerializedName("sms")
        @Expose
        private boolean sms;

        /**
         * Whether Receipt email and sms need to be sent or not, default will be true (if customer emil and phone info available, then receipt will be sent)
         */
        public Receipt(boolean email, boolean sms) {
            this.email = email;
            this.sms = sms;
        }
    }

    /**
     * Customer Charge Request Model. Either Customer ID or Customer Information is required
     */
    private static final class Customer {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("first_name")
        @Expose
        private String first_name;

        @SerializedName("last_name")
        @Expose
        private String last_name;

        @SerializedName("email_address")
        @Expose
        private String email_address;

        @SerializedName("phone_number")
        @Expose
        private String phone_number;

        public Customer(String id) {
            this.id = id;
        }

        public Customer(String email_address, String phone_number, String first_name) {
            this(email_address, phone_number, first_name, null);
        }

        public Customer(String email_address, String phone_number, String first_name, String last_name) {
            this.email_address = email_address;
            this.phone_number = phone_number;
            this.first_name = first_name;
            this.last_name = last_name;
        }
    }

    /**
     * A payment source to be charged, the source you provide must either be a token id, card id or source id. If you do not pass source, Tap check out url will be provided.
     */
    private static final class Source {
        @SerializedName("id")
        @Expose
        private String id;

        public Source(String id) {
            this.id = id;
        }
    }
}
