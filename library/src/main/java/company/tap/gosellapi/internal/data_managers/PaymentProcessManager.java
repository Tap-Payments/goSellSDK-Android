package company.tap.gosellapi.internal.data_managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.enums.TransactionMode;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.AuthorizeAction;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.Customer;
import company.tap.gosellapi.internal.api.models.ExtraFee;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.internal.api.requests.CreateAuthorizeRequest;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.AmountCalculator;
import company.tap.gosellapi.internal.utils.CurrencyFormatter;

final class PaymentProcessManager {

    PaymentDataManager.WebPaymentURLDecision decisionForWebPaymentURL(String url) {

        boolean urlIsReturnURL = url.startsWith(Constants.RETURN_URL);
        boolean shouldLoad = !urlIsReturnURL;
        boolean redirectionFinished = urlIsReturnURL;
        boolean shouldCloseWebPaymentScreen = redirectionFinished && currentPaymentViewModel.getPaymentOption().getPaymentType() == PaymentType.CARD;

        @Nullable String tapID = null;

        Uri uri = Uri.parse(url);
        if (uri.getQueryParameterNames().contains(Constants.TAP_ID)) {

            tapID = uri.getQueryParameter(Constants.TAP_ID);
        }

        return PaymentDataManager.getInstance().new WebPaymentURLDecision(shouldLoad, shouldCloseWebPaymentScreen, redirectionFinished, tapID);
    }

    void startPaymentProcess(@NonNull final PaymentOptionViewModel paymentOptionModel) {

        IPaymentDataProvider provider = getDataProvider();

        AmountedCurrency amount = provider.getSelectedCurrency();
        ArrayList<ExtraFee> extraFees = paymentOptionModel.getPaymentOption().getExtraFees();
        if (extraFees == null) { extraFees = new ArrayList<>(); }
        ArrayList<AmountedCurrency> supportedCurrencies = provider.getSupportedCurrencies();
        BigDecimal feesAmount = AmountCalculator.calculateExtraFeesAmount(extraFees,supportedCurrencies,amount);

        if (feesAmount.compareTo(BigDecimal.ZERO) == 1) {

            AmountedCurrency extraFeesAmount = new AmountedCurrency(amount.getCurrency(), feesAmount);

            showExtraFeesAlert(amount, extraFeesAmount, new DialogManager.DialogResult() {
                @Override
                public void dialogClosed(boolean positiveButtonClicked) {

                    if ( positiveButtonClicked ) {

                        forceStartPaymentProcess(paymentOptionModel);
                    }
                }
            });
        }
        else {

            forceStartPaymentProcess(paymentOptionModel);
        }
    }

    PaymentProcessManager(@NonNull IPaymentDataProvider dataProvider, @NonNull IPaymentProcessListener listener) {

        this.dataProvider       = dataProvider;
        this.processListener    = listener;
    }

    @NonNull
    IPaymentDataProvider getDataProvider() {

        return dataProvider;
    }

    @NonNull
    IPaymentProcessListener getProcessListener() {

        return processListener;
    }

    @Nullable private PaymentOptionViewModel currentPaymentViewModel;

    private IPaymentDataProvider dataProvider;
    private IPaymentProcessListener processListener;

    private void showExtraFeesAlert(AmountedCurrency amount, AmountedCurrency extraFeesAmount, DialogManager.DialogResult callback) {

        AmountedCurrency totalAmount = new AmountedCurrency(amount.getCurrency(), amount.getAmount().add(extraFeesAmount.getAmount()), amount.getSymbol());

        String extraFeesText = CurrencyFormatter.format(extraFeesAmount);
        String totalAmountText = CurrencyFormatter.format(totalAmount);

        String title = "Confirm extra charges";
        String message = String.format("You will be charged an additional fee of %s for this type of payment, totaling an amount of %s", extraFeesText, totalAmountText);

        DialogManager.getInstance().showDialog(title, message, "Confirm", "Cancel", callback);
    }

    private void forceStartPaymentProcess(@NonNull PaymentOptionViewModel paymentOptionModel) {

        if ( paymentOptionModel instanceof WebPaymentViewModel ) {

            startPaymentProcessWithWebPaymentModel((WebPaymentViewModel) paymentOptionModel);
        }
        else if ( paymentOptionModel instanceof CardCredentialsViewModel ) {

            startPaymentProcessWithCardPaymentModel((CardCredentialsViewModel) paymentOptionModel);
        }
    }

    private void startPaymentProcessWithWebPaymentModel(@NonNull WebPaymentViewModel paymentOptionModel) {

        PaymentOption paymentOption = paymentOptionModel.getPaymentOption();
        SourceRequest source = new SourceRequest(paymentOption.getSourceId());

        callChargeOrAuthorizeAPI(source, paymentOption, null, null);
    }

