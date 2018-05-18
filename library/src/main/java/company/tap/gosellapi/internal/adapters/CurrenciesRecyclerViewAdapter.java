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
import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;

public class CurrenciesRecyclerViewAdapter extends RecyclerView.Adapter<CurrenciesRecyclerViewAdapter.CurrencyCellViewHolder> {
    public interface CurrenciesAdapterCallback {
        void itemSelected(String currencyCode);
    }
    private CurrenciesAdapterCallback callback;

    private HashMap<String, Double> dataSource;
    private String selectedCurrencyCode;
    private ArrayList<String> dataKeys;

    private int selectedPosition;

    public CurrenciesRecyclerViewAdapter(HashMap<String, Double> dataSource, String selectedCurrencyCode, CurrenciesAdapterCallback callback) {
        this.dataSource = dataSource;
        this.selectedCurrencyCode = selectedCurrencyCode;
        this.callback = callback;

        createDataKeys();
    }

    private void createDataKeys() {
        dataKeys = new ArrayList<>(dataSource.keySet());
        Collections.sort(dataKeys);
    }

    @NonNull
    @Override
    public CurrencyCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_currencies, parent, false);
        return new CurrencyCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyCellViewHolder holder, int position) {
        holder.bind(dataKeys.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    private void removePreviousSelection(int newSelection) {
        selectedCurrencyCode = dataKeys.get(newSelection);

        notifyItemChanged(selectedPosition);
        selectedPosition = newSelection;
        notifyItemChanged(selectedPosition);
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
            callback.itemSelected(dataKeys.get(position));
        }
    }
}
