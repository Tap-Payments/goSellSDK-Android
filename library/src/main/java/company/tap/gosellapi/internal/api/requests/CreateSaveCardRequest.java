package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class CreateSaveCardRequest {

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("customer")
    @Expose
    @NonNull private Customer customer;

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

    @SerializedName("card")
    @Expose
    @Nullable private boolean card;

    @SerializedName("promo")
    @Expose
    @Nullable private Boolean promo = true;

    @SerializedName("loyalty")
    @Expose
    @Nullable private Boolean loyalty = true;

    @SerializedName("risk")
    @Expose
    @Nullable private Boolean risk = true;

    @SerializedName("issuer")
    @Expose
    @Nullable private Boolean issuer = true;

    public CreateSaveCardRequest(@NonNull     String                  currency,
                                 @NonNull     Customer                customer,
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
                                 @Nullable    boolean                 card,
                                 @Nullable    boolean                 risk,
                                 @Nullable    boolean                 issuer,
                                 @Nullable    boolean                 promo,
                                 @Nullable    boolean                 loyalty) {

        this.currency               = currency;
        this.customer               = customer;
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
        this.card                   = card;
        this.risk                   = risk;
        this.issuer                 = issuer;
        this.promo                  = promo;
        this.loyalty                = loyalty;
    }
}
