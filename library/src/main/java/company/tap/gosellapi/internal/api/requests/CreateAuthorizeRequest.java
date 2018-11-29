package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Customer;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.TrackingURL;

public final class CreateAuthorizeRequest extends CreateChargeRequest {

    @SerializedName("auto")
    @Expose
    @NonNull private AuthorizeAction authorizeAction;

    public CreateAuthorizeRequest(@NonNull  BigDecimal              amount,
                                  @NonNull  String                  currency,
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
                                  @NonNull  AuthorizeAction         authorizeAction) {

        super(amount, currency, customer, fee, order, redirect, post, source, description, metadata, reference, saveCard, statementDescriptor, threeDSecure, receipt);

        this.authorizeAction = authorizeAction;
    }
}
