package company.tap.gosellapi.open.controllers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Address;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.SourceRequest;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.internal.api.models.TrackingURL;
import company.tap.gosellapi.internal.api.requests.CreateSaveCardRequest;
import company.tap.gosellapi.internal.api.requests.CreateTokenWithCardDataRequest;
import company.tap.gosellapi.internal.api.responses.ListCardsResponse;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.interfaces.IPaymentDataProvider;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.models.CardsList;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.Receipt;
import company.tap.gosellapi.open.models.Reference;


public class APIsExposer {
    /**
     * Singleton getInstance method
     *
     * @return the instance
     */

    private APIsExposer() {

    }

    public static APIsExposer getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final APIsExposer INSTANCE = new APIsExposer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Exposing Tokenizing card feature without showing SDK MainActivity.
     * This feature must be available only for Merchants who are PCI enabled.
     * Merchant is responsible for collecting Card details [ cardNumber - expirationMonth - expirationMonth - cvc - cardHolderName - address]
     * and pass it to SDK.
     * SDK will tokenize card without showing MainActivity UI, hence the process will not go through
     * PaymentDataManager nor PaymentProcessManager
     */

    /**
     * @param cardNumber
     * @param expirationMonth
     * @param expirationYear
     * @param cvc
     * @param cardholderName
     * @param address
     * @param sessionDelegate
     */
    public void startToknizingCard(@NonNull String cardNumber,
                                   @NonNull String expirationMonth,
                                   @NonNull String expirationYear,
                                   @NonNull String cvc,
                                   @NonNull String cardholderName,
                                   @Nullable Address address,
                                   @NonNull SessionDelegate sessionDelegate) {

        if (!validCardData(cardNumber, expirationMonth, expirationYear, cvc, cardholderName, address))
            sessionDelegate.invalidCardDetails();

//    PaymentDataManager.getInstance().
//            startCardTokenizationPaymentProcessWithoutCardPaymentModel(
//            cardNumber,expirationMonth,expirationYear,cvc,cardholderName,address);

        CreateTokenCard card = createCard(cardNumber, expirationMonth, expirationYear, cvc, cardholderName, address);

        CreateTokenWithCardDataRequest request = new CreateTokenWithCardDataRequest(card);

        GoSellAPI.getInstance().createTokenWithEncryptedCard(request, new APIRequestCallback<Token>() {

            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {
                if (serializedResponse != null) {
                    sessionDelegate.cardTokenizedSuccessfully(serializedResponse.getId());
                } else {
                    sessionDelegate.backendUnknownError(" Tokenizing card response is null with response code :"+responseCode);
                }
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                sessionDelegate.sdkError(errorDetails);
            }
        });

    }


    /**
     * Exposing saving card feature without showing SDK MainActivity.
     * This feature must be available only for Merchants who are PCI enabled.
     * Merchant is responsible for collecting Card details [ cardNumber - expirationMonth - expirationMonth - cvc - cardHolderName - address]
     * and pass it to SDK.
     * SDK will tokenize card and save it without showing MainActivity UI, hence the process will not go through
     * PaymentDataManager nor PaymentProcessManager
     */

    /**
     * @param cardNumber
     * @param expirationMonth
     * @param expirationYear
     * @param cvc
     * @param cardholderName
     * @param address
     * @param sessionDelegate
     */
    public void startSavingCard(@NonNull String cardNumber,
                                @NonNull String expirationMonth,
                                @NonNull String expirationYear,
                                @NonNull String cvc,
                                @NonNull String cardholderName,
                                @Nullable Address address,
                                @NonNull SessionDelegate sessionDelegate) {

        if (!validCardData(cardNumber, expirationMonth, expirationYear, cvc, cardholderName, address)) {
            sessionDelegate.invalidCardDetails();
            return;
        }

        CreateTokenCard card = createCard(cardNumber, expirationMonth, expirationYear, cvc, cardholderName, address);

        CreateTokenWithCardDataRequest request = new CreateTokenWithCardDataRequest(card);

        GoSellAPI.getInstance().createTokenWithEncryptedCard(request, new APIRequestCallback<Token>() {
            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {

                SourceRequest source = new SourceRequest(serializedResponse);
                callSaveCardAPI(source, true, sessionDelegate);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                sessionDelegate.sdkError(errorDetails);
            }
        });


    }

