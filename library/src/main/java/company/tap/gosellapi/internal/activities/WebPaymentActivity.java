package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;

public class WebPaymentActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);

        createCharge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void getWebPaymentLink() {

        Redirect redirect = new Redirect("", "");

        Source source = new Source("");

    }

    private void createCharge() {
        HashMap<String, String> chargeMetadata = new HashMap<>();
        chargeMetadata.put("Order Number", "ORD-1001");

        Source source = new Source("src_kw.knet");

        GoSellAPI.getInstance().createCharge(
                new CreateChargeRequest
                        .Builder(10, "KWD", new Redirect("http://return.com/returnurl", "http://return.com/posturl"))
                        .source(source)
                        .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d("Web Payment", "onSuccess createCharge: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d("Web Payment", "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }
}
