package company.tap.gosellapi.internal.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import company.tap.gosellapi.R;

public class WebPaymentActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String url = extras.getString("URL");
            WebView webView = findViewById(R.id.webPaymentWebView);
            webView.setWebViewClient(new WebPaymentWebViewClient());
            webView.loadUrl(url);
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

            Log.e("WEBVIEW", "PAGE STARTED URL " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Log.e("WEBVIEW", "PAGE FINISHED URL " + url);
        }
    }
}
