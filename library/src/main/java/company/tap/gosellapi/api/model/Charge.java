package company.tap.gosellapi.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import company.tap.gosellapi.api.responses.BaseResponse;
import company.tap.gosellapi.api.responses.SourceResponse;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Charge response
 */

public final class Charge implements BaseResponse{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("object")
    @Expose
    private String object;

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
    private SourceResponse source;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

    @SerializedName("captured")
    @Expose
    private boolean captured;

    @SerializedName("threeds")
    @Expose
    private boolean threeds;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

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
    public SourceResponse getSource() {
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

    /**
     * @return Whether the charge was captured.
     */
    public boolean isCaptured() {
        return captured;
    }

    /**
     * @return Defining whether 3D secure transactions or not
     */
    public boolean isThreeds() {
        return threeds;
    }

    /**
     * @return Merchant Reference number to track the payment status and payment attempts
     */
    public String getReference() {
        return reference;
    }

    /**
     * @return Customer information
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @return Customer information
     */
    public String getLast_name() {
        return last_name;
    }

    @Override
    public String toString() {
        return "\nCharge {" +
                "\n    id =  '" + id + '\'' +
                "\n    object =  '" + object + '\'' +
                "\n    amount =  " + amount +
                "\n    amount_refunded =  " + amount_refunded +
                "\n    created =  " + created +
                "\n    currency =  '" + currency + '\'' +
                "\n    description =  '" + description + '\'' +
                "\n    failure_code =  '" + failure_code + '\'' +
                "\n    failure_message =  '" + failure_message + '\'' +
                "\n    livemode =  " + livemode +
                "\n    metadata =  " + metadata +
                "\n    paid =  " + paid +
                "\n    receipt_email =  '" + receipt_email + '\'' +
                "\n    receipt_sms =  '" + receipt_sms + '\'' +
                "\n    receipt_number =  '" + receipt_number + '\'' +
                "\n    refunded =  " + refunded +
                "\n    source =  " + source +
                "\n    statement_descriptor =  '" + statement_descriptor + '\'' +
                "\n    status =  '" + status + '\'' +
                "\n    redirect =  " + redirect +
                "\n    captured =  " + captured +
                "\n    threeds =  " + threeds +
                "\n    reference =  " + reference +
                "\n    first_name =  " + first_name +
                "\n    last_name =  " + last_name +
                "\n}";
    }
}
