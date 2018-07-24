package company.tap.gosellapi.internal.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.responses.AddressFormatsResponse;
import company.tap.gosellapi.internal.data_managers.LoadingScreenManager;

public class GoSellCardAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gosellapi_activity_card_address);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getAddressOnCardFormat();
            }
        });
    }

    private void getAddressOnCardFormat() {

        LoadingScreenManager.getInstance().showLoadingScreen(this);

        GoSellAPI.getInstance().retrieveAddressFormats(new APIRequestCallback<AddressFormatsResponse>() {
            @Override
            public void onSuccess(int responseCode, AddressFormatsResponse serializedResponse) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
            }

            @Override
            public void onFailure(GoSellError errorDetails) {
                LoadingScreenManager.getInstance().closeLoadingScreen();
            }
        });
    }
}

