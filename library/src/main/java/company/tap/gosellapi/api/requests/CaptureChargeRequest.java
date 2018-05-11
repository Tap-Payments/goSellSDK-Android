package company.tap.gosellapi.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 11.05.2018.
 * <br>
 * Request to create {@link CaptureChargeRequest}, with {@link Builder}
 */

public final class CaptureChargeRequest {
    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("receipt_sms")
    @Expose
    private String receipt_sms;

    @SerializedName("receipt_email")
    @Expose
    private String receipt_email;

    private CaptureChargeRequest() {
    }

    /**
     * Builder to create {@link CaptureChargeRequest} instance
     */
    public final static class Builder {
        private CaptureChargeRequest captureChargeRequest;

        /**
         * Builder constructor, no obligatory params
         */
        public Builder() {
            captureChargeRequest = new CaptureChargeRequest();
        }

        /**
         * @param amount A positive double, representing how much to charge. The minimum amount is $0.50 US or equivalent in charge currency.
         */
        public Builder amount(double amount) {
            captureChargeRequest.amount = amount;
            return this;
        }

        /**
         *
         * @param statement_descriptor An arbitrary string to be displayed on your customer&#8217;s credit card statement. This may be up to 22 characters. As an example, if your website is RunClub and the item you&#8217;re charging for is a race ticket, you may want to specify a statement_descriptor of RunClub 5K race ticket. The statement description must contain at least one letter, may not include &lt;&gt;&#34;&#8217; characters, and will appear on your customer&#8217;s statement in capital letters. Non-ASCII characters are automatically stripped. While most banks and card issuers display this information consistently, some may display it incorrectly or not at all.
         */
        public Builder statement_descriptor(String statement_descriptor) {
            captureChargeRequest.statement_descriptor = statement_descriptor;
            return this;
        }

        /**
         * @param receipt_sms The mobile number to send this charge&#8217;s receipt to. The receipt will not be sent until the charge is paid. If this charge is for a customer, the mobile number specified here will override the customer&#8217;s mobile number. Receipts will not be sent for test mode charges. If receipt_sms is specified for a charge in live mode, a receipt will be sent regardless of your sms settings. (optional, either receipt_sms or receipt_email is required if customer is not available)
         */
        public Builder receipt_sms(String receipt_sms) {
            captureChargeRequest.receipt_sms = receipt_sms;
            return this;
        }

        /**
         * @param receipt_email The email address to send this charge&#8217;s receipt to. The receipt will not be sent until the charge is paid. If this charge is for a customer, the email address specified here will override the customer&#8217;s email address. Receipts will not be sent for test mode charges. If receipt_email is specified for a charge in live mode, a receipt will be sent regardless of your email settings. (optional, either receipt_sms or receipt_email is required if customer is not available)
         */
        public Builder receipt_email(String receipt_email) {
            captureChargeRequest.receipt_email = receipt_email;
            return this;
        }

        public CaptureChargeRequest build() {
            return captureChargeRequest;
        }
    }
}
