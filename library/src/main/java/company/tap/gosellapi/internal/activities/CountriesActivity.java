package company.tap.gosellapi.internal.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CountriesRecyclerViewAdapter;

/**
 * The type Countries activity.
 */
public class CountriesActivity extends BaseActionBarActivity implements CountriesRecyclerViewAdapter.CountriesRecyclerViewAdapterCallback {
    private SearchView mSearchView;

    /**
     * The constant INTENT_EXTRA_KEY_COUNTRIES.
     */
    public static final String INTENT_EXTRA_KEY_COUNTRIES = "Countries";
    /**
     * The constant INTENT_EXTRA_KEY_SELECTED_COUNTRY.
     */
    public static final String INTENT_EXTRA_KEY_SELECTED_COUNTRY = "selectedCountry";

    /**
     * The constant COUNTRIES_ACTIVITY_USER_CHOICE_COUNTRY.
     */
    public static final String COUNTRIES_ACTIVITY_USER_CHOICE_COUNTRY = "CountriesActivityUserChoiceCountry";

    private String selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Fix for web view
         * Force it to portrait to fix resend request each time configurations change "Portrait to Landscape"
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out);
        setContentView(R.layout.gosellapi_activity_countries);

        Bundle extras = getIntent().getExtras();

        ArrayList<String> countries = new ArrayList<>(Arrays.asList(Locale.getISOCountries()));

        if (extras != null) {
            countries = extras.getStringArrayList(INTENT_EXTRA_KEY_COUNTRIES);
            selectedCountry = extras.getString(INTENT_EXTRA_KEY_SELECTED_COUNTRY);
        }

        RecyclerView countriesRecyclerView = findViewById(R.id.rvCountries);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        countriesRecyclerView.setLayoutManager(linearLayoutManager);

        CountriesRecyclerViewAdapter adapter = new CountriesRecyclerViewAdapter(countries, this);
        countriesRecyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.recycler_divider);
        if (divider != null) {
            dividerItemDecoration.setDrawable(divider);
        }
        countriesRecyclerView.addItemDecoration(dividerItemDecoration);

        if(countries != null) {
            int position = countries.indexOf(selectedCountry);
            countriesRecyclerView.scrollToPosition(position);
            adapter.setSelection(position);
        }

        setTitle();
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
//                adapter.filter(newText.toLowerCase());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra(COUNTRIES_ACTIVITY_USER_CHOICE_COUNTRY, selectedCountry));
        super.onBackPressed();
    }

    @Override
    public void itemSelected(String country) {
        selectedCountry = country;
        setTitle();
    }

    private void setTitle() {
        Locale locale = new Locale("", selectedCountry);
        setTitle(locale.getDisplayCountry(Locale.ENGLISH));
    }
}
