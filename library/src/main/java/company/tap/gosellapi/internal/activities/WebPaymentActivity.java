package company.tap.gosellapi.internal.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.data_managers.PaymentResultToastManager;

public class WebPaymentActivity extends BaseActionBarActivity {
    private String chargeID;
    private String pageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_web_payment);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            pageUrl = extras.getString("URL");
            WebView webView = findViewById(R.id.webPaymentWebView);
            webView.setWebViewClient(new WebPaymentWebViewClient());
            webView.loadUrl(pageUrl);

            chargeID = extras.getString("id");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private class WebPaymentWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Log.e("OkHttp", "PAGE FINISHED URL " + url);
            Log.e("OkHttp", "STORED URL " + pageUrl);
//            if(url.equals(pageUrl)) {
                retrieveCharge();
//            }
        }
    }

    private void retrieveCharge() {
        Log.e("OkHttp", "RETREIVE CHARGE CALLED");

        GoSellAPI.getInstance().retrieveCharge(chargeID, new APIRequestCallback<Charge>() {
            @Override
            public void onSuccess(int responseCode, Charge serializedResponse) {

                Log.e("OkHttp", "STATUS " + serializedResponse.getRedirect().getStatus());

                String message = serializedResponse.getRedirect().getStatus();
                PaymentResultToastManager.getInstance().showPaymentResult(getLayoutInflater(), getApplicationContext(), message);

                finish();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {

            }
        });
    }

}
