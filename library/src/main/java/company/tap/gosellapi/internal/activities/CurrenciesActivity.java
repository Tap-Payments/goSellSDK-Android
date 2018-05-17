package company.tap.gosellapi.internal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Map;
import java.util.TreeMap;

import company.tap.gosellapi.R;

/**
 * Created by Roman Romanenko on 09.08.2016.
 */

public class CurrenciesActivity
        extends AppCompatActivity {
//    public static final String COUNTRY_CODE = "chosen_country_code";
//
//    ListView listCurrencies;
//
//    void countryChosen(int position) {
//        externalCode = result.get(position).getisdNumber();
//        Intent intent = new Intent();
//        intent.putExtra(COUNTRY_CODE, externalCode);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    ArrayAdapter<Country> arrayAdapter;
//
//
//        if (getIntent().getExtras() != null) {
//            externalCode = getIntent().getExtras().getString(COUNTRY_CODE);
//        }
//        arrayAdapter = new ArrayAdapter<Country>(this, R.layout.cell_country_code, result) {
//            @NonNull
//            @Override
//            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                ViewHolder viewHolder;
//                if (convertView == null) {
//                    convertView = inflater.inflate(R.layout.cell_country_code, parent, false);
//                    viewHolder = new ViewHolder(convertView);
//                    convertView.setTag(viewHolder);
//                    FontHelper.getInstance().bind(convertView);
//                } else {
//                    viewHolder = (ViewHolder) convertView.getTag();
//                }
//
//                Country c = result.get(position);
//                D.setFlag(CountryCodesActivity.this, c.getisoCode().gettwoLetters(), viewHolder.tvCountryIcon);
//                String iso = c.getisoCode().gettwoLetters();
//                String name = new Locale("", iso).getDisplayName(new Locale(Localization.getInstance().getCurrentLocaleString()));
//                String code;
//                if (pickerState == CountryPickerState.QUICK_ADD_ID) {
//                    code = c.getisoCode().getthreeLetters();
//                    viewHolder.tvCode.setText(code);
//                } else {
//                    code = c.getisdNumber();
//                    viewHolder.tvCode.setText(String.format("+%s", code));
//                }
//                viewHolder.ivCheckMark.setVisibility(externalCode != null && externalCode.equals(code) ? View.VISIBLE : View.INVISIBLE);
//                viewHolder.tvName.setText(name);
//                return convertView;
//            }
//
//            class ViewHolder {
//                private TextView tvCode;
//                private TextView tvName;
//                private ImageView tvCountryIcon;
//                private ImageView ivCheckMark;
//
//                ViewHolder(View view) {
//                    tvCode = view.findViewById(R.id.tvCountryCode);
//                    tvName = view.findViewById(R.id.tvCountryName);
//                    tvCountryIcon = view.findViewById(R.id.tvCountryIcon);
//                    ivCheckMark = view.findViewById(R.id.ivCheckMark);
//                }
//            }
//        };
//        lvCountryCodes.setAdapter(arrayAdapter);
//
//    private void notifyDataSetChanged() {
//        arrayAdapter.notifyDataSetChanged();
//
//        if (externalCode != null) {
//            int counter = 0;
//            for (Country country : result) {
//                String code = pickerState == CountryPickerState.QUICK_ADD_ID ? country.getisoCode().getthreeLetters() : country.getisdNumber();
//                if (code.equals(externalCode)) {
//                    lvCountryCodes.setSelection(counter);
//                    break;
//                }
//                counter++;
//            }
//        }
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        if (newText.equals("")) {
//            result.clear();
//            result.addAll(currentData());
//            notifyDataSetChanged();
//        } else {
//            result.clear();
//            String name;
//            String nameEn;
//            for (Country c : currentData()) {
//                name = new Locale("", c.getisoCode().gettwoLetters()).getDisplayName(new Locale(Localization.getInstance().getCurrentLocaleString()));
//                nameEn = new Locale("", c.getisoCode().gettwoLetters()).getDisplayName(new Locale("en"));
//                if (c.getisdNumber().contains(newText) ||
//                        name.toLowerCase().contains(newText.toLowerCase()) ||
//                        nameEn.toLowerCase().contains(newText.toLowerCase()) ||
//                        c.getisoCode().gettwoLetters().toLowerCase().contains(newText.toLowerCase()) ||
//                        c.getisoCode().getthreeLetters().toLowerCase().contains(newText.toLowerCase())) {
//                    result.add(c);
//                }
//            }
//            arrayAdapter.notifyDataSetChanged();
//        }
//        return false;
//    }


    public static final String CURRENCIES_ACTIVITY_DATA = "currenciesActivityData";
    private TreeMap<String, Double> currenciesSorted;
    private SearchView mSearchView;
    private RecyclerView recyclerCurrencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out);
        setContentView(R.layout.activity_currencies);

        getData();
        initRecycler();
    }

    private void getData() {
        //noinspection unchecked
        currenciesSorted = new TreeMap<>((Map<String, Double>) getIntent().getSerializableExtra(CURRENCIES_ACTIVITY_DATA));
    }

    private void initRecycler() {
        recyclerCurrencies = findViewById(R.id.recyclerCurrencies);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                mSearchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left);
    }
}
