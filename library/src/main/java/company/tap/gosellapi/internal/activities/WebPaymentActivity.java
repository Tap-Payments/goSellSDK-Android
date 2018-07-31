package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;

public class WebPaymentActivity extends BaseActionBarActivity {
    private final PaymentOptionsResponse paymentOptionsResponse = GlobalDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse();;
    private PaymentOption webPaymentOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_web_payment);

        webPaymentOption = new PaymentOption();
        for(PaymentOption option : paymentOptionsResponse.getPayment_options()) {

            if (option.getPaymentType().equals(PaymentType.WEB)) {
                webPaymentOption = option;
                break;
            }
        }

        setTitle(webPaymentOption.getName());
        setImage(webPaymentOption.getImage());

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

        PaymentInfo paymentInfo = GlobalDataManager.getInstance().getPaymentInfo();

        // Configure request body
        Source source = new Source(webPaymentOption.getSourceId());

        String customerIdentifier = paymentInfo.getCustomerIdentifier();
        CustomerInfo customerInfo = new CustomerInfo(customerIdentifier);

//        Redirect redirect = new Redirect("gosellsdk://return_url");

        Order order = new Order(paymentOptionsResponse.getOrderID());

        CreateChargeRequest request = new CreateChargeRequest
                .Builder(paymentInfo.getTotal_amount(), paymentInfo.getCurrencyCode(), 20, false)
                .order(order)
                .customer(customerInfo)
                .source(source)
                .build();

        // Configure request callbacks
        APIRequestCallback<Charge> requestCallback = new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
                updateWebView();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
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

//            if(url.equals(returnURL)) {
//                retrieveCharge();
//            }
        }
    }

    private void retrieveCharge() {

//        GoSellAPI.getInstance().retrieveCharge(chargeID, new APIRequestCallback<Charge>() {
//            @Override
//            public void onSuccess(int responseCode, Charge serializedResponse) {
//
//                String message = serializedResponse.getRedirect().getStatus();
//                PaymentResultToastManager.getInstance().showPaymentResult(getApplicationContext(), message);
//
//                setResult(RESULT_OK);
//                finish();
//            }
//
//            @Override
//            public void onFailure(GoSellError errorDetails) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
