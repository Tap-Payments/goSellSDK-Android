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

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("live_mode")
    @Expose
    private boolean live_mode;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("terminal_id")
    @Expose
    private String terminal_id;

    @SerializedName("auth_id")
    @Expose
    private String auth_id;

    @SerializedName("threeDSecure")
    @Expose
    private boolean threeDSecure;

    @SerializedName("transaction_reference")
    @Expose
    private String transaction_reference;

    @SerializedName("order_reference")
    @Expose
    private String order_reference;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("created")
    @Expose
    private long created;

    @SerializedName("post")
    @Expose
    private long post;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("statement_descriptor")
    @Expose
    private String statement_descriptor;

    @SerializedName("chargeback")
    @Expose
    private boolean chargeback;

    @SerializedName("response")
    @Expose
    private Response response;

    @SerializedName("acquirer")
    @Expose
    private Response acquirer;

    @SerializedName("receipt")
    @Expose
    private Receipt receipt;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("source")
    @Expose
    private SourceResponse source;

    @SerializedName("redirect")
    @Expose
    private Redirect redirect;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("metadata")
    @Expose
    private HashMap<String, String> metadata;

    //inner models
    /**
     * Charges Response Code and Messages from Tap or Acquirer
     */
    public static final class Response {
        @SerializedName("code")
        @Expose
        private String code;

        @SerializedName("message")
        @Expose
        private String message;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Response {" +
                    "\n        code =  '" + code + '\'' +
                    "\n        message =  '" + message + '\'' +
                    "\n    }";
        }
    }

    /**
     * Whether Receipt email and sms need to be sent or not, default will be true (if customer emil and phone info available, then receipt will be sent)
     */
    public static final class Receipt {
        @SerializedName("email")
        @Expose
        private boolean email;

        @SerializedName("sms")
        @Expose
        private boolean sms;

        public Receipt(boolean email, boolean sms) {
            this.email = email;
            this.sms = sms;
        }

        public boolean isEmail() {
            return email;
        }

        public boolean isSms() {
            return sms;
        }

        @Override
        public String toString() {
            return "Receipt {" +
                    "\n        email =  '" + email + '\'' +
                    "\n        sms =  '" + sms + '\'' +
                    "\n    }";
        }
    }

    public static final class SourceResponse {
        @SerializedName("object")
        @Expose
        private String object;

        @SerializedName("card_last4")
        @Expose
        private String card_last4;

        @SerializedName("payment_type")
        @Expose
        private String payment_type;

        @SerializedName("id")
        @Expose
        private String id;

        /**
         * @return String representing the object&#8217;s type. Objects of the same type share the same value. (value is "card_id", "token_id", "source_id", or "card")
         */
        public String getObject() {
            return object;
        }

        /**
         * @return Credit Card Number Last 4 Digit
         */
        public String getCard_last4() {
            return card_last4;
        }

        /**
         * @return Card Type (DEBIT_CARD, CREDIT_CARD, PREPAID_CARD, PREPAID_WALLET)
         */
        public String getPayment_type() {
            return payment_type;
        }

        /**
         * @return Source ID or Token ID or Card ID should be provided
         */
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Source {" +
                    "\n        object =  '" + object + '\'' +
                    "\n        card_last4 =  '" + card_last4 + '\'' +
                    "\n        payment_type =  '" + payment_type + '\'' +
                    "\n        id =  '" + id + '\'' +
                    "\n    }";
        }
    }

    /**
     * Customer Charge Response model
     */
    public static final class Customer {
        @SerializedName("object")
        @Expose
        private String object;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("first_name")
        @Expose
        private String first_name;

        @SerializedName("last_name")
        @Expose
        private String last_name;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("phone")
        @Expose
        private String phone;

        /**
         * @return String representing the object&#8217;s type. Objects of the same type share the same value. (value is "customer")
         */
        public String getObject() {
            return object;
        }

        /**
         * @return The ID of an existing customer that will be charged in this request. Either customer id or customer information is required
         */
        public String getId() {
            return id;
        }

        /**
         * @return Customer's First Name
         */
        public String getFirst_name() {
            return first_name;
        }

        /**
         * @return Customer's Last Name
         */
        public String getLast_name() {
            return last_name;
        }

        /**
         * @return The customer’s email address.
         */
        public String getEmail() {
            return email;
        }

        /**
         * @return Customer phone (including extension).
         */
        public String getPhone() {
            return phone;
        }

        @Override
        public String toString() {
            return "Customer {" +
                    "\n        object =  '" + object + '\'' +
                    "\n        id =  '" + id + '\'' +
                    "\n        first_name =  '" + first_name + '\'' +
                    "\n        last_name =  '" + last_name + '\'' +
                    "\n        email =  '" + email + '\'' +
                    "\n        phone =  '" + phone + '\'' +
                    "\n    }";
        }
    }

    //getters
    /**
     * @return Unique identifier for the object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return String representing the object&#8217;s type. Objects of the same type share the same value. (value is "charge")
     */
    public String getObject() {
        return object;
    }

    /**
     * @return Flag indicating whether the object exists in live mode or test mode.
     */
    public boolean isLive_mode() {
        return live_mode;
    }

    /**
     * @return Its a charge status, values can be one of INITIATED, IN_PROGRESS, CANCELLED, FAILED, DECLINED, RESTRICTED, CAPTURED, VOID
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Terminal ID (Virtual terminal ID or Physical Terminal ID (POS))
     */
    public String getTerminal_id() {
        return terminal_id;
    }

    /**
     * @return Transaction Authorization ID issued by Gateway
     */
    public String getAuth_id() {
        return auth_id;
    }

    /**
     * @return The 3D Secure request status for a particular charge, values can be one of(true or false)
     */
    public boolean isThreeDSecure() {
        return threeDSecure;
    }

    /**
     * @return Merchant Transaction Reference Number
     */
    public String getTransaction_reference() {
        return transaction_reference;
    }

    /**
     * @return Merchant Order Reference Number
     */
    public String getOrder_reference() {
        return order_reference;
    }

    /**
     * @return Time format of the charge (UTC+3:00)
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @return Time at which the object was created. Measured in seconds since the Unix epoch.
     */
    public long getCreated() {
        return created;
    }

    /**
     * @return Time at which the object was posted into the business statement. Measured in seconds since the Unix epoch.
     */
    public long getPost() {
        return post;
    }

    /**
     * @return A positive double, representing how much to charge.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return Three-letter ISO currency code, in lowercase. Must be a supported currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return Extra information about a charge. This will appear on your customer&#8217;s credit card statement. It must contain at least one letter.
     */
    public String getStatement_descriptor() {
        return statement_descriptor;
    }

    /**
     * @return Whether chargeback applied or not
     */
    public boolean isChargeback() {
        return chargeback;
    }

    /**
     * @return Charges Response Code and Messages from Tap
     */
    public Response getResponse() {
        return response;
    }

    /**
     * @return Charges Response Code and Messages from Acquirer
     */
    public Response getAcquirer() {
        return acquirer;
    }

    /**
     * @return Whether Receipt email and sms need to be sent or not, default will be true (if customer emil and phone info available, then receipt will be sent)
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * @return The ID of an existing customer that will be charged in this request.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return For most Tap users, the source of every charge is a credit or debit card. This hash is then the card object describing that card.
     */
    public SourceResponse getSource() {
        return source;
    }

    /**
     * @return Information related to the payment page redirect.
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * @return An arbitrary string attached to the object. It is displayed when in the web interface alongside the charge. Note that if you use Tap to send automatic email receipts to your customers, your receipt emails will include the description of the charge(s) that they are describing. optional, default is None
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return HashMap of key/value pairs that you can attach to an object. It can be useful for storing additional information about the object in a structured format.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "\nCharge {" +
                "\n    id =  '" + id + '\'' +
                "\n    object =  '" + object + '\'' +
                "\n    live_mode =  " + live_mode +
                "\n    status =  " + status +
                "\n    terminal_id =  " + terminal_id +
                "\n    auth_id =  " + auth_id +
                "\n    threeDSecure =  " + threeDSecure +
                "\n    transaction_reference =  " + transaction_reference +
                "\n    order_reference =  " + order_reference +
                "\n    timezone =  " + timezone +
                "\n    created =  " + created +
                "\n    post =  " + post +
                "\n    amount =  " + amount +
                "\n    currency =  '" + currency + '\'' +
                "\n    statement_descriptor =  '" + statement_descriptor + '\'' +
                "\n    chargeback =  '" + chargeback + '\'' +
                "\n    response =  '" + response + '\'' +
                "\n    acquirer =  " + acquirer +
                "\n    receipt =  " + receipt +
                "\n    customer =  " + customer +
                "\n    source =  " + source +
                "\n    redirect =  " + redirect +
                "\n    description =  " + description +
                "\n    metadata =  " + metadata +
                "\n}";
    }
}
