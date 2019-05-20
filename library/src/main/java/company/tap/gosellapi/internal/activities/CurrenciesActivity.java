package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.interfaces.CurrenciesAdapterCallback;
import company.tap.gosellapi.internal.adapters.CurrenciesRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;

/**
 * The type Currencies activity.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class CurrenciesActivity extends BaseActionBarActivity implements CurrenciesAdapterCallback {

    /**
     * The constant CURRENCIES_ACTIVITY_DATA.
     */
    public static final String CURRENCIES_ACTIVITY_DATA = "currenciesActivityData";
    /**
     * The constant CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY.
     */
    public static final String CURRENCIES_ACTIVITY_INITIAL_SELECTED_CURRENCY = "currenciesActivityInitialSelectedCurrency";
    /**
     * The constant CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY.
     */
    public static final String CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY = "currenciesActivityUserChoiceCurrency";
    private SearchView mSearchView;

    /**
     * The Currencies.
     */
    ArrayList<AmountedCurrency> currencies;
    private AmountedCurrency selectedCurrency;

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
    }

    private void initRecycler() {

        RecyclerView recycler = findViewById(R.id.recyclerCurrencies);

        adapter = new CurrenciesRecyclerViewAdapter(currencies, selectedCurrency, this);

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
       // setTitle(Utils.getFormattedCurrency(selectedCurrency));
        setTitle(getResources().getString(R.string.select_currency_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
// hide it for now
        mSearchView.setVisibility(View.INVISIBLE);
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
        //super.overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void itemSelected(AmountedCurrency currency) {

        //if ( this.selectedCurrency.equals(currency) ) { return; }

        this.selectedCurrency = currency;
        setTitle();
        setResult(RESULT_OK, new Intent().putExtra(CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY, selectedCurrency));
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra(CURRENCIES_ACTIVITY_USER_CHOICE_CURRENCY, selectedCurrency));
        super.onBackPressed();
    }
}