    /**
     * @param source
     * @param b
     * @param sessionDelegate
     */
    private void callSaveCardAPI(SourceRequest source, boolean b, SessionDelegate sessionDelegate) {
        IPaymentDataProvider provider = PaymentDataManager.getInstance().getPaymentDataProvider();
        if (provider == null) {
            sessionDelegate.backendUnknownError(" callSaveCardAPI error : Payment Data Provider is null.");
            return;
        }

        String orderID = provider.getPaymentOptionsOrderID();
        @Nullable String postURL = provider.getPostURL();
        @Nullable TrackingURL post = postURL == null ? null : new TrackingURL(postURL);

        AmountedCurrency amountedCurrency = provider.getSelectedCurrency();
        Customer customer = provider.getCustomer();


        Order order = new Order(orderID);
        TrackingURL redirect = new TrackingURL(Constants.RETURN_URL);
        String paymentDescription = provider.getPaymentDescription();
        HashMap<String, String> paymentMetadata = provider.getPaymentMetadata();
        Reference reference = provider.getPaymentReference();
        String statementDescriptor = provider.getPaymentStatementDescriptor();
        boolean require3DSecure = provider
                .getRequires3DSecure();
        Receipt receipt = provider.getReceiptSettings();

        CreateSaveCardRequest saveCardRequest = new CreateSaveCardRequest(
                amountedCurrency.getCurrency(),
                customer,
                order,
                redirect,
                post,
                source,
                paymentDescription,
                paymentMetadata,
                reference,
                true,
                statementDescriptor,
                require3DSecure,
                receipt,
                true,
                true,
                true,
                true,
                true
        );

        GoSellAPI.getInstance().createSaveCard(saveCardRequest, new APIRequestCallback<SaveCard>() {
            @Override
            public void onSuccess(int responseCode, SaveCard serializedResponse) {
                sessionDelegate.cardSaved(serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                sessionDelegate.sdkError(errorDetails);
            }
        });
    }


    /**
     * Validate card details coming from Merchant activity
     *
     * @param cardNumber
     * @param expirationMonth
     * @param expirationYear
     * @param cvc
     * @param cardholderName
     * @param address
     * @return
     */
    private boolean validCardData(String cardNumber, String expirationMonth, String expirationYear, String cvc, String cardholderName, Address address) {
        return (cardNumber != null && expirationMonth != null && expirationYear != null && cvc != null && cardholderName != null);
    }


    /**
     * create TokenCard object using card details
     *
     * @param cardNumber
     * @param expirationMonth
     * @param expirationYear
     * @param cvc
     * @param cardholderName
     * @param address
     * @return
     */
    private CreateTokenCard createCard(String cardNumber, String expirationMonth, String expirationYear, String cvc, String cardholderName, Address address) {
        @Nullable CreateTokenCard card = new CreateTokenCard(
                cardNumber.replace(" ", ""),
                expirationMonth,
                (expirationYear.length() == 4) ? expirationYear.substring(2) : expirationYear,
                cvc,
                cardholderName,
                null);
        return card;
    }

    /**
     * Gets all available cards for the passed Customer.
     * @param customer
     * @param sessionDelegate
     */
    public void listAllCards(@NonNull String customer,@NonNull SessionDelegate sessionDelegate){

       GoSellAPI.getInstance().listAllCards(customer, new APIRequestCallback<ListCardsResponse>() {
           @Override
           public void onSuccess(int responseCode, ListCardsResponse serializedResponse) {
               if(serializedResponse!=null) {
                 sessionDelegate.savedCardsList(new CardsList(responseCode,serializedResponse.getObject(),serializedResponse.isHas_more(),serializedResponse.getCards()));
               }else
                   sessionDelegate.backendUnknownError("list All cards response is null,  with response code : "+ responseCode);
           }

           @Override
           public void onFailure(GoSellError errorDetails) {
              if(errorDetails!=null) Log.d("listAllCards API "," listAllCards errorDetails :  " + errorDetails.getErrorBody());
               sessionDelegate.sdkError(errorDetails);
           }
       }
       );
    }



}
