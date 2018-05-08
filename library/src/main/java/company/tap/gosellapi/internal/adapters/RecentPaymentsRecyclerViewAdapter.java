package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.Card;

public class RecentPaymentsRecyclerViewAdapter extends RecyclerView.Adapter<RecentPaymentsRecyclerViewAdapter.RecentPaymentsViewHolder> {

    private ArrayList<Card> datasource = new ArrayList<>();

    public interface RecentPaymentsRecyclerViewAdapterListener {

        void recentPaymentItemClicked();
    }

    private RecentPaymentsRecyclerViewAdapterListener listener;

    public RecentPaymentsRecyclerViewAdapter(ArrayList<Card> datasource, RecentPaymentsRecyclerViewAdapterListener listener) {
        this.datasource = datasource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentPaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_payments, parent, false);
        return new RecentPaymentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPaymentsViewHolder holder, int position) {

        Card card = datasource.get(position);

        TextView cardLastDigits = holder.itemView.findViewById(R.id.cardLastDigits);
        cardLastDigits.setText(card.getLast4());

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.recentPaymentItemClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    static class RecentPaymentsViewHolder extends RecyclerView.ViewHolder {

        RecentPaymentsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
