package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.fragments.GoSellOTPScreenFragment;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import jp.wasabeef.blurry.Blurry;

public class GoSellPaymentActivity
        extends AppCompatActivity
        implements PaymentOptionsDataManager.PaymentOptionsDataListener {
    private static final int SCAN_REQUEST_CODE = 123;
    private static final int CURRENCIES_REQUEST_CODE = 124;

    private PaymentOptionsDataManager dataSource;
    private FragmentManager fragmentManager;
    private GoSellPaymentOptionsFragment paymentOptionsFragment;

    private ImageView businessIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, android.R.anim.fade_out);
        setContentView(R.layout.gosellapi_activity_main);

        fragmentManager = getSupportFragmentManager();
        dataSource = GlobalDataManager.getInstance().getPaymentOptionsDataManager(this);

        final FrameLayout fragmentContainer = findViewById(R.id.paymentActivityFragmentContainer);
        fragmentContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                dataSource.setAvailableHeight(fragmentContainer.getHeight());
                fragmentContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        initViews();
    }

    private void initViews() {
        paymentOptionsFragment = GoSellPaymentOptionsFragment.newInstance(dataSource);
        fragmentManager
                .beginTransaction()
                .replace(R.id.paymentActivityFragmentContainer, paymentOptionsFragment)
                .commit();

        //   Configure Close button
        ImageButton closeButton = findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        businessIcon = findViewById(R.id.businessIcon);
        String logoPath = GlobalDataManager.getInstance().getSDKSettings().getData().getMerchant().getLogo();
        Glide.with(this).load(logoPath).apply(RequestOptions.circleCropTransform()).into(businessIcon);

        String businessNameString = GlobalDataManager.getInstance().getSDKSettings().getData().getMerchant().getName();
        TextView businessName = findViewById(R.id.businessName);
        businessName.setText(businessNameString);
    }

    @Override
    public void startOTP() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.paymentActivityFragmentContainer, new GoSellOTPScreenFragment())
                .addToBackStack("")
                .commit();
    }

    @Override
    public void startCurrencySelection(ArrayList<AmountedCurrency> currencies, AmountedCurrency selectedCurrency) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        intent.putExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_DATA, currencies);
        intent.putExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY, selectedCurrency);

        startActivityForResult(intent, CURRENCIES_REQUEST_CODE);
    }

    @Override
    public void startWebPayment() {
        Intent intent = new Intent(this, WebPaymentActivity.class);
        startActivity(intent);
    }

    @Override
    public void startScanCard() {
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
    public void cardDetailsFilled(boolean isFilled, CardRawData cardRawData) {

    }

    @Override
    public void addressOnCardClicked() {
        Intent intent = new Intent(this, GoSellCardAddressActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition) {
        paymentOptionsFragment.scrollRecyclerToPosition(saveCardBlockPosition);
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
        } else if (requestCode == CURRENCIES_REQUEST_CODE) {
            AmountedCurrency userChoiceCurrency = (AmountedCurrency) data.getSerializableExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY);
            if (userChoiceCurrency != null) {
                dataSource.currencySelectedByUser(userChoiceCurrency);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
    }
}


