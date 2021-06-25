package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Merchant;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.TopUp;

/**
 * The type Create charge request.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class CreateChargeRequest {

    @SerializedName("merchant")
    @Expose
    @Nullable private Merchant merchant;

    @SerializedName("amount")
    @Expose
    @NonNull private BigDecimal amount;

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("selected_amount")
    @Expose
    @NonNull private BigDecimal selectedAmount;

    @SerializedName("selected_currency")
    @Expose
    @NonNull private String selectedCurrency;

    @SerializedName("customer")
    @Expose
    @NonNull private Customer customer;

    @SerializedName("fee")
    @Expose
    @NonNull private BigDecimal fee;

    @SerializedName("order")
    @Expose
    @NonNull private Order order;

    @SerializedName("redirect")
    @Expose
    @NonNull private TrackingURL redirect;

    @SerializedName("post")
    @Expose
    @Nullable private TrackingURL post;

    @SerializedName("source")
    @Expose
    @NonNull private SourceRequest source;

    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("metadata")
    @Expose
    @Nullable private HashMap<String, String> metadata;

    @SerializedName("reference")
    @Expose
    @Nullable private Reference reference;

    @SerializedName("save_card")
    @Expose
    private boolean saveCard;

    @SerializedName("statement_descriptor")
    @Expose
    @Nullable private String statementDescriptor;

    @SerializedName("threeDSecure")
    @Expose
    @Nullable private Boolean threeDSecure = true;

    @SerializedName("receipt")
    @Expose
    @Nullable private Receipt receipt;

    @SerializedName("destinations")
    @Expose
    @Nullable private Destinations destinations;

    @SerializedName("topup")
    @Expose
    @Nullable private TopUp topup;


    /**
     * Instantiates a new Create charge request.
     * @param merchant            the merchant
     * @param amount              the amount
     * @param currency            the currency
     * @param customer            the customer
     * @param fee                 the fee
     * @param order               the order
     * @param redirect            the redirect
     * @param post                the post
     * @param source              the source
     * @param description         the description
     * @param metadata            the metadata
     * @param reference           the reference
     * @param saveCard            the save card
     * @param statementDescriptor the statement descriptor
     * @param threeDSecure        the three d secure
     * @param receipt             the receipt
     * @param destinations        the destinations
     * @param topUp               the topUp
     */
    public CreateChargeRequest(
                               @Nullable    Merchant                merchant,
                               @NonNull     BigDecimal              amount,
                               @NonNull     String                  currency,
                               @NonNull     BigDecimal              selectedAmount,
                               @NonNull     String                  selectedCurrency,
                               @NonNull     Customer                customer,
                               @NonNull     BigDecimal              fee,
                               @NonNull     Order                   order,
                               @NonNull     TrackingURL             redirect,
                               @Nullable    TrackingURL             post,
                               @NonNull     SourceRequest           source,
                               @Nullable    String                  description,
                               @Nullable    HashMap<String, String> metadata,
                               @Nullable    Reference               reference,
                               @NonNull     boolean                 saveCard,
                               @Nullable    String                  statementDescriptor,
                               @Nullable    boolean                 threeDSecure,
                               @Nullable    Receipt                 receipt,
                               @Nullable    Destinations destinations,
                               @Nullable    TopUp                 topUp) {

        this.merchant               = merchant;
        this.amount                 = amount;
        this.currency               = currency;
        this.selectedAmount         = selectedAmount;
        this.selectedCurrency       = selectedCurrency;
        this.customer               = customer;
        this.fee                    = fee;
        this.order                  = order;
        this.redirect               = redirect;
        this.post                   = post;
        this.source                 = source;
        this.description            = description;
        this.metadata               = metadata;
        this.reference              = reference;
        this.saveCard               = saveCard;
        this.statementDescriptor    = statementDescriptor;
        this.threeDSecure           = threeDSecure;
        this.receipt                = receipt;
        this.destinations           = destinations;
        this.topup                  = topUp;
    }
}
