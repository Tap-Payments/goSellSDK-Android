package company.tap.gosellapi.internal.data_managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.open.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;
import company.tap.gosellapi.internal.api.requests.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.api.responses.SDKSettings;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.open.interfaces.PaymentDataSource;

public final class PaymentDataManager {

    @Nullable   private PaymentDataSource externalDataSource;
    @NonNull    private IPaymentDataProvider dataProvider = new PaymentDataProvider();
    @NonNull    private PaymentProcessListener processListener = new PaymentProcessListener();
    @NonNull    private PaymentProcessManager paymentProcessManager = new PaymentProcessManager(getPaymentDataProvider(), getProcessListener());

    @NonNull private IPaymentDataProvider getPaymentDataProvider() {

        return dataProvider;
    }

    @NonNull private PaymentProcessListener getProcessListener() {

        return processListener;
    }

    @NonNull private PaymentProcessManager getPaymentProcessManager() {

        return paymentProcessManager;
    }

    private SDKSettings SDKSettings;
    private PaymentOptionsRequest paymentOptionsRequest;
    private PaymentOptionsDataManager paymentOptionsDataManager;
    private BINLookupResponse binLookupResponse;

    private PaymentDataManager() {}

    /////////////////////////////////////////    ########### Start of Singleton section ##################
    /**
     * Here we will use inner class to create a singleton object of PaymentDataManager
     * Inner class singleton approach introduced by Bill Pugh to overcome other singleton approaches :
     *    - Eager initialization
     *    - Static block initialization
     *    - Lazy load initialization
     *    - thread safe initialization
     * in this approach create the Singleton class using a inner static helper class
     * When the singleton class is loaded, SingletonCreationAdmin class is not loaded into memory
     *  and only when someone calls the getInstance method.
     */
    private static class SingletonCreationAdmin {
        private static final PaymentDataManager INSTANCE = new PaymentDataManager();
    }

