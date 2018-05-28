package company.tap.gosellapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import company.tap.gosellapi.api.facade.APIRequestCallback;
import company.tap.gosellapi.api.facade.GoSellAPI;
import company.tap.gosellapi.api.facade.GoSellError;
import company.tap.gosellapi.api.model.BIN;
import company.tap.gosellapi.api.model.Card;
import company.tap.gosellapi.api.model.Charge;
import company.tap.gosellapi.api.model.Customer;
import company.tap.gosellapi.api.model.Redirect;
import company.tap.gosellapi.api.model.Source;
import company.tap.gosellapi.api.model.Token;
import company.tap.gosellapi.api.requests.CaptureChargeRequest;
import company.tap.gosellapi.api.requests.CardRequest;
import company.tap.gosellapi.api.requests.CreateChargeRequest;
import company.tap.gosellapi.api.requests.CustomerRequest;
import company.tap.gosellapi.api.requests.UpdateChargeRequest;
import company.tap.gosellapi.api.responses.GeneralDeleteResponse;
import company.tap.gosellapi.api.responses.RetrieveCardsResponse;

public class TestActivity extends Activity {
    private static final String TAG = "TestAct";
    private static final String AUTH_TOKEN = "sk_test_LVmRFM2Y0gifacSPnI4GqbZh"; //"your authorization token goes here";
    public static final String ENCRYPTION_KEY = "-----BEGIN PUBLIC KEY-----\nMIIBIDANBgkqhkiG9w0BAQEFAAOCAQ0AMIIBCAKCAQEAndkqmyMcuBdK81jsddnv\nMxdDMxl2qccY+15CGJGiFub1uYl5Z+Lm6Bb50EFtaU10D8QziwdWWACtVu3llHvu\nlcq+1Vzo7fr8SxAWlKI4Xlndmuat/1qG29y+zXOyAcezHe6X793YGYU6k3pC3LVb\nAOR4gDFOKZBTQAwdmsChcVh2ibOA5Bt7l1lxVKfR2GZun72HNYYs+2Jvwz7SeQZP\nTkbDMDfrWRJTurCk66WSU/Zh0dlF/5Nkzfnv/xXd8xIOo9J3zddpIbpEDMGa8wU7\nVy54szyBbbPH5+OMGj1G3Okb2UNy3JuM9CVfHHO2J/dKjud4MsdhtJ6NWLB/Cq49\n8QIBEQ==\n-----END PUBLIC KEY-----\n"; //"your encryption key goes here";

