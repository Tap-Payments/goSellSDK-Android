package company.tap.gosellapi.internal.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;

public class GoSellCardAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_card_address);

        getAddressOnCardFormat();
    }

    private void getAddressOnCardFormat() {

        GoSellAPI.getInstance().retrieveAddressFormats(new APIRequestCallback<AddressFormatsResponse>() {
            @Override
            public void onSuccess(int responseCode, AddressFormatsResponse serializedResponse) {
            }

            @Override
            public void onFailure(GoSellError errorDetails) {

            }
        });

    }
}

