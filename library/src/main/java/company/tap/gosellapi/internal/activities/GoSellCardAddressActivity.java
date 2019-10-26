package company.tap.gosellapi.internal.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.AddressOnCardRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.enums.AddressFormat;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.BillingAddressField;
import company.tap.gosellapi.internal.api.models.BillingAddressFormat;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;

/**
 * The type Go sell card address activity.
 */
public class GoSellCardAddressActivity extends BaseActionBarActivity implements AddressOnCardRecyclerViewAdapter.AddressonCardRecyclerViewInterface, LoadingScreenManager.LoadingScreenListener {

    /**
     * The constant INTENT_EXTRA_KEY_COUNTRY.
     */
    public static final String INTENT_EXTRA_KEY_COUNTRY = "Country";

    private String currentCountry;
    private AddressFormatsResponse currentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Fix for web view
         * Force it to portrait to fix resend request each time configurations change "Portrait to Landscape"
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.gosellapi_activity_card_address);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            currentCountry = extras.getString(INTENT_EXTRA_KEY_COUNTRY);
        }

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.post(new Runnable() {
            @Override
            public void run() {
                getAddressOnCardFormat();
            }
        });

        setTitle(getString(R.string.actionbar_title_address_on_card));
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Fix for web view
         * Force it to portrait to fix resend request each time configurations change "Portrait to Landscape"
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    private void getAddressOnCardFormat() {
        LoadingScreenManager.getInstance().showLoadingScreen(this);

        GoSellAPI.getInstance().retrieveAddressFormats(new APIRequestCallback<AddressFormatsResponse>() {
            @Override
            public void onSuccess(int responseCode, AddressFormatsResponse serializedResponse) {
                currentResponse = serializedResponse;
                initAddressRecyclerView(currentResponse);

                LoadingScreenManager.getInstance().closeLoadingScreenWithListener(GoSellCardAddressActivity.this);
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
            }
        });
    }

    private AddressFormat getAddressFormatForCountry(String country) {
        if(!country.isEmpty()) {
            return currentResponse.getCountryFormats().get(currentCountry);
        }
        else {
            return null;
        }
    }

    private ArrayList<BillingAddressField> getFieldsForFormat(AddressFormat format) {
        ArrayList<BillingAddressField> currentBillingAddressFormats = new ArrayList<>();

        for(BillingAddressFormat billingAddressFormat : currentResponse.getFormats()) {

            if(billingAddressFormat.getName().equals(format)) {
                currentBillingAddressFormats = billingAddressFormat.getFields();
                break;
            }
        }

        return currentBillingAddressFormats;
    }

    private void initAddressRecyclerView(AddressFormatsResponse response) {

        AddressFormat currentFormat = getAddressFormatForCountry(currentCountry);
        if(currentFormat == null) return;

        ArrayList<BillingAddressField> formatFields = getFieldsForFormat(currentFormat);
        if(formatFields == null) return;

        RecyclerView addressOnCardRecyclerView = findViewById(R.id.addressOnCardRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        addressOnCardRecyclerView.setLayoutManager(linearLayoutManager);

        AddressOnCardRecyclerViewAdapter adapter = new AddressOnCardRecyclerViewAdapter(currentCountry, formatFields, response.getFields(), this);
        addressOnCardRecyclerView.setAdapter(adapter);

        float offsetPx = getResources().getDimension(R.dimen.recycler_view_bottom_offset);
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        addressOnCardRecyclerView.addItemDecoration(bottomOffsetDecoration);
    }

    private static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        /**
         * Instantiates a new Bottom offset decoration.
         *
         * @param bottomOffset the bottom offset
         */
        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);

            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }

    @Override
    public void countryDropdownClicked() {

        if(currentResponse == null) {
            return;
        }

        HashMap<String, AddressFormat> countryFormats = currentResponse.getCountryFormats();

        ArrayList<String> countries = new ArrayList<>(countryFormats.keySet());

        Intent intent = new Intent(this, CountriesActivity.class);
        intent.putExtra(CountriesActivity.INTENT_EXTRA_KEY_COUNTRIES, countries);
        intent.putExtra(CountriesActivity.INTENT_EXTRA_KEY_SELECTED_COUNTRY, currentCountry);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Bundle extras = data.getExtras();
            if(extras == null) return;

            if(resultCode == RESULT_OK) {
                currentCountry = extras.getString(CountriesActivity.COUNTRIES_ACTIVITY_USER_CHOICE_COUNTRY);
                initAddressRecyclerView(currentResponse);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
            }
    }

    @Override
    public void onLoadingScreenClosed() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }
}

