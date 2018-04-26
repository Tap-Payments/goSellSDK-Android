package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.fragments.MainScreenFragment;

public class MainActivity extends AppCompatActivity implements MainScreenFragment.MainScreenFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_main);

        initViews();
    }

    private void initViews() {

        // Configure Close button
        ImageButton closeButton = findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void cardScannerButtonClicked() {
        Log.e("MAIN ACTIVITY", "CARD SCANNER BUTTON CLICKED");
    }

    @Override
    public void saveCardSwitchCheckedChanged() {
        Log.e("MAIN ACTIVITY", "SAVE CARD SWITCH CHECKED CHANGED");
    }

    @Override
    public void paymentSystemViewHolderClicked() {
        Log.e("MAIN ACTIVITY", "PAYMENT SYSTEM VIEW HOLDER CLICKED");
    }

    @Override
    public void recentPaymentItemClicked() {
        Log.e("MAIN ACTIVITY", "RECENT PAYMENT ITEM CLICKED");
    }
}


