package company.tap.gosellapi.internal.api.requests;

import android.os.Build;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Redirect;
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

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

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

    @SerializedName("customer")
    @Expose
    private CustomerInfo customer;

    private CreateChargeRequest(double amount, String currency, Redirect redirect) {
        this.amount = amount;
        this.currency = currency;
        this.redirect = redirect;
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
         * @param redirect Information related to the {@link Redirect}.
         */
        public Builder(double amount, String currency, Redirect redirect) {
            createChargeRequest = new CreateChargeRequest(amount, currency, redirect);
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
         *
         * @param statement_descriptor An arbitrary string to be displayed on your customer&#8217;s credit card statement. This may be up to 22 characters. As an example, if your website is RunClub and the item you&#8217;re charging for is a race ticket, you may want to specify a statement_descriptor of RunClub 5K race ticket. The statement description must contain at least one letter, may not include &lt;&gt;&#34;&#8217; characters, and will appear on your customer&#8217;s statement in capital letters. Non-ASCII characters are automatically stripped. While most banks and card issuers display this information consistently, some may display it incorrectly or not at all.
         */
        public Builder statement_descriptor(String statement_descriptor) {
            createChargeRequest.statement_descriptor = statement_descriptor;
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
         * @param receipt_sms The mobile number to send this charge&#8217;s receipt to. The receipt will not be sent until the charge is paid. If this charge is for a customer, the mobile number specified here will override the customer&#8217;s mobile number. Receipts will not be sent for test mode charges. If receipt_sms is specified for a charge in live mode, a receipt will be sent regardless of your sms settings. (optional, either receipt_sms or receipt_email is required if customer is not available)
         */
        public Builder receipt_sms(String receipt_sms) {
            createChargeRequest.receipt_sms = receipt_sms;
            return this;
        }

        /**
         * @param receipt_email The email address to send this charge&#8217;s receipt to. The receipt will not be sent until the charge is paid. If this charge is for a customer, the email address specified here will override the customer&#8217;s email address. Receipts will not be sent for test mode charges. If receipt_email is specified for a charge in live mode, a receipt will be sent regardless of your email settings. (optional, either receipt_sms or receipt_email is required if customer is not available)
         */
        public Builder receipt_email(String receipt_email) {
            createChargeRequest.receipt_email = receipt_email;
            return this;
        }

        /**
         * @param customer The customer info to send this charge&#8217;s receipt to.
         */
        public Builder customer(CustomerInfo customer) {
            createChargeRequest.customer = customer;
            return this;
        }

        public CreateChargeRequest build() {
            return createChargeRequest;
        }
    }
}
