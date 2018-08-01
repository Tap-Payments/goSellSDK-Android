package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.nio.file.WatchEvent;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOptionsRequest;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.PaymentResultToastManager;

public class WebPaymentActivity extends BaseActionBarActivity {
    private final PaymentOptionsResponse paymentOptionsResponse = GlobalDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse();;
    private PaymentOption webPaymentOption;

    private WebView webView;

    String returnURL;
    private String chargeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_web_payment);

        webView = findViewById(R.id.webPaymentWebView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

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

        Source source = new Source(webPaymentOption.getSourceId());

        APIRequestCallback<Charge> requestCallback = new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {
                LoadingScreenManager.getInstance().closeLoadingScreen();

                returnURL = serializedResponse.getRedirect().getReturnURL();
                chargeID = serializedResponse.getId();

                updateWebView();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
                updateWebView();
            }
        };

        GlobalDataManager.getInstance().callChargeAPI(source, webPaymentOption,null, requestCallback);
    }

    private void updateWebView() {
        WebView webView = findViewById(R.id.webPaymentWebView);
        webView.setWebViewClient(new WebPaymentWebViewClient());
        webView.setVisibility(View.VISIBLE);

        if(returnURL == null) return;
        webView.loadUrl(returnURL);
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
//                retrieveCharge();
            }
        }
    }

    private void retrieveCharge() {

        if(chargeID == null) return;

        APIRequestCallback<Charge> callback = new APIRequestCallback<Charge>() {
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
        };

        GoSellAPI.getInstance().retrieveCharge(chargeID, callback);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
