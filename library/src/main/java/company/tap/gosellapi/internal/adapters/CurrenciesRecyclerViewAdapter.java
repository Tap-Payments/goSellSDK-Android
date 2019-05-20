package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.regex.Pattern;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.interfaces.CurrenciesAdapterCallback;
import company.tap.gosellapi.internal.utils.CurrencyFormatter;
import company.tap.gosellapi.internal.utils.LocalizedCurrency;
import company.tap.gosellapi.internal.utils.Utils;

/**
 * The type Currencies recycler view adapter.
 */
public class CurrenciesRecyclerViewAdapter extends RecyclerView.Adapter<CurrenciesRecyclerViewAdapter.CurrencyCellViewHolder> {

    private CurrenciesAdapterCallback callback;
    private final static int NO_SELECTION = -1;
    private ArrayList<LocalizedCurrency> allCurrencies;
    private ArrayList<LocalizedCurrency> filteredCurrencies;
    private LocalizedCurrency selectedCurrency;
    private String searchQuery = null;
    private ArrayList<LocalizedCurrency> getAllCurrencies() { return allCurrencies; }
    private ArrayList<LocalizedCurrency> getFilteredCurrencies() { return filteredCurrencies; }
    private LocalizedCurrency getSelectedCurrency() { return selectedCurrency; }
    private String getSearchQuery() { return searchQuery; }

    private int selectedPosition = NO_SELECTION;

    /**
     * Instantiates a new Currencies recycler view adapter.
     *
     * @param allCurrencies    the all currencies
     * @param selectedCurrency the selected currency
     * @param callback         the callback
     */
    public CurrenciesRecyclerViewAdapter(ArrayList<AmountedCurrency> allCurrencies, AmountedCurrency selectedCurrency, CurrenciesAdapterCallback callback) {

        this.allCurrencies = new ArrayList<>();

        LocalizedCurrency selected = null;

        for ( AmountedCurrency currency : allCurrencies) {

            LocalizedCurrency localizedCurrency = new LocalizedCurrency(currency);
            this.allCurrencies.add(localizedCurrency);

            if ( currency.equals(selectedCurrency) ) {

                selected = localizedCurrency;
            }
        }

        if ( selected == null ) {

            selected = this.allCurrencies.get(0);
        }
        this.selectedCurrency   = selected;
        this.callback           = callback;
        prepareDataSources();
    }

