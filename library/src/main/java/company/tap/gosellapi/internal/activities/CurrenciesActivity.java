package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;
import company.tap.gosellapi.internal.adapters.CurrenciesRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.data_managers.payment_options.CurrencySectionData;

public class CurrenciesActivity
        extends BaseActionBarActivity implements CurrenciesRecyclerViewAdapter.CurrenciesAdapterCallback {
    public static final String CURRENCIES_ACTIVITY_DATA = "currenciesActivityData";
    public static final String CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY = "currenciesActivityInitialSelectedCurrency";
    public static final String CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY = "currenciesActivityUserChoiceCurrency";
    private SearchView mSearchView;

    ArrayList<AmountedCurrency> currencies;
    private AmountedCurrency selectedCurrency;
    private ArrayList<String> dataKeys;

    private CurrenciesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out);
        setContentView(R.layout.gosellapi_activity_currencies);

        getData();
        initRecycler();
        setTitle();
    }

    private void getData() {
        //noinspection unchecked
        currencies = (ArrayList<AmountedCurrency>) getIntent().getSerializableExtra(CURRENCIES_ACTIVITY_DATA);
        selectedCurrency = (AmountedCurrency) getIntent().getSerializableExtra(CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY);

        dataKeys = new ArrayList<>();
        for (AmountedCurrency amountedCurrency : currencies) {
            dataKeys.add(amountedCurrency.getIsoCode());
        }
    }

    private void initRecycler() {
        RecyclerView recycler = findViewById(R.id.recyclerCurrencies);
        adapter = new CurrenciesRecyclerViewAdapter(dataKeys, selectedCurrency.getIsoCode(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.recycler_divider);
        if (divider != null) {
            dividerItemDecoration.setDrawable(divider);
        }
        recycler.addItemDecoration(dividerItemDecoration);
    }

    private void setTitle() {
        setTitle(Utils.getFormattedCurrency(selectedCurrency));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText.toLowerCase());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left);
    }

    @Override
    public void itemSelected(String currencyCode) {
        if (!this.selectedCurrency.getIsoCode().equalsIgnoreCase(currencyCode)) {
            this.selectedCurrency = CurrencySectionData.getAmountedCurrencyByCurrencyCode(currencyCode, currencies);
            setTitle();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra(CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY, selectedCurrency));
        super.onBackPressed();
    }
}
