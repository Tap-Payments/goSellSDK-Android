package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.Merchant;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.TopUp;

/**
 * The type Create authorize request.
 */
public final class CreateAuthorizeRequest extends CreateChargeRequest {

    @SerializedName("auto")
    @Expose
    @NonNull private AuthorizeAction authorizeAction;

    /**
     * Instantiates a new Create authorize request.
     *
     * @param amount              the amount
     * @param currency            the currency
     * @param selectedAmount      the user selected amount
     * @param selectedCurrency    the user selected currency
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
     * @param authorizeAction     the authorize action
     * @param destinations        the destinations object
     */
    public CreateAuthorizeRequest(
                                  @NonNull  Merchant                merchant,
                                  @NonNull  BigDecimal              amount,
                                  @NonNull  String                  currency,
                                  @NonNull  BigDecimal              selectedAmount,
                                  @NonNull  String                  selectedCurrency,
                                  @NonNull  Customer                customer,
                                  @NonNull  BigDecimal              fee,
                                  @NonNull  Order                   order,
                                  @NonNull  TrackingURL             redirect,
                                  @Nullable TrackingURL             post,
                                  @NonNull  SourceRequest           source,
                                  @Nullable String                  description,
                                  @Nullable HashMap<String, String> metadata,
                                  @Nullable Reference               reference,
                                  @NonNull  boolean                 saveCard,
                                  @Nullable String                  statementDescriptor,
                                  @Nullable boolean                 threeDSecure,
                                  @Nullable Receipt                 receipt,
                                  @NonNull  AuthorizeAction         authorizeAction,
                                  @Nullable Destinations destinations,
                                  @Nullable TopUp topUp) {

        super(merchant,amount, currency,selectedAmount, selectedCurrency,customer, fee, order, redirect, post, source, description, metadata, reference, saveCard, statementDescriptor, threeDSecure, receipt,destinations,topUp);

        this.authorizeAction = authorizeAction;
    }
}
