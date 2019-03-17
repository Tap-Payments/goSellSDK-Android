package company.tap.gosellapi.internal.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Destination;
import company.tap.gosellapi.open.models.Destinations;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;

/**
 * The interface Payment data provider.
 */
public interface IPaymentDataProvider {

    /**
     * Gets selected currency.
     *
     * @return the selected currency
     */
    @NonNull    AmountedCurrency            getSelectedCurrency();

    /**
     * Gets supported currencies.
     *
     * @return the supported currencies
     */
    @NonNull    ArrayList<AmountedCurrency> getSupportedCurrencies();

    /**
     * Gets payment options order id.
     *
     * @return the payment options order id
     */
    @NonNull    String                      getPaymentOptionsOrderID();

    /**
     * Gets post url.
     *
     * @return the post url
     */
    @Nullable   String                      getPostURL();

    /**
     * Gets customer.
     *
     * @return the customer
     */
    @NonNull    Customer                    getCustomer();

    /**
     * Gets payment description.
     *
     * @return the payment description
     */
    @Nullable   String                      getPaymentDescription();

    /**
     * Gets payment metadata.
     *
     * @return the payment metadata
     */
    @Nullable   HashMap<String, String>     getPaymentMetadata();

    /**
     * Gets payment reference.
     *
     * @return the payment reference
     */
    @Nullable   Reference                   getPaymentReference();

    /**
     * Gets payment statement descriptor.
     *
     * @return the payment statement descriptor
     */
    @Nullable   String                      getPaymentStatementDescriptor();

    /**
     * Gets requires 3 d secure.
     *
     * @return the requires 3 d secure
     */
    @NonNull    boolean                     getRequires3DSecure();

    /**
     * Gets receipt settings.
     *
     * @return the receipt settings
     */
    @Nullable   Receipt                     getReceiptSettings();

    /**
     * Gets transaction mode.
     *
     * @return the transaction mode
     */
    @NonNull    TransactionMode             getTransactionMode();

    /**
     * Gets authorize action.
     *
     * @return the authorize action
     */
    @NonNull    AuthorizeAction             getAuthorizeAction();

    /**
     * get Destination
     */
    @Nullable    Destinations               getDestination();
}