    private Token token;
    private Charge charge;
    private Customer customer;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        createToken();
        createCustomer();
//        getBINNumberDetails();
    }

    private void createToken() {
        GoSellAPI.getInstance(AUTH_TOKEN).createToken(
                new CardRequest
                        .Builder("4111111111111111", "10", "20", "123", ENCRYPTION_KEY)
                        .address_city("Some city")
                        .address_line1("First line")
                        .address_line2("Second line")
                        .address_state("Royal State")
                        .address_zip("007")
                        .address_country("Some country")
                        .name("Testing VISA card")
                        .build(),
                new APIRequestCallback<Token>() {
                    @Override
                    public void onSuccess(int responseCode, Token serializedResponse) {
                        Log.d(TAG, "onSuccess createToken serializedResponse:" + serializedResponse);
                        token = serializedResponse;
//                        retrieveToken();
                        createCharge();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure createToken, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                });
    }

    private void retrieveToken() {
        GoSellAPI.getInstance(AUTH_TOKEN).retrieveToken(token.getId(), new APIRequestCallback<Token>() {
            @Override
            public void onSuccess(int responseCode, Token serializedResponse) {
                Log.d(TAG, "onSuccess retrieveToken: serializedResponse:" + serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                Log.d(TAG, "onFailure retrieveToken, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
            }
        });
    }

    private void createCharge() {
        HashMap<String, String> chargeMetadata = new HashMap<>();
        chargeMetadata.put("Order Number", "ORD-1001");

        GoSellAPI.getInstance(AUTH_TOKEN).createCharge(
                new CreateChargeRequest
                        .Builder(10, "KWD", new Redirect("http://return.com/returnurl", "http://return.com/posturl"))
                        .source(new Source(token.getId()))
                        .statement_descriptor("Test Txn 001")
                        .description("Test Transaction")
                        .metadata(chargeMetadata)
                        .receipt_sms("96598989898")
                        .receipt_email("test@test.com")
                        .capture(true)
                        .threeds(true)
                        .reference("123456")
                        .first_name("John")
                        .last_name("Doe")
                        .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d(TAG, "onSuccess createCharge: serializedResponse:" + serializedResponse);
                        charge = serializedResponse;
                        captureCharge();
                        retrieveCharge();
                        updateCharge();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void createCustomer() {
        HashMap<String, String> customerMetadata = new HashMap<>();
        customerMetadata.put("Gender", "Male");
        customerMetadata.put("Nationality", "Kuwaiti");
        GoSellAPI.getInstance(AUTH_TOKEN).createCustomer(
                new CustomerRequest
                        .Builder("Test User", "0096598989898", "test@test.com")
                        .description("test description")
                        .metadata(customerMetadata)
                        .currency("KWT")
                        .build(),
                new APIRequestCallback<Customer>() {
                    @Override
                    public void onSuccess(int responseCode, Customer serializedResponse) {
                        Log.d(TAG, "onSuccess createCustomer: ");
                        customer = serializedResponse;
//                        getCustomer();
                        createCard();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure createCustomer, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void getCustomer() {
        GoSellAPI.getInstance(AUTH_TOKEN).retrieveCustomer(customer.getId(), new APIRequestCallback<Customer>() {
            @Override
            public void onSuccess(int responseCode, Customer serializedResponse) {
                Log.d(TAG, "onSuccess retrieveCustomer: ");
                updateCustomer();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                Log.d(TAG, "onFailure retrieveCustomer, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
            }
        });
    }

    private void updateCustomer() {
        HashMap<String, String> customerMetadata = new HashMap<>();
        customerMetadata.put("Gender", "Male");
        customerMetadata.put("Nationality", "Kuwaiti");
        GoSellAPI.getInstance(AUTH_TOKEN).updateCustomer(customer.getId(),
                new CustomerRequest
                        .Builder("Test User NEW", "0096598989898", "test@test.com")
                        .description("test description NEW")
                        .metadata(customerMetadata)
                        .currency("USD")
                        .build(),
                new APIRequestCallback<Customer>() {
                    @Override
                    public void onSuccess(int responseCode, Customer serializedResponse) {
                        Log.d(TAG, "onSuccess updateCustomer: ");
                        deleteCustomer();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure updateCustomer, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                });
    }

    private void deleteCustomer() {
        GoSellAPI.getInstance(AUTH_TOKEN).deleteCustomer(customer.getId(),
                new APIRequestCallback<GeneralDeleteResponse>() {
                    @Override
                    public void onSuccess(int responseCode, GeneralDeleteResponse serializedResponse) {
                        Log.d(TAG, "onSuccess deleteCustomer: ");
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure deleteCustomer, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                });
    }

    private void createCard() {
        GoSellAPI.getInstance(AUTH_TOKEN).createCard(
                customer.getId(),
                new Source("card", "12", "20", "4242424242424242", "123"),
                new APIRequestCallback<Card>() {
                    @Override
                    public void onSuccess(int responseCode, Card serializedResponse) {
                        Log.d(TAG, "onSuccess createCard: ");
                        card = serializedResponse;
                        retrieveCard();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure createCard, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void retrieveCard() {
        GoSellAPI.getInstance(AUTH_TOKEN).retrieveCard(
                customer.getId(),
                card.getId(),
                new APIRequestCallback<Card>() {
                    @Override
                    public void onSuccess(int responseCode, Card serializedResponse) {
                        Log.d(TAG, "onSuccess retrieveCard: ");
                        card = serializedResponse;
                        retrieveAllCards();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure retrieveCard, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void retrieveAllCards() {
        GoSellAPI.getInstance(AUTH_TOKEN).retrieveAllCards(
                customer.getId(),
                new APIRequestCallback<RetrieveCardsResponse>() {
                    @Override
                    public void onSuccess(int responseCode, RetrieveCardsResponse serializedResponse) {
                        Log.d(TAG, "onSuccess retrieveAllCards: ");
                        deleteCardForCustomer();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure retrieveAllCards, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void deleteCardForCustomer() {
        GoSellAPI.getInstance(AUTH_TOKEN).deleteCard(
                customer.getId(),
                card.getId(),
                new APIRequestCallback<GeneralDeleteResponse>() {
                    @Override
                    public void onSuccess(int responseCode, GeneralDeleteResponse serializedResponse) {
                        Log.d(TAG, "onSuccess deleteCard: ");
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure deleteCard, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void retrieveCharge() {
        GoSellAPI.getInstance(AUTH_TOKEN).retrieveCharge(
                charge.getId(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d(TAG, "onSuccess retrieveCharge: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure retrieveCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void updateCharge() {
        GoSellAPI.getInstance(AUTH_TOKEN).updateCharge(
                charge.getId(),
                new UpdateChargeRequest.Builder()
                    .description("New description")
                    .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d(TAG, "onSuccess updateCharge: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure updateCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void captureCharge() {
        GoSellAPI.getInstance(AUTH_TOKEN).captureCharge(
                charge.getId(),
                new CaptureChargeRequest
                    .Builder()
                    .amount(10)
                    .statement_descriptor("Test Txn 001")
                    .receipt_sms("96598989898")
                    .receipt_email("test@test.com")
                    .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d(TAG, "onSuccess captureCharge: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure captureCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                });
    }

    private void getBINNumberDetails() {
        GoSellAPI.getInstance(AUTH_TOKEN).getBINNumberDetails(
                "516874",
                new APIRequestCallback<BIN>() {
                    @Override
                    public void onSuccess(int responseCode, BIN serializedResponse) {
                        Log.d(TAG, "onSuccess getBINNumberDetails: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure getBINNumberDetails, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                });
    }
}
