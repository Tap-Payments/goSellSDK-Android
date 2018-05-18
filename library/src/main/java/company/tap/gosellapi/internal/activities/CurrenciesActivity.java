package company.tap.gosellapi.internal.activities;

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
import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;
import company.tap.gosellapi.internal.adapters.CurrenciesRecyclerViewAdapter;

public class CurrenciesActivity
        extends BaseActionBarActivity implements CurrenciesRecyclerViewAdapter.CurrenciesAdapterCallback {
//    public static final String COUNTRY_CODE = "chosen_country_code";
//
//    void countryChosen(int position) {
//        externalCode = result.get(position).getisdNumber();
//        Intent intent = new Intent();
//        intent.putExtra(COUNTRY_CODE, externalCode);
//        setResult(RESULT_OK, intent);
//        finish();
//    }


    public static final String CURRENCIES_ACTIVITY_DATA = "currenciesActivityData";
    public static final String CURRENCIES_ACTIVITY_SELECTED_CURRENCY = "currenciesActivityInitialCurrency";
    private SearchView mSearchView;

    private HashMap<String, Double> currencies;
    private String selectedCurrencyCode;
    private ArrayList<String> dataKeys;

    private RecyclerView recycler;
    private CurrenciesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out);
        setContentView(R.layout.activity_currencies);

        getData();
        initRecycler();
        setTitle();
    }

    private void getData() {
        //noinspection unchecked
        currencies = (HashMap<String, Double>) getIntent().getSerializableExtra(CURRENCIES_ACTIVITY_DATA);
        selectedCurrencyCode = getIntent().getStringExtra(CURRENCIES_ACTIVITY_SELECTED_CURRENCY);

        dataKeys = new ArrayList<>(currencies.keySet());
    }

    private void initRecycler() {
        recycler = findViewById(R.id.recyclerCurrencies);
        adapter = new CurrenciesRecyclerViewAdapter(dataKeys, selectedCurrencyCode, this);
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
        double selectedAmount = currencies.get(selectedCurrencyCode);
        setTitle(Utils.getFormattedCurrency(selectedCurrencyCode, selectedAmount));
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
                adapter.filter(newText);
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
        if (!currencyCode.equalsIgnoreCase(selectedCurrencyCode)) {
            selectedCurrencyCode = currencyCode;
            setTitle();
        }
    }
}
