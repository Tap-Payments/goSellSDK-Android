package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.Source;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Request to create {@link Charge}, with {@link Builder}
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateChargeRequest {
    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("customer")
    @Expose
    private CustomerInfo customer;

    @SerializedName("fee")
    @Expose
    private double fee;

    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    @SerializedName("reference")
    @Expose
    private Reference reference;

    @SerializedName("save_card")
    @Expose
    private boolean saveCard;

    @SerializedName("statement_descriptor")
    @Expose
    private String statementDescriptor;

    @SerializedName("threeDSecure")
    @Expose
    private boolean threeDSecure = true;

    @SerializedName("receipt")
    @Expose
    private Receipt receipt;

    private CreateChargeRequest(double amount, String currency, double fee, boolean saveCard) {
        this.amount = amount;
        this.currency = currency;
        this.fee = fee;
        this.saveCard = saveCard;
    }

    /**
     * Builder to create {@link CreateChargeRequest} instance
     */
    public final static class Builder {
        private CreateChargeRequest createChargeRequest;

        /**
         * Builder constructor with necessary params
         * @param amount A positive double, representing how much to charge. The minimum amount is $0.50 US or equivalent in charge currency.
         * @param currency Three-letter ISO currency code, in lowercase. Must be a supported currency.
         * @param fee Extra fee amount (if any). Is available only in SDK
         * @param saveCard Parameter which defines whether to save the card. Is available only in SDK
         */
        public Builder(double amount, String currency, double fee, boolean saveCard) {
            createChargeRequest = new CreateChargeRequest(amount, currency, fee, saveCard);
        }

        /**
         * @param source The source of every charge is a credit or debit card. This hash is then the card object describing that card.
        <br>
        If source is null then, default Tap payment page link will be provided.
        <br>
        if source.id = "src_kw.knet" then KNET payment page link will be provided.
        <br>
        if source.id = "src_visamastercard" then Credit Card payment page link will be provided.
         */
        public Builder source(Source source) {
            createChargeRequest.source = source;
            return this;
        }

        /**
         * @param description An arbitrary string which you can attach to a Charge object. It is displayed when in the web interface alongside the charge.
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

        /**
         * @param customer The customer info to send this charge&#8217;s receipt to.
         */
        public Builder customer(CustomerInfo customer) {
            createChargeRequest.customer = customer;
            return this;
        }

        /**
         * @param redirect Information related to the {@link Redirect}.
         */
        public Builder redirect(Redirect redirect) {
            createChargeRequest.redirect = redirect;
            return this;
        }

        /**
         * @param order Order containing identifier (from payment options response)
         */
        public Builder order(Order order) {
            createChargeRequest.order = order;
            return this;
        }

        /**
         * @param reference Merchant reference object, more information in {@link Reference}
         */
        public Builder reference(Reference reference) {
            createChargeRequest.reference = reference;
            return this;
        }

        /**
         * @param statement_descriptor An arbitrary string to be displayed on your customer&#8217;s credit card statement. This may be up to 22 characters. As an example, if your website is RunClub and the item you&#8217;re charging for is a race ticket, you may want to specify a statement_descriptor of RunClub 5K race ticket. The statement description must contain at least one letter, may not include &lt;&gt;&#34;&#8217; characters, and will appear on your customer&#8217;s statement in capital letters. Non-ASCII characters are automatically stripped. While most banks and card issuers display this information consistently, some may display it incorrectly or not at all.
         */
        public Builder statementDescriptor(String statement_descriptor) {
            createChargeRequest.statementDescriptor = statement_descriptor;
            return this;
        }

        /**
         * @param threeDSecure Defines if 3D secure is required. Default is true
         */
        public Builder threeDSecure(boolean threeDSecure) {
            createChargeRequest.threeDSecure = threeDSecure;
            return this;
        }

        /**
         * @param receipt Receipt settings
         */
        public Builder receipt(Receipt receipt) {
            createChargeRequest.receipt = receipt;
            return this;
        }

        public CreateChargeRequest build() {
            return createChargeRequest;
        }
    }
}
