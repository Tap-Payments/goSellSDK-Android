package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import company.tap.gosellapi.R;

public class CurrenciesRecyclerViewAdapter extends RecyclerView.Adapter<CurrenciesRecyclerViewAdapter.ViewHolder> {
    private HashMap<String, Double> dataSource;

    public CurrenciesRecyclerViewAdapter(HashMap<String, Double> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_currencies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCurrencyName;
        private TextView tvCurrencyAmount;

        private ViewHolder(View itemView) {
            super(itemView);
            tvCurrencyName = itemView.findViewById(R.id.tvCurrencyName);
            tvCurrencyAmount = itemView.findViewById(R.id.tvCurrencyAmount);

            itemView.setOnClickListener(this);
        }

        private void bind(Double aDouble) {

        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