    @NonNull
    @Override
    public CurrencyCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_cell_currencies, parent, false);
        return new CurrencyCellViewHolder(view);
    }

    private void prepareDataSources() {
        Collections.sort(allCurrencies);
        filter(searchQuery);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyCellViewHolder holder, int position) {
       String bindedCurrency =
                               (getFilteredCurrencies()!= null && getFilteredCurrencies().get(position)!=null )
                               ?
                               getFilteredCurrencies().get(position).getCurrency().getCurrency()
                               :
                               getAllCurrencies().get(position).getCurrency().getCurrency();
        holder.bind(bindedCurrency);
    }

    @Override
    public int getItemCount() {
        return getFilteredCurrencies()!=null? getFilteredCurrencies().size(): getAllCurrencies().size();

    }

    private void setSelection(int newSelection) {

        selectedCurrency = getFilteredCurrencies()!=null ?getFilteredCurrencies().get(newSelection)
                           : getAllCurrencies().get(newSelection);
        if (selectedPosition != NO_SELECTION) {
            notifyItemChanged(selectedPosition);
        }

        selectedPosition = newSelection;
        notifyItemChanged(selectedPosition);
    }

    /**
     * Filter.
     *
     * @param newText the new text
     */
    public void filter(@NonNull String newText) {
        if ( searchQuery == newText ) { return; }
        searchQuery = newText;
        this.filteredCurrencies = Utils.List.filter(getAllCurrencies(), searchQueryFilter);
        notifyDataSetChanged();
    }

    private Utils.List.Filter<LocalizedCurrency> searchQueryFilter = new Utils.List.Filter<LocalizedCurrency>() {

        @Override
        public boolean isIncluded(LocalizedCurrency object) {

            String query = getSearchQuery();
            if ( query.length() == 0 ) {

                return true;
            }

            Pattern pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);

            if (pattern.matcher(object.getCurrency().getCurrency()).find()) {

                return true;
            }

            if ( pattern.matcher(object.getLocalizedDisplayName()).find() ) {

                return true;
            }

            return false;
        }
    };


    /**
     * The type Currency cell view holder.
     */
    class CurrencyCellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCurrencyName;
        private ImageView ivCurrencyChecked;

        private CurrencyCellViewHolder(View itemView) {

            super(itemView);
            tvCurrencyName = itemView.findViewById(R.id.tvCurrencyName);
            ivCurrencyChecked = itemView.findViewById(R.id.ivCurrencyChecked);
            ivCurrencyChecked.setImageDrawable(Utils.setImageTint(itemView.getContext(), R.drawable.ic_checkmark_normal, R.color.black));

            itemView.setOnClickListener(this);
        }

        private void bind(String currencyCode) {
            tvCurrencyName.setText(getCurrencySelectionString(currencyCode));

            if (currencyCode.equals(selectedCurrency)) {
                ivCurrencyChecked.setVisibility(View.VISIBLE);
                selectedPosition = getAdapterPosition();
            } else {
                ivCurrencyChecked.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            setSelection(position);

            AmountedCurrency selectedCurrency = getFilteredCurrencies()!=null ? getFilteredCurrencies().get(position).getCurrency()
                                                : getAllCurrencies().get(position).getCurrency();
            callback.itemSelected(selectedCurrency);
        }

        //with highlight on search text logic
        private SpannableStringBuilder getCurrencySelectionString(String currencyCode) {
            Currency currency = Utils.getCurrency(currencyCode);
            String symbol = "";
            int currencyNameIndex = 0;

            if (currency != null) {
                symbol = currency.getSymbol(itemView.getContext().getResources().getConfiguration().locale);
            }

            String currencyCodeLowered = currencyCode.toLowerCase();
            String currencyName = Utils.getCurrencyName(currencyCode, currency,itemView.getContext());

//            SpannableStringBuilder sb = new SpannableStringBuilder(currencyCode);
            SpannableStringBuilder sb = new SpannableStringBuilder();

            if (!symbol.isEmpty() && !symbol.equalsIgnoreCase(currencyCode)) {
                //sb.append(" ");
                //sb.append(symbol);
            }

            if (!currencyName.isEmpty()) {
//                sb.append(" (");

                currencyNameIndex = sb.length();
                sb.append(currencyName);

//                sb.append(")");
            }
            /*
              SearchQuery check done By Haitham >>> to avoid null pointer exception
             */
            if(searchQuery!=null) {
                //formatting
                if (!currencyName.isEmpty()  && !searchQuery.isEmpty()) {
                    int index = currencyName.toLowerCase().indexOf(searchQuery);
                    while (index >= 0) {
                        Utils.highlightText(itemView.getContext(), sb, currencyNameIndex + index, searchQuery);
                        index = currencyName.toLowerCase().indexOf(searchQuery, index + 1);
                    }
                }

                //search in currency code (not sequentally)
                ArrayList<Integer> indexesToHighlight = new ArrayList<>();
                int index = -1;
                for (char ch : searchQuery.toCharArray()) {
                    index = currencyCodeLowered.indexOf(ch, index + 1);
                    if (index >= 0) {
                        indexesToHighlight.add(index);
                    }
                }
                if (indexesToHighlight.size() == searchQuery.length()) {
                    for (int i : indexesToHighlight) {
                        Utils.highlightText(itemView.getContext(), sb, i);
                    }
                }
            }
            return sb;
        }
    }
}
