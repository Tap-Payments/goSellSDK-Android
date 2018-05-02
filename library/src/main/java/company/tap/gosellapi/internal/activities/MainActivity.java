package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;
import company.tap.gosellapi.internal.fragments.MainScreenFragment;
import company.tap.gosellapi.internal.fragments.OTPScreenFragment;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.MainRecyclerViewAdapterListener {
    private static final int SCAN_REQUEST_CODE = 123;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_main);

        fragmentManager = getSupportFragmentManager();

        initViews();
    }

    private void initViews() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.mainScreenFragmentContainer, new MainScreenFragment())
                .commit();

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

        fragmentManager
                .beginTransaction()
                .replace(R.id.mainScreenFragmentContainer, new OTPScreenFragment())
                .commit();
    }

    public void scanCard() {
        Intent scanCard = new Intent(this, CardIOActivity.class);
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanCard.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);
        scanCard.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true);
        scanCard.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
        scanCard.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
        scanCard.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);

        startActivityForResult(scanCard, SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = scanResult.cardNumber;
//                etCardNumber.setText(resultDisplayStr);
//
//                setTypeFromNumber(resultDisplayStr);
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    String month;
                    if (scanResult.expiryMonth < 10) {
                        month = "0" + scanResult.expiryMonth;
                    } else {
                        month = scanResult.expiryMonth + "";
                    }
                    int year = scanResult.expiryYear - 2000;
//                    etDate.setText(month + "/" + year);
                }

                if (scanResult.cvv != null) {
//                    etCVV.setText(scanResult.cvv);
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
//                TapDialog.createToast(this, L.scan_was_canceled.toString(), Toast.LENGTH_LONG);
            }
        }
    }
}


