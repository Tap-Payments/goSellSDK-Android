package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.interfaces.ChargeObserver;

public class WebPaymentActivity extends BaseActionBarActivity implements ChargeObserver {
    private final PaymentOptionsResponse paymentOptionsResponse = GlobalDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse();;
    private PaymentOption webPaymentOption;

    private WebView webView;

    String paymentURL;
    String returnURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_web_payment);

        webView = findViewById(R.id.webPaymentWebView);
        webView.setWebViewClient(new WebPaymentWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webPaymentOption = new PaymentOption();
        for(PaymentOption option : paymentOptionsResponse.getPaymentOptions()) {

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
        GlobalDataManager.getInstance().initiatePayment(this, webPaymentOption);
    }

    private void updateWebView() {
        WebView webView = findViewById(R.id.webPaymentWebView);
        webView.setVisibility(View.VISIBLE);

        if(paymentURL == null) return;

        webView.loadUrl(paymentURL);

    }

    private void finishActivityWithResultCodeOK() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private class WebPaymentWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LoadingScreenManager.getInstance().closeLoadingScreen();

            if(url.equals(returnURL)) {
                GlobalDataManager.getInstance().retrieveChargeAPI();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            view.stopLoading();
            view.loadUrl("about:blank");
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void otpScreenNeedToShown() {

        Log.e("OkHttp", "OTP NEED TO SHOWN");
    }

    @Override
    public void webScreenNeedToShown() {
        Log.e("OkHttp", "WEB NEED TO SHOWN");
    }

    @Override
    public void responseSucceed() {
        Log.e("OkHttp","RESPONSE SUCCEED");
    }

    @Override
    public void responseFailed() {
        Log.e("OkHttp", "RESPONSE FAILED");
    }

    @Override
    public void dataAcquired(Charge response) {
        paymentURL = response.getRedirect().getUrl();
        returnURL = response.getRedirect().getReturnURL();
        updateWebView();
    }
}
