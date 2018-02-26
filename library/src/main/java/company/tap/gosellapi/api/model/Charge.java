package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.responses.BaseResponse;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Charge response
 */

public final class Charge implements BaseResponse{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object_type")
    @Expose
    private String object_type;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("amount_refunded")
    @Expose
    private double amount_refunded;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("failure_code")
    @Expose
    private String failure_code;

    @SerializedName("failure_message")
    @Expose
    private String failure_message;

    @SerializedName("livemode")
    @Expose
    private boolean livemode;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    @SerializedName("paid")
    @Expose
    private boolean paid;

    @SerializedName("receipt_email")
    @Expose
    private String receipt_email;

    @SerializedName("receipt_sms")
    @Expose
    private String receipt_sms;

    @SerializedName("receipt_number")
    @Expose
    private String receipt_number;

    @SerializedName("refunded")
    @Expose
    private boolean refunded;

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return String representing the object&#8217;s type. Objects of the same type share the same value.
     */
    public String getObject_type() {
        return object_type;
    }

    /**
     * @return A positive double, representing how much to charge. The minimum amount is $0.50 US or equivalent in charge currency.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return Amount (can be less than the amount attribute on the charge if a partial refund was issued). Minimum: 0
     */
    public double getAmount_refunded() {
        return amount_refunded;
    }

    /**
     * @return Time at which the object was created. Measured in seconds since the Unix epoch.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Three-letter ISO currency code, in lowercase. Must be a supported currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return An arbitrary string attached to the object. Often useful for displaying to users.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Error code explaining reason for charge failure if available (see the errors section for a list of codes).
     */
    public String getFailure_code() {
        return failure_code;
    }

    /**
     * @return Message to user further explaining reason for charge failure if available.
     */
    public String getFailure_message() {
        return failure_message;
    }

    /**
     * @return Flag indicating whether the object exists in live mode or test mode.
     */
    public boolean isLivemode() {
        return livemode;
    }

    /**
     * @return HashMap of key/value pairs that you can attach to an object. It can be useful for storing additional information about the object in a structured format.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    /**
     * @return True if the charge succeeded, or was successfully authorized for later capture.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * @return This is the email address that the receipt for this charge was sent to.
     */
    public String getReceipt_email() {
        return receipt_email;
    }

    /**
     * @return The mobile number to send this charge&#8217;s receipt to. The receipt will not be sent until the charge is paid. If this charge is for a customer, the mobile number specified here will override the customer&#8217;s mobile number. Receipts will not be sent for test mode charges. If receipt_sms is specified for a charge in live mode, a receipt will be sent regardless of your sms settings. (optional, either receipt_sms or receipt_email is required if customer is not available)
     */
    public String getReceipt_sms() {
        return receipt_sms;
    }

    /**
     * @return This is the transaction number that appears on email receipts sent for this charge. This attribute will be null until a receipt has been sent.
     */
    public String getReceipt_number() {
        return receipt_number;
    }

    /**
     * @return Whether the charge has been fully refunded. If the charge is only partially refunded, this attribute will still be false.
     */
    public boolean isRefunded() {
        return refunded;
    }

    /**
     * @return The source of every charge is a credit or debit card. This hash is then the card object describing that card.
    <br>
    If source is null then, default Tap payment page link will be provided.
    <br>
    if source.id = "src_kw.knet" then KNET payment page link will be provided.
    <br>
    if source.id = "src_visamastercard" then Credit Card payment page link will be provided.
     */
    public Source getSource() {
        return source;
    }

    /**
     * @return Extra information about a charge. This will appear on your customer&#8217;s credit card statement. It must contain at least one letter.
     */
    public String getStatement_descriptor() {
        return statement_descriptor;
    }

    /**
     * @return The status of the payment is either succeeded, pending, or failed
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Information related to the payment page redirect.
     */
    public Redirect getRedirect() {
        return redirect;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "id='" + id + '\'' +
                ", object_type='" + object_type + '\'' +
                ", amount=" + amount +
                ", amount_refunded=" + amount_refunded +
                ", created=" + created +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", failure_code='" + failure_code + '\'' +
                ", failure_message='" + failure_message + '\'' +
                ", livemode=" + livemode +
                ", metadata=" + metadata +
                ", paid=" + paid +
                ", receipt_email='" + receipt_email + '\'' +
                ", receipt_sms='" + receipt_sms + '\'' +
                ", receipt_number='" + receipt_number + '\'' +
                ", refunded=" + refunded +
                ", source=" + source +
                ", statement_descriptor='" + statement_descriptor + '\'' +
                ", status='" + status + '\'' +
                ", redirect=" + redirect +
                '}';
    }
}
