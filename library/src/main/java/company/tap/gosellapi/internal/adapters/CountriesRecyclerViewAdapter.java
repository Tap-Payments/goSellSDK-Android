package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountriesCellViewHolder> {
    private ArrayList<String> datasource;

    public CountriesRecyclerViewAdapter(ArrayList<String> datasource) {
        this.datasource = datasource;
    }

    @NonNull
    @Override
    public CountriesCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_cell_countries, parent, false);
        return new CountriesCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesCellViewHolder holder, int position) {
        Locale locale = new Locale("", datasource.get(position));
        holder.bind(locale.getDisplayCountry());
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

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

        private void bind(String countryName) {
            tvCountryName.setText(countryName);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
        }
    }
}
