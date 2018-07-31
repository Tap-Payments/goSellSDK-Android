package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.PaymentResultToastManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;

public class WebPaymentActivity extends BaseActionBarActivity implements PaymentOptionsDataManager.PaymentOptionsDataListener {
    private String chargeID;
    private String returnURL;

    public static final String INTENT_EXTRA_KEY_NAME = "name";
    public static final String INTENT_EXTRA_KEY_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_web_payment);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String title = extras.getString(INTENT_EXTRA_KEY_NAME);
            setTitle(title);

            String source = extras.getString(INTENT_EXTRA_KEY_IMAGE);
            setImage(source);
        }

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.post(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private void getData() {

        LoadingScreenManager.getInstance().showLoadingScreen(this);

        PaymentOptionsDataManager dataSource = GlobalDataManager.getInstance().getPaymentOptionsDataManager(this);

        // Configure request body
        Source source = new Source("src_kw.knet");
        PhoneNumber phoneNumber = new PhoneNumber("965", "77777777");
        CustomerInfo customerInfo = GlobalDataManager.getInstance().getPaymentInfo().getCustomer();
        Redirect redirect = new Redirect("gosellsdk://return_url");

        Order order = new Order(dataSource.getPaymentOptionsResponse().getOrderID());

        CreateChargeRequest request = new CreateChargeRequest
                .Builder(100, "KWD", 20, false)
                .customer(customerInfo)
                .order(order)
                .redirect(redirect)
                .source(source)
                .build();

        // Configure request callbacks
        APIRequestCallback<Charge> requestCallback = new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
                Log.e("TAG", "SUCCESS");
                updateWebView();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
                Log.e("TAG", "FAILURE");
                updateWebView();
            }
        };

        // Create charge
        GoSellAPI.getInstance().createCharge(request, requestCallback);
    }

    private void updateWebView() {
        WebView webView = findViewById(R.id.webPaymentWebView);
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private class WebPaymentWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if(url.equals(returnURL)) {
                retrieveCharge();
            }
        }
    }

    private void retrieveCharge() {

        GoSellAPI.getInstance().retrieveCharge(chargeID, new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {

                String message = serializedResponse.getRedirect().getStatus();
                PaymentResultToastManager.getInstance().showPaymentResult(getApplicationContext(), message);

                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void startCurrencySelection(ArrayList<AmountedCurrency> currencies, AmountedCurrency selectedCurrency) {

    }

    @Override
    public void startOTP() {

    }

    @Override
    public void startWebPayment() {

    }

    @Override
    public void startScanCard() {

    }

    @Override
    public void cardDetailsFilled(boolean isFilled, CardRawData cardRawData) {

    }

    @Override
    public void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition) {

    }

    @Override
    public void addressOnCardClicked() {

    }

    @Override
    public void cardExpirationDateClicked() {

    }

    @Override
    public void binNumberEntered(String binNumber) {

    }
}
