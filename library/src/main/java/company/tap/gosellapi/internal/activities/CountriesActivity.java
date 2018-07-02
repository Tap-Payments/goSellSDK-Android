package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
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

public class CountriesActivity extends BaseActionBarActivity {
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out);
        setContentView(R.layout.gosellapi_activity_countries);

        ArrayList<String> countries = new ArrayList<>(Arrays.asList(Locale.getISOCountries()));

        RecyclerView countriesRecyclerView = findViewById(R.id.rvCountries);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        countriesRecyclerView.setLayoutManager(linearLayoutManager);

        CountriesRecyclerViewAdapter adapter = new CountriesRecyclerViewAdapter(countries);
        countriesRecyclerView.setAdapter(adapter);
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
}
