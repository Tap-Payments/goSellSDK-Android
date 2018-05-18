package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;

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
    private String searchText = "";

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
            searchText = "";
        } else {
            dataSourceFiltered.clear();
            searchText = newText;

            for (String currencyCode : dataSource) {
                String currencyCodeLowered = currencyCode.toLowerCase();
                String currencyName = Utils.getCurrencyName(currencyCode, Utils.getCurrency(currencyCode));

                boolean presentInCurrencyCode = true;
                boolean presentInCurrencyName = currencyName.toLowerCase().contains(newText.toLowerCase());

                //search in currency code (not sequentally)
                int index = -1;
                for (char ch : newText.toCharArray()) {
                    index = currencyCodeLowered.indexOf(ch, index + 1);
                    if (index < 0) {
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
            tvCurrencyName.setText(getCurrencySelectionString(currencyCode));

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

        //with highlight on search text logic
        private SpannableStringBuilder getCurrencySelectionString(String currencyCode) {
            Currency currency = Utils.getCurrency(currencyCode);
            String symbol = "";
            int currencyNameIndex = 0;

            if (currency != null) {
                symbol = currency.getSymbol();
            }

            String currencyCodeLowered = currencyCode.toLowerCase();
            String currencyName = Utils.getCurrencyName(currencyCode, currency);


            SpannableStringBuilder sb = new SpannableStringBuilder(currencyCode);

            if (!symbol.isEmpty() && !symbol.equalsIgnoreCase(currencyCode)) {
                sb.append(" ");
                sb.append(symbol);
            }

            if (!currencyName.isEmpty()) {
                sb.append(" (");

                currencyNameIndex = sb.length();
                sb.append(currencyName);

                sb.append(")");
            }


            //formatting
            if (!currencyName.isEmpty() && !searchText.isEmpty()) {
                int index = currencyName.toLowerCase().indexOf(searchText);
                while (index >= 0) {
                    Utils.highlightText(itemView.getContext(), sb, currencyNameIndex + index, searchText);
                    index = currencyName.toLowerCase().indexOf(searchText, index + 1);
                }
            }

            //search in currency code (not sequentally)
            boolean allCharsPresent = true;
            int index = -1;
            for (char ch : searchText.toCharArray()) {
                index = currencyCodeLowered.indexOf(ch, index + 1);
                if (index < 0) {
                    allCharsPresent = false;
                }
            }

            if (allCharsPresent) {
                for (char ch : searchText.toCharArray()) {
                    index = currencyCodeLowered.indexOf(ch);

                    while (index >= 0) {
                        Utils.highlightText(itemView.getContext(), sb, index);
                        index = currencyCodeLowered.indexOf(ch, index + 1);
                    }
                }
            }

            return sb;
        }
    }
}
