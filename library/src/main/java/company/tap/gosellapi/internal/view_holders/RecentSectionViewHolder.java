package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;

public class RecentSectionViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView mRecycler;

    private MainRecyclerViewAdapter.MainRecyclerViewAdapterListener listener;

    public RecentSectionViewHolder(View view, MainRecyclerViewAdapter.MainRecyclerViewAdapterListener listener) {
        super(view);

        this.listener = listener;
        mRecycler = view.findViewById(R.id.recentPaymentsRecyclerView);
    }

    public void bind() {

        // Configure layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(layoutManager);

        // Configure RecentPaymentsRecyclerViewAdapter
        RecentPaymentsRecyclerViewAdapter adapter = new RecentPaymentsRecyclerViewAdapter(new RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener() {
            @Override
            public void recentPaymentItemClicked() {
                listener.recentPaymentItemClicked();
            }
        });


        mRecycler.setAdapter(adapter);
    }
}