    /**
     * Singleton getInstance method
     * @return
     */
    public static PaymentDataManager getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }
    /////////////////////////////////////////  ########### End of Singleton section ##################

    public PaymentDataSource getExternalDataSource() {
        return externalDataSource;
    }

    public void setExternalDataSource(PaymentDataSource externalDataSource) {
        this.externalDataSource = externalDataSource;
    }

    public SDKSettings getSDKSettings() {
        return SDKSettings;
    }

    public void setSDKSettings(SDKSettings SDKSettings) {
        this.SDKSettings = SDKSettings;
    }

    public PaymentOptionsRequest getPaymentOptionsRequest() {
        return paymentOptionsRequest;
    }

    public BINLookupResponse getBinLookupResponse() {
        return binLookupResponse;
    }

    public WebPaymentURLDecision decisionForWebPaymentURL(String url) {

        return getPaymentProcessManager().decisionForWebPaymentURL(url);
    }

    public void setPaymentOptionsRequest(PaymentOptionsRequest paymentOptionsRequest) {
        this.paymentOptionsRequest = paymentOptionsRequest;
    }

    public void setBinLookupResponse(BINLookupResponse binLookupResponse) {
        this.binLookupResponse = binLookupResponse;
    }

    public void createPaymentOptionsDataManager(PaymentOptionsResponse paymentOptionsResponse) {
        paymentOptionsDataManager = new PaymentOptionsDataManager(paymentOptionsResponse);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager(PaymentOptionsDataManager.PaymentOptionsDataListener dataListener) {
        return paymentOptionsDataManager.setListener(dataListener);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager() {
        return paymentOptionsDataManager;
    }

    public void initiatePayment(PaymentOptionViewModel model, IPaymentProcessListener listener) {

        getProcessListener().addListener(listener);
        getPaymentProcessManager().startPaymentProcess(model);
    }

    public final class WebPaymentURLDecision {

        @NonNull private boolean shouldLoad;
        @NonNull private boolean shouldCloseWebPaymentScreen;
        @NonNull private boolean redirectionFinished;

        @Nullable private String tapID;

        @NonNull public boolean shouldLoad() {

            return shouldLoad;
        }

        @NonNull boolean shouldCloseWebPaymentScreen() {

            return shouldCloseWebPaymentScreen;
        }

        @NonNull boolean redirectionFinished() {

            return redirectionFinished;
        }

        @Nullable String getTapID() {

            return tapID;
        }

        WebPaymentURLDecision(@NonNull boolean shouldLoad, @NonNull boolean shouldCloseWebPaymentScreen, @NonNull boolean redirectionFinished, @Nullable String tapID) {

            this.shouldLoad                     = shouldLoad;
            this.shouldCloseWebPaymentScreen    = shouldCloseWebPaymentScreen;
            this.redirectionFinished            = redirectionFinished;
            this.tapID                          = tapID;
        }
    }

    private class PaymentDataProvider implements IPaymentDataProvider {

        @NonNull
        @Override
        public AmountedCurrency getSelectedCurrency() {

            return getPaymentOptionsDataManager().getSelectedCurrency();
        }

        @NonNull
        @Override
        public ArrayList<AmountedCurrency> getSupportedCurrencies() {

            return getPaymentOptionsDataManager().getPaymentOptionsResponse().getSupportedCurrencies();
        }

        @NonNull
        @Override
        public String getPaymentOptionsOrderID() {

            return getPaymentOptionsDataManager().getPaymentOptionsResponse().getOrderID();
        }

        @Nullable
        @Override
        public String getPostURL() {

            return getExternalDataSource().getPostURL();
        }

        @NonNull
        @Override
        public Customer getCustomer() {

            return getExternalDataSource().getCustomer();
        }

        @Nullable
        @Override
        public String getPaymentDescription() {

            return getExternalDataSource().getPaymentDescription();
        }

        @Nullable
        @Override
        public HashMap<String, String> getPaymentMetadata() {

            return getExternalDataSource().getPaymentMetadata();
        }

        @Nullable
        @Override
        public Reference getPaymentReference() {

            return getExternalDataSource().getPaymentReference();
        }

        @Nullable
        @Override
        public String getPaymentStatementDescriptor() {

            return getExternalDataSource().getPaymentStatementDescriptor();
        }

        @NonNull
        @Override
        public boolean getRequires3DSecure() {

            boolean merchantRequires3DSecure = getSDKSettings().getData().getPermissions().contains(Permission.THREEDSECURE_DISABLED);
            return merchantRequires3DSecure || getExternalDataSource().getRequires3DSecure();
        }

        @Nullable
        @Override
        public Receipt getReceiptSettings() {

            return getExternalDataSource().getReceiptSettings();
        }

        @NonNull
        @Override
        public TransactionMode getTransactionMode() {

            TransactionMode transactionMode = getExternalDataSource().getTransactionMode();
            if ( transactionMode == null ) { transactionMode = TransactionMode.PURCHASE; }

            return transactionMode;
        }

        @NonNull
        @Override
        public AuthorizeAction getAuthorizeAction() {

            AuthorizeAction authorizeAction = getExternalDataSource().getAuthorizeAction();
            if ( authorizeAction == null ) { authorizeAction = AuthorizeAction.getDefault(); }

            return authorizeAction;
        }
    }

    private class PaymentProcessListener implements IPaymentProcessListener {

        @Override
        public void didReceiveCharge(Charge charge) {

            for ( IPaymentProcessListener listener : getListeners() ) {

                listener.didReceiveCharge(charge);
            }
        }

        @Override
        public void didReceiveAuthorize(Authorize authorize) {

            for ( IPaymentProcessListener listener : getListeners() ) {

                listener.didReceiveAuthorize(authorize);
            }
        }

        @Override
        public void didReceiveError(GoSellError error) {

            for ( IPaymentProcessListener listener : getListeners() ) {

                listener.didReceiveError(error);
            }
        }

        private void addListener(IPaymentProcessListener listener) {

            getListeners().add(listener);
        }

        private void removeListener(IPaymentProcessListener listener) {

            getListeners().remove(listener);
        }

        @NonNull private ArrayList<IPaymentProcessListener> listeners;

        private PaymentProcessListener() {

            this.listeners = new ArrayList<>();
        }

        private ArrayList<IPaymentProcessListener> getListeners() {

            return listeners;
        }
    }
}
