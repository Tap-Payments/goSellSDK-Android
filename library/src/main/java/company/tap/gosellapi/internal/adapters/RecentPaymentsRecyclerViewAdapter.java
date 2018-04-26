package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;

public class RecentPaymentsRecyclerViewAdapter extends RecyclerView.Adapter<RecentPaymentsRecyclerViewAdapter.RecentPaymentsViewHolder> {

    public interface RecentPaymentsRecyclerViewAdapterListener {

        void recentPaymentItemClicked();
    }

    private RecentPaymentsRecyclerViewAdapterListener listener;

    public RecentPaymentsRecyclerViewAdapter(RecentPaymentsRecyclerViewAdapterListener listener) {

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
        return 10;
    }

    static class RecentPaymentsViewHolder extends RecyclerView.ViewHolder {

        RecentPaymentsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
