package company.tap.gosellapi.internal.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Customer;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;

public interface IPaymentDataProvider {

    @NonNull    AmountedCurrency            getSelectedCurrency();
    @NonNull    ArrayList<AmountedCurrency> getSupportedCurrencies();
    @NonNull    String                      getPaymentOptionsOrderID();
    @Nullable   String                      getPostURL();
    @NonNull    Customer                    getCustomer();
    @Nullable   String                      getPaymentDescription();
    @Nullable   HashMap<String, String>     getPaymentMetadata();
    @Nullable   Reference                   getPaymentReference();
    @Nullable   String                      getPaymentStatementDescriptor();
    @NonNull    boolean                     getRequires3DSecure();
    @Nullable   Receipt                     getReceiptSettings();
    @NonNull    TransactionMode             getTransactionMode();
    @NonNull    AuthorizeAction             getAuthorizeAction();
}
