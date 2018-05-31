package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.view.Menu;

import company.tap.gosellapi.R;

public class WebPaymentActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
