//package company.tap.gosellapi;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.HashMap;
//
//import company.tap.gosellapi.api.facade.APIRequestCallback;
//import company.tap.gosellapi.api.facade.GoSellAPI;
//import company.tap.gosellapi.api.model.Charge;
//import company.tap.gosellapi.api.model.Redirect;
//import company.tap.gosellapi.api.model.Source;
//import company.tap.gosellapi.api.model.Token;
//import company.tap.gosellapi.api.requests.CardRequest;
//import company.tap.gosellapi.api.requests.CreateChargeRequest;
//import company.tap.gosellapi.api.requests.UpdateChargeRequest;
//
//public class MainActivity extends Activity {
//    private static final String TAG = "MainAct";
//    private static final String SECRET_KEY = "sk_test_LVmRFM2Y0gifacSPnI4GqbZh";
//    public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\nMIIBIDANBgkqhkiG9w0BAQEFAAOCAQ0AMIIBCAKCAQEAndkqmyMcuBdK81jsddnv\nMxdDMxl2qccY+15CGJGiFub1uYl5Z+Lm6Bb50EFtaU10D8QziwdWWACtVu3llHvu\nlcq+1Vzo7fr8SxAWlKI4Xlndmuat/1qG29y+zXOyAcezHe6X793YGYU6k3pC3LVb\nAOR4gDFOKZBTQAwdmsChcVh2ibOA5Bt7l1lxVKfR2GZun72HNYYs+2Jvwz7SeQZP\nTkbDMDfrWRJTurCk66WSU/Zh0dlF/5Nkzfnv/xXd8xIOo9J3zddpIbpEDMGa8wU7\nVy54szyBbbPH5+OMGj1G3Okb2UNy3JuM9CVfHHO2J/dKjud4MsdhtJ6NWLB/Cq49\n8QIBEQ==\n-----END PUBLIC KEY-----\n";
//
//    private Token token;
//    private Charge charge;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        createToken();
//    }
//
//    private void createToken() {
//        GoSellAPI.getInstance(SECRET_KEY).createToken(
//                new CardRequest
//                        .Builder("4111111111111111", "10", "20", "123", PUBLIC_KEY)
//                        .address_city("Some city")
//                        .address_line1("First line")
//                        .address_line2("Second line")
//                        .address_state("Royal State")
//                        .address_zip("007")
//                        .address_country("Some country")
//                        .name("Testing VISA card")
//                        .build(),
//                new APIRequestCallback<Token>() {
//                    @Override
//                    public void onSuccess(int responseCode, Token serializedResponse) {
//                        synchronized (this) {
//                            Log.d(TAG, "onSuccess createToken serializedResponse:" + serializedResponse);
//                        }
//                        token = serializedResponse;
//                        retrieveToken();
//                        createCharge();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        synchronized (this) {
//                            Log.d(TAG, "onFailure createToken, t: " + t);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int responseCode, String errorBody) {
//                        synchronized (this) {
//                            Log.d(TAG, "onFailure createToken, responseCode: " + responseCode + ", errorBody: " + errorBody);
//                        }
//                    }
//                });
//    }
//
//    private void retrieveToken() {
//        GoSellAPI.getInstance(SECRET_KEY).retrieveToken(token.getId(), new APIRequestCallback<Token>() {
//            @Override
//            public void onSuccess(int responseCode, Token serializedResponse) {
//                Log.d(TAG, "onSuccess retrieveToken: serializedResponse:" + serializedResponse);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.d(TAG, "onFailure retrieveToken, t: " + t);
//            }
//
//            @Override
//            public void onFailure(int responseCode, String errorBody) {
//                Log.d(TAG, "onFailure retrieveToken, responseCode: " + responseCode + ", errorBody: " + errorBody);
//            }
//        });
//    }
//
//    private void createCharge() {
//        HashMap<String, String> chargeMetadata = new HashMap<>();
//        chargeMetadata.put("Order Number", "ORD-1001");
//
//        GoSellAPI.getInstance(SECRET_KEY).createCharge(
//                new CreateChargeRequest
//                        .Builder(10, "KWD", new Redirect("http://return.com/returnurl", "http://return.com/posturl"))
//                        .source(new Source(token.getId()))
//                        .statement_descriptor("Test Txn 001")
//                        .description("Test Transaction")
//                        .metadata(chargeMetadata)
//                        .receipt_sms("96598989898")
//                        .receipt_email("test@test.com")
//                        .build(),
//                new APIRequestCallback<Charge>() {
//                    @Override
//                    public void onSuccess(int responseCode, Charge serializedResponse) {
//                        Log.d(TAG, "onSuccess createCharge: serializedResponse:" + serializedResponse);
//                        charge = serializedResponse;
//                        retrieveCharge();
//                        updateCharge();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.d(TAG, "onFailure createCharge, t: " + t);
//                    }
//
//                    @Override
//                    public void onFailure(int responseCode, String errorBody) {
//                        Log.d(TAG, "onFailure createCharge, responseCode: " + responseCode + ", errorBody: " + errorBody);
//                    }
//                }
//        );
//    }
//
//    private void retrieveCharge() {
//        GoSellAPI.getInstance(SECRET_KEY).retrieveCharge(
//                charge.getId(),
//                new APIRequestCallback<Charge>() {
//                    @Override
//                    public void onSuccess(int responseCode, Charge serializedResponse) {
//                        Log.d(TAG, "onSuccess retrieveCharge: serializedResponse:" + serializedResponse);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.d(TAG, "onFailure retrieveCharge, t: " + t);
//                    }
//
//                    @Override
//                    public void onFailure(int responseCode, String errorBody) {
//                        Log.d(TAG, "onFailure retrieveCharge, responseCode: " + responseCode + ", errorBody: " + errorBody);
//                    }
//                }
//        );
//    }
//
//    private void updateCharge() {
//        GoSellAPI.getInstance(SECRET_KEY).updateCharge(
//                charge.getId(),
//                new UpdateChargeRequest.Builder()
//                    .description("New description")
//                    .build(),
//                new APIRequestCallback<Charge>() {
//                    @Override
//                    public void onSuccess(int responseCode, Charge serializedResponse) {
//                        Log.d(TAG, "onSuccess updateCharge: serializedResponse:" + serializedResponse);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.d(TAG, "onFailure updateCharge, t: " + t);
//                    }
//
//                    @Override
//                    public void onFailure(int responseCode, String errorBody) {
//                        Log.d(TAG, "onFailure updateCharge, responseCode: " + responseCode + ", errorBody: " + errorBody);
//                    }
//                }
//        );
//    }
//}
