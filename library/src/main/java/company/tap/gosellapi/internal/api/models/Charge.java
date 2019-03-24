package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.ChargeStatus;
import company.tap.gosellapi.internal.api.responses.BaseResponse;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destination;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Charge response
 */
//@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Charge implements BaseResponse, Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("live_mode")
    @Expose
    private boolean liveMode;

    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("authenticate")
    @Expose
    @Nullable private Authenticate authenticate;

    @SerializedName("redirect")
    @Expose
    private TrackingURL redirect;

    @SerializedName("post")
    @Expose
    @Nullable private TrackingURL post;

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("status")
    @Expose
    private ChargeStatus status;

    @SerializedName("threeDSecure")
    @Expose
    private boolean threeDSecure;

    @SerializedName("transaction")
    @Expose
    private TransactionDetails transaction;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("metadata")
    @Expose
    @Nullable private HashMap<String, String> metadata;

    @SerializedName("reference")
    @Expose
    @Nullable private Reference reference;

    @SerializedName("receipt")
    @Expose
    @Nullable private Receipt receipt;

    @SerializedName("response")
    @Expose
    @Nullable private Response response;

    @SerializedName("statement_descriptor")
    @Expose
    @Nullable private String statementDescriptor;

    @SerializedName("destinations")
    @Expose
    @Nullable private Destinations destinations;

    /**
     * Gets id.
     *
     * @return Charge identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets amount.
     *
     * @return Charge amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets currency.
     *
     * @return Transaction currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets customer.
     *
     * @return Customer information.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Is live mode boolean.
     *
     * @return Defines whether the charge is in real environment.
     */
    public boolean isLiveMode() {
        return liveMode;
    }

    /**
     * Gets object.
     *
     * @return Object type. For charge, “charge” always.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets authenticate.
     *
     * @return Required authentication options (if any).
     */
    public Authenticate getAuthenticate() {
        return authenticate;
    }

    /**
     * Gets redirect.
     *
     * @return Charge redirect.
     */
    public TrackingURL getRedirect() {
        return redirect;
    }

    /**
     * Gets post.
     *
     * @return the post
     */
    public @Nullable TrackingURL getPost() {

        return post;
    }

    /**
     * Gets source.
     *
     * @return Charge source
     */
    public Source getSource() {
        return source;
    }

    /**
     * Gets status.
     *
     * @return Charge status.
     */
    public ChargeStatus getStatus() {
        return status;
    }

    /**
     * Is three d secure boolean.
     *
     * @return Defines if 3D secure is required
     */
    public boolean isThreeDSecure() {
        return threeDSecure;
    }

    /**
     * Gets transaction.
     *
     * @return Transaction details.
     */
    public TransactionDetails getTransaction() {
        return transaction;
    }

    /**
     * Gets description.
     *
     * @return Charge description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets metadata.
     *
     * @return Charge metadata.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Gets reference.
     *
     * @return Merchant reference object.
     */
    public Reference getReference() {
        return reference;
    }

    /**
     * Gets receipt.
     *
     * @return Receipt settings.
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Gets response.
     *
     * @return Charge response code and message.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Gets statement descriptor.
     *
     * @return Statement descriptor.
     */
    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    /**
     * Gets destinations
     * @return
     */
    @Nullable
    public Destinations getDestinations() {
        return destinations;
    }
}
