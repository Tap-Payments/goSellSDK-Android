package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.utils.Utils;

/**
 * The type Countries recycler view adapter.
 */
public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountriesCellViewHolder> {
    /**
     * The interface Countries recycler view adapter callback.
     */
    public interface CountriesRecyclerViewAdapterCallback {
        /**
         * Item selected.
         *
         * @param country the country
         */
        void itemSelected(String country);
    }

    private CountriesRecyclerViewAdapterCallback callback;

    private final static int NO_SELECTION = -1;
    private ArrayList<String> datasource;
    private ArrayList<String> datasourceFiltered;
    private String selectedCountry;
    private String searchText = "";
    private int selectedPosition = NO_SELECTION;

    /**
     * Instantiates a new Countries recycler view adapter.
     *
     * @param datasource the datasource
     * @param callback   the callback
     */
    public CountriesRecyclerViewAdapter(ArrayList<String> datasource, CountriesRecyclerViewAdapterCallback callback) {
        this.datasource = datasource;
        this.callback = callback;

        prepareDataSources();
    }

    private void prepareDataSources() {
        Collections.sort(datasource);
        int selectedIndex = datasource.indexOf(selectedCountry);

        if (selectedIndex != NO_SELECTION) {
            datasource.remove(selectedIndex);
            datasource.add(0, selectedCountry);
        }
        datasourceFiltered = new ArrayList<>(datasource);
    }

    /**
     * Sets selection.
     *
     * @param newSelection the new selection
     */
    public void setSelection(int newSelection) {
        selectedCountry = datasourceFiltered.get(newSelection);

        if (selectedPosition != NO_SELECTION) {
            notifyItemChanged(selectedPosition);
        }

        selectedPosition = newSelection;
        notifyItemChanged(selectedPosition);
    }

    @NonNull
    @Override
    public CountriesCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_cell_countries, parent, false);
        return new CountriesCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesCellViewHolder holder, int position) {
        holder.bind(datasource.get(position));
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    /**
     * The type Countries cell view holder.
     */
    class CountriesCellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCountryName;
        private ImageView ivCountryChecked;

        private CountriesCellViewHolder(View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            ivCountryChecked = itemView.findViewById(R.id.ivCountryChecked);
            ivCountryChecked.setImageDrawable(Utils.setImageTint(itemView.getContext(), R.drawable.ic_checkmark_normal, R.color.black));

            itemView.setOnClickListener(this);
        }

        private void bind(String countryCode) {
            Locale locale = new Locale("", countryCode);
            tvCountryName.setText(locale.getDisplayCountry(Locale.ENGLISH));

            if (countryCode.equals(selectedCountry)) {
                ivCountryChecked.setVisibility(View.VISIBLE);
                selectedPosition = getAdapterPosition();
            } else {
                ivCountryChecked.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            setSelection(position);
            callback.itemSelected(selectedCountry);
        }
    }
}
