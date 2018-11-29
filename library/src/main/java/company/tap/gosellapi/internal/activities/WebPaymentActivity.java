package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.AuthenticationStatus;
import company.tap.gosellapi.internal.api.enums.ChargeStatus;
import company.tap.gosellapi.internal.api.models.Authenticate;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;

public class WebPaymentActivity extends BaseActionBarActivity implements IPaymentProcessListener {

    public final class IntentParameters {

        public static final String paymentOptionModel = "payment_option_model";
    }

    private WebPaymentViewModel viewModel;

    private PaymentOption getPaymentOption() {

        return viewModel.getPaymentOption();
    }

    private WebView webView;

    String paymentURL;
    String returnURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = (WebPaymentViewModel) ActivityDataExchanger.getInstance().getExtra(getIntent(), IntentParameters.paymentOptionModel);

        setContentView(R.layout.gosellapi_activity_web_payment);

        webView = findViewById(R.id.webPaymentWebView);
        webView.setWebViewClient(new WebPaymentWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        setTitle(getPaymentOption().getName());
        setImage(getPaymentOption().getImage());

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
        PaymentDataManager.getInstance().initiatePayment(viewModel, this);
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
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            PaymentDataManager.WebPaymentURLDecision decision = PaymentDataManager.getInstance().decisionForWebPaymentURL(url);
            boolean shouldOverride = !decision.shouldLoad();


            return shouldOverride;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            LoadingScreenManager.getInstance().closeLoadingScreen();
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
    public void didReceiveCharge(Charge charge) {

        obtainPaymentURLFromChargeOrAuthorize(charge);
    }

    @Override
    public void didReceiveAuthorize(Authorize authorize) {

        obtainPaymentURLFromChargeOrAuthorize(authorize);
    }

    @Override
    public void didReceiveError(GoSellError error) {

        closeLoadingScreen();
    }

    private void obtainPaymentURLFromChargeOrAuthorize(Charge chargeOrAuthorize) {

        if ( chargeOrAuthorize.getStatus() != ChargeStatus.INITIATED ) { return; }

        Authenticate authentication = chargeOrAuthorize.getAuthenticate();
        if ( authentication != null && authentication.getStatus() == AuthenticationStatus.INITIATED ) { return; }

        String url = chargeOrAuthorize.getTransaction().getUrl();
        if ( url != null ) {

            this.paymentURL = url;
            updateWebView();
        }
    }

    private void closeLoadingScreen() {

        LoadingScreenManager.getInstance().closeLoadingScreen();
    }
}
