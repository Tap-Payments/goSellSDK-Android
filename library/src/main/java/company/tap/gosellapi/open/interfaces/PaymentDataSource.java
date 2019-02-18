package company.tap.gosellapi.open.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.Tax;
import company.tap.gosellapi.open.models.TapCurrency;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface PaymentDataSource {

    /**Transaction currency.*/
    @NonNull     TapCurrency            getCurrency();

    /**Customer.*/
    @NonNull     Customer                getCustomer();

    /**Amount. Either amount or items should return nonnull value. If both return nonnull, then items is preferred.*/
    @Nullable    BigDecimal              getAmount();

    /**List of items to pay for. Either amount or items should return nonnull value. If both return nonnull, then items is preferred.*/
    @Nullable    ArrayList<PaymentItem>  getItems();

    /**Transaction mode. If you return null in this method, it will be treated as PURCHASE.*/
    @Nullable    TransactionMode         getTransactionMode();

    /**List of taxes. Optional. Note: specifying taxes will affect total payment amount.*/
    @Nullable    ArrayList<Tax>          getTaxes();

    /**Shipping list. Optional. Note: specifying shipping will affect total payment amount.*/
    @Nullable    ArrayList<Shipping>     getShipping();

    /**Tap will post to this URL after transaction finishes. Optional.*/
    @Nullable    String                  getPostURL();

    /**Description of the payment.*/
    @Nullable    String                  getPaymentDescription();

    /**If you would like to pass additional information with the payment, pass it here.*/
    @Nullable    HashMap<String, String> getPaymentMetadata();

    /**Payment reference. Implement this property to keep a reference to the transaction on your backend.*/
    @Nullable    Reference               getPaymentReference();

    /**Payment statement descriptor.*/
    @Nullable    String                  getPaymentStatementDescriptor();

    /**Defines if 3D secure check is required.*/
    @Nullable    boolean                 getRequires3DSecure();

    /**Receipt dispatch settings.*/
    @Nullable    Receipt                 getReceiptSettings();

    /**Action to perform after authorization succeeds. Used only if transaction mode is AUTHORIZE_CAPTURE.*/
    @Nullable    AuthorizeAction         getAuthorizeAction();
}