    private void startPaymentProcessWithCardPaymentModel(@NonNull CardCredentialsViewModel paymentOptionModel) {

        @Nullable CreateTokenCard card = paymentOptionModel.getCard();
        if ( card == null ) { return; }

        startPaymentProcessWithCard(card, paymentOptionModel.getPaymentOption(), paymentOptionModel.shouldSaveCard());
    }

    private void startPaymentProcessWithCard(@NonNull CreateTokenCard card, PaymentOption paymentOption, boolean saveCard) {

        CreateTokenWithCardDataRequest request = new CreateTokenWithCardDataRequest(card);
        callTokenAPI(request, paymentOption, saveCard);
    }

    private void callTokenAPI(@NonNull CreateTokenRequest request, @NonNull final PaymentOption paymentOption, @Nullable final boolean saveCard) {

        GoSellAPI.getInstance().createToken(request, new APIRequestCallback<Token>() {

            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {

                SourceRequest source = new SourceRequest(serializedResponse);
                callChargeOrAuthorizeAPI(source, paymentOption, serializedResponse.getCard().getFirstSix(), saveCard);
            }

            @Override
            public void onFailure(GoSellError errorDetails) { }
        });
    }

    private void callChargeOrAuthorizeAPI(@NonNull SourceRequest source, @NonNull PaymentOption paymentOption, @Nullable String cardBIN, @Nullable Boolean saveCard) {

        Log.e("OkHttp", "CALL CHARGE API");

        IPaymentDataProvider provider = getDataProvider();

        ArrayList<AmountedCurrency> supportedCurrencies = provider.getSupportedCurrencies();
        String orderID = provider.getPaymentOptionsOrderID();
        @Nullable String postURL = provider.getPostURL();

        @Nullable TrackingURL post = postURL == null ? null : new TrackingURL(postURL);

        AmountedCurrency        amountedCurrency    = provider.getSelectedCurrency();
        Customer                customer            = provider.getCustomer();
        BigDecimal              fee                 = AmountCalculator.calculateExtraFeesAmount(paymentOption.getExtraFees(), supportedCurrencies, amountedCurrency);
        Order                   order               = new Order(orderID);
        TrackingURL             redirect            = new TrackingURL(Constants.RETURN_URL);
        String                  paymentDescription  = provider.getPaymentDescription();
        HashMap<String, String> paymentMetadata     = provider.getPaymentMetadata();
        Reference               reference           = provider.getPaymentReference();
        boolean                 shouldSaveCard      = saveCard == null ? false : saveCard;
        String                  statementDescriptor = provider.getPaymentStatementDescriptor();
        boolean                 require3DSecure     = provider.getRequires3DSecure();// this.dataSource.getRequires3DSecure() || this.chargeRequires3DSecure();
        Receipt                 receipt             = provider.getReceiptSettings();
        TransactionMode         transactionMode     = provider.getTransactionMode();

        switch (transactionMode) {

            case PURCHASE:

                CreateChargeRequest chargeRequest = new CreateChargeRequest(

                        amountedCurrency.getAmount(),
                        amountedCurrency.getCurrency(),
                        customer,
                        fee,
                        order,
                        redirect,
                        post,
                        source,
                        paymentDescription,
                        paymentMetadata,
                        reference,
                        shouldSaveCard,
                        statementDescriptor,
                        require3DSecure,
                        receipt
                );

                GoSellAPI.getInstance().createCharge(chargeRequest, new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {

                        handleChargeOrAuthorizeResponse(serializedResponse, null);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {

                        handleChargeOrAuthorizeResponse(null, errorDetails);
                    }
                });

                break;

            case AUTHORIZE_CAPTURE:

                AuthorizeAction authorizeAction = provider.getAuthorizeAction();

                CreateAuthorizeRequest authorizeRequest = new CreateAuthorizeRequest(

                        amountedCurrency.getAmount(),
                        amountedCurrency.getCurrency(),
                        customer,
                        fee,
                        order,
                        redirect,
                        post,
                        source,
                        paymentDescription,
                        paymentMetadata,
                        reference,
                        shouldSaveCard,
                        statementDescriptor,
                        require3DSecure,
                        receipt,
                        authorizeAction
                );

                GoSellAPI.getInstance().createAuthorize(authorizeRequest, new APIRequestCallback<Authorize>() {
                    @Override
                    public void onSuccess(int responseCode, Authorize serializedResponse) {

                        handleChargeOrAuthorizeResponse(serializedResponse, null);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {

                        handleChargeOrAuthorizeResponse(null, errorDetails);
                    }
                });
        }
    }

    private void handleChargeOrAuthorizeResponse(@Nullable Charge chargeOrAuthorize, @Nullable GoSellError error) {

        if ( chargeOrAuthorize != null ) {

            if ( chargeOrAuthorize instanceof Authorize ) {

                getProcessListener().didReceiveAuthorize((Authorize) chargeOrAuthorize);
            }
            else {

                getProcessListener().didReceiveCharge(chargeOrAuthorize);
            }
        }
    }
}
