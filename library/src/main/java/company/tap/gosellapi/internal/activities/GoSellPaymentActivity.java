package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.Order;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.models.Source;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.custom_views.TapDialog;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class GoSellPaymentActivity
        extends AppCompatActivity
        implements PaymentOptionsDataManager.PaymentOptionsDataListener {
    private static final int SCAN_REQUEST_CODE = 123;
    private static final int CURRENCIES_REQUEST_CODE = 124;
    private static final int WEB_PAYMENT_REQUEST_CODE = 125;

    private PaymentOptionsDataManager dataSource;
    private FragmentManager fragmentManager;

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
        GoSellPaymentOptionsFragment paymentOptionsFragment = GoSellPaymentOptionsFragment.newInstance(dataSource);
        fragmentManager
                .beginTransaction()
                .replace(R.id.paymentActivityFragmentContainer, paymentOptionsFragment)
                .commit();

        businessIcon = findViewById(R.id.businessIcon);
        String logoPath = GlobalDataManager.getInstance().getSDKSettings().getData().getMerchant().getLogo();
        Glide.with(this).load(logoPath).apply(RequestOptions.circleCropTransform()).into(businessIcon);

        String businessNameString = GlobalDataManager.getInstance().getSDKSettings().getData().getMerchant().getName();
        TextView businessName = findViewById(R.id.businessName);
        businessName.setText(businessNameString);

    }

    @Override
    public void startOTP() {
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

        PaymentOption webPaymentOption = new PaymentOption();
        for(PaymentOption option : dataSource.getPaymentOptionsResponse().getPayment_options()) {

            if (option.getPaymentType().equals(PaymentType.WEB)) {
                webPaymentOption = option;
                break;
            }
        }

        intent.putExtra(WebPaymentActivity.INTENT_EXTRA_KEY_IMAGE, webPaymentOption.getImage());
        intent.putExtra(WebPaymentActivity.INTENT_EXTRA_KEY_NAME, webPaymentOption.getName());

        startActivityForResult(intent, WEB_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void cardExpirationDateClicked() {

        TapDialog.expireDate(this, new TapDialog.TapDialogListener() {
            @Override
            public void expirationDateSelected(String month, String year) {
                dataSource.cardExpirationDateSelected(month, year);
            }
        });
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
        BINLookupResponse binLookupResponse = GlobalDataManager.getInstance().getBinLookupResponse();
        if(binLookupResponse == null) return;
        Intent intent = new Intent(this, GoSellCardAddressActivity.class);
        intent.putExtra(GoSellCardAddressActivity.INTENT_EXTRA_KEY_COUNTRY, binLookupResponse.getCountry());
        startActivity(intent);
    }

    @Override
    public void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition) {
//        paymentOptionsFragment.scrollRecyclerToPosition(saveCardBlockPosition);
    }

    @Override
    public void binNumberEntered(String binNumber) {

        GoSellAPI.getInstance().retrieveBINLookupBINLookup(binNumber, new APIRequestCallback<BINLookupResponse>() {
            @Override
            public void onSuccess(int responseCode, BINLookupResponse serializedResponse) {
                dataSource.showAddressOnCardCell(true);
                GlobalDataManager.getInstance().setBinLookupResponse(serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                dataSource.showAddressOnCardCell(false);
                GlobalDataManager.getInstance().setBinLookupResponse(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                dataSource.cardScanned(scanResult);
            }
        } else if (requestCode == CURRENCIES_REQUEST_CODE) {
            AmountedCurrency userChoiceCurrency = (AmountedCurrency) data.getSerializableExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY);
            if (userChoiceCurrency != null) {
                dataSource.currencySelectedByUser(userChoiceCurrency);
            }
        } else if (requestCode == WEB_PAYMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
    }
}


