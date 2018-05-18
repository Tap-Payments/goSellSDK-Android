package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;

public class CurrenciesRecyclerViewAdapter extends RecyclerView.Adapter<CurrenciesRecyclerViewAdapter.CurrencyCellViewHolder> {
    public interface CurrenciesAdapterCallback {
        void itemSelected(String currencyCode);
    }
    private CurrenciesAdapterCallback callback;

    private final static int NO_SELECTION = -1;
    private ArrayList<String> dataSource;
    private ArrayList<String> dataSourceFiltered;
    private String selectedCurrencyCode;

    private int selectedPosition = NO_SELECTION;

    public CurrenciesRecyclerViewAdapter(ArrayList<String> dataSource, String selectedCurrencyCode, CurrenciesAdapterCallback callback) {
        this.dataSource = dataSource;
        this.selectedCurrencyCode = selectedCurrencyCode;
        this.callback = callback;

        dataSourceFiltered = new ArrayList<>(dataSource);
    }

    @NonNull
    @Override
    public CurrencyCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_currencies, parent, false);
        return new CurrencyCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyCellViewHolder holder, int position) {
        holder.bind(dataSourceFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSourceFiltered.size();
    }

    private void removePreviousSelection(int newSelection) {
        selectedCurrencyCode = dataSourceFiltered.get(newSelection);

        if (selectedPosition != NO_SELECTION) {
            notifyItemChanged(selectedPosition);
        }

        selectedPosition = newSelection;
        notifyItemChanged(selectedPosition);
    }

    public void filter(String newText) {
        if (newText.equals("")) {
            dataSourceFiltered.clear();
            dataSourceFiltered.addAll(dataSource);
        } else {
            dataSourceFiltered.clear();

            for (String currencyCode : dataSource) {
                String currencyCodeLowered = currencyCode.toLowerCase();
                String currencyName = Utils.getCurrencyName(currencyCode).toLowerCase();

                boolean presentInCurrencyCode = true;
                boolean presentInCurrencyName = currencyName.contains(newText.toLowerCase());

                //search in currency code (not sequentally)
                for (char ch : newText.toCharArray()) {
                    if (currencyCode.indexOf(ch) < 0 && currencyCodeLowered.indexOf(ch) < 0) {
                        presentInCurrencyCode = false;
                    }
                }

                if (presentInCurrencyCode || presentInCurrencyName) {
                    dataSourceFiltered.add(currencyCode);
                }
            }
        }
        notifyDataSetChanged();
    }

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
            tvCurrencyName.setText(Utils.getCurrencySelectionString(currencyCode));
            if (currencyCode.equals(selectedCurrencyCode)) {
                ivCurrencyChecked.setVisibility(View.VISIBLE);
                selectedPosition = getAdapterPosition();
            } else {
                ivCurrencyChecked.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            removePreviousSelection(position);
            callback.itemSelected(dataSourceFiltered.get(position));
        }
    }
}
