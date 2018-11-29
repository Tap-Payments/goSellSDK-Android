package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.custom_views.DatePicker;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class GoSellPaymentActivity extends BaseActivity implements PaymentOptionsDataManager.PaymentOptionsDataListener {
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
        dataSource = PaymentDataManager.getInstance().getPaymentOptionsDataManager(this);

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
        String logoPath = PaymentDataManager.getInstance().getSDKSettings().getData().getMerchant().getLogo();
        Glide.with(this).load(logoPath).apply(RequestOptions.circleCropTransform()).into(businessIcon);

        String businessNameString = PaymentDataManager.getInstance().getSDKSettings().getData().getMerchant().getName();
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
    public void startWebPayment(WebPaymentViewModel model) {

        Intent intent = new Intent(this, WebPaymentActivity.class);

        ActivityDataExchanger.getInstance().putExtra(model, WebPaymentActivity.IntentParameters.paymentOptionModel, intent);

        startActivityForResult(intent, WEB_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void cardExpirationDateClicked(CardCredentialsViewModel model) {

        String selectedMonth = null;
        String selectedYear = null;


        String modelExpirationMonth = model.getExpirationMonth();
        if (modelExpirationMonth != null && !modelExpirationMonth.isEmpty()) {

            selectedMonth = modelExpirationMonth;
        }

        String modelExpirationYear = model.getExpirationYear();
        if (modelExpirationYear != null && !modelExpirationYear.isEmpty()) {

            selectedYear = modelExpirationYear;
        }

        DatePicker.showInContext(this, selectedMonth, selectedYear, new DatePicker.DatePickerListener() {
            @Override
            public void dateSelected(String month, String year) {

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
        BINLookupResponse binLookupResponse = PaymentDataManager.getInstance().getBinLookupResponse();
        if(binLookupResponse == null) return;
        Intent intent = new Intent(this, GoSellCardAddressActivity.class);
        intent.putExtra(GoSellCardAddressActivity.INTENT_EXTRA_KEY_COUNTRY, binLookupResponse.getCountry());
        startActivity(intent);
    }

    @Override
    public void saveCardSwitchClicked(boolean isChecked, int saveCardBlockPosition) {
    }

    @Override
    public void binNumberEntered(String binNumber) {

        GoSellAPI.getInstance().retrieveBINLookupBINLookup(binNumber, new APIRequestCallback<BINLookupResponse>() {
            @Override
            public void onSuccess(int responseCode, BINLookupResponse serializedResponse) {
                dataSource.showAddressOnCardCell(true);
                PaymentDataManager.getInstance().setBinLookupResponse(serializedResponse);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                dataSource.showAddressOnCardCell(false);
                PaymentDataManager.getInstance().setBinLookupResponse(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                    CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                    dataSource.cardScanned(scanResult);
                }
                break;

            case CURRENCIES_REQUEST_CODE:
                AmountedCurrency userChoiceCurrency = (AmountedCurrency) data.getSerializableExtra(CurrenciesActivity.CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY);
                if (userChoiceCurrency != null) {
                    dataSource.currencySelectedByUser(userChoiceCurrency);
                }
                break;

            case WEB_PAYMENT_REQUEST_CODE:
                if(resultCode == RESULT_OK) finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
    }
}


