package company.tap.gosellapi.api.facade;

import company.tap.gosellapi.api.model.BIN;
import company.tap.gosellapi.api.model.Card;
import company.tap.gosellapi.api.model.Charge;
import company.tap.gosellapi.api.model.Customer;
import company.tap.gosellapi.api.model.Source;
import company.tap.gosellapi.api.model.Token;
import company.tap.gosellapi.api.requests.CaptureChargeRequest;
import company.tap.gosellapi.api.requests.CardRequest;
import company.tap.gosellapi.api.requests.CreateCardRequest;
import company.tap.gosellapi.api.requests.CreateChargeRequest;
import company.tap.gosellapi.api.requests.CreateTokenRequest;
import company.tap.gosellapi.api.requests.CustomerRequest;
import company.tap.gosellapi.api.requests.UpdateChargeRequest;
import company.tap.gosellapi.api.responses.CardResponse;
import company.tap.gosellapi.api.responses.GeneralDeleteResponse;
import company.tap.gosellapi.api.responses.RetrieveCardsResponse;

/**
 * Facade class to use Android SDK
 * <br>
 * This is a singleton class, use it by {@link #getInstance(String authToken)} method.
 */
public final class GoSellAPI {
    private APIService apiHelper;

    private GoSellAPI() {
        apiHelper = RetrofitHelper.getApiHelper();
    }

    private static class SingletonHolder {
        private static final GoSellAPI INSTANCE = new GoSellAPI();
    }

    /**
     * Use this method to get instance of {@link GoSellAPI}
     * @param authToken An authorization token from your dashboard
     * @return {@link GoSellAPI} instance to perform requests
     */
    public static GoSellAPI getInstance(String authToken) {
        RetrofitHelper.setAuthenticationKey(authToken);
        return SingletonHolder.INSTANCE;
    }

    /**
     * Creates {@link Token} with {@link CardRequest} source
     * @param card {@link CardRequest}, modified representation of {@link CardResponse} model. Use {@link CardRequest.Builder} to build this card instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Token} model
     */
    public void createToken(final CardRequest card, final APIRequestCallback<Token> requestCallback) {
        apiHelper.createToken(new CreateTokenRequest
                .Builder(card)
                .build())
            .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Retrieves {@link Token} by id
     * <br>
     * @param tokenId Id field from {@link Token#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Token} model
     */
    public void retrieveToken(final String tokenId, final APIRequestCallback<Token> requestCallback) {
        apiHelper.retrieveToken(tokenId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Creates {@link Customer} with {@link CustomerRequest}
     * <br>
     * @param customerRequest {@link CustomerRequest} instance. Use {@link CustomerRequest.Builder} to obtain this instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Customer} model
     */
    public void createCustomer(final CustomerRequest customerRequest, final APIRequestCallback<Customer> requestCallback) {
        apiHelper.createCustomer(customerRequest)
                .enqueue(new BaseCallback<>(requestCallback));
    }


    /**
     * Retrieves {@link Customer} by id
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Customer} model
     */
    public void retrieveCustomer(final String customerId, final APIRequestCallback<Customer> requestCallback) {
        apiHelper.retrieveCustomer(customerId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Updates {@link Customer} by id with {@link CustomerRequest}
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param customerRequest {@link CustomerRequest} instance. Use {@link CustomerRequest.Builder} to obtain this instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Customer} model
     */
    public void updateCustomer(final String customerId, final CustomerRequest customerRequest, final APIRequestCallback<Customer> requestCallback) {
        apiHelper.updateCustomer(customerId, customerRequest)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Deletes {@link Customer} by id
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link GeneralDeleteResponse} model
     */
    public void deleteCustomer(final String customerId, final APIRequestCallback<GeneralDeleteResponse> requestCallback) {
        apiHelper.deleteCustomer(customerId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Creates {@link Card} for customer id with {@link Source}
     * <br>
     * @param customerId id field from {@link Customer#getId()} method
     * @param source source identifier
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Card} model
     */
    public void createCard(final String customerId, final String source, final APIRequestCallback<Card> requestCallback) {
        apiHelper.createCard(customerId, new CreateCardRequest(source))
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Retrieves specific {@link Card} by customer id and card id
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param cardId Id field from {@link Card#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Card} model
     */
    public void retrieveCard(final String customerId, final String cardId, final APIRequestCallback<Card> requestCallback){
        apiHelper.retrieveCard(customerId, cardId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Retrieves all {@link Card}s by customer id
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link RetrieveCardsResponse} model
     */
    public void retrieveAllCards(final String customerId, final APIRequestCallback<RetrieveCardsResponse> requestCallback){
        apiHelper.retrieveAllCards(customerId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Deletes {@link Card} for customer id and card id
     * <br>
     * @param customerId Id field from {@link Customer#getId()} method
     * @param cardId Id field from {@link Card#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link GeneralDeleteResponse} model
     */
    public void deleteCard(final String customerId, final String cardId, final APIRequestCallback<GeneralDeleteResponse> requestCallback) {
        apiHelper.deleteCard(customerId, cardId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Creates {@link Charge} from {@link CreateChargeRequest}
     * @param createChargeRequest {@link CreateChargeRequest} instance. Use {@link CreateChargeRequest.Builder} to obtain this instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Charge} model
     */
    public void createCharge(final CreateChargeRequest createChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        apiHelper.createCharge(createChargeRequest)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Retrieves {@link Charge} by id
     * <br>
     * Method is not using in Android SDK at the moment
     * @param chargeId Id field from {@link Charge#getId()} method
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Charge} model
     */
    public void retrieveCharge(final String chargeId, final APIRequestCallback<Charge> requestCallback) {
        apiHelper.retrieveCharge(chargeId)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Updates {@link Charge} by id with {@link UpdateChargeRequest}
     * <br>
     * Method is not using in Android SDK at the moment
     * @param chargeId Id field from {@link Charge#getId()} method
     * @param updateChargeRequest {@link UpdateChargeRequest} instance. Use {@link UpdateChargeRequest.Builder} to obtain this instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Charge} model
     */
    public void updateCharge(final String chargeId, final UpdateChargeRequest updateChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        apiHelper.updateCharge(chargeId, updateChargeRequest)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Captures {@link Charge} by id with {@link CaptureChargeRequest}
     * <br>
     * @param chargeId Id field from {@link Charge#getId()} method
     * @param captureChargeRequest {@link CaptureChargeRequest} instance. Use {@link CaptureChargeRequest.Builder} to obtain this instance
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link Charge} model
     */
    public void captureCharge(final String chargeId, final CaptureChargeRequest captureChargeRequest, final APIRequestCallback<Charge> requestCallback) {
        apiHelper.captureCharge(chargeId, captureChargeRequest)
                .enqueue(new BaseCallback<>(requestCallback));
    }

    /**
     * Get card information from BIN number
     * <br>
     * @param bin_number first 6 digits of a card
     * @param requestCallback {@link APIRequestCallback} parametrized with {@link BIN} model
     */
    public void getBINNumberDetails(final String bin_number, final APIRequestCallback<BIN> requestCallback) {
        apiHelper.getBINNumberDetails(bin_number)
                .enqueue(new BaseCallback<>(requestCallback));
    }
}
