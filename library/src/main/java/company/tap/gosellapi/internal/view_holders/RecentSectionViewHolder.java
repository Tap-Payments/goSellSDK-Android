package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;

public class RecentSectionViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView mRecycler;

    public RecentSectionViewHolder(View view) {
        super(view);

        mRecycler = view.findViewById(R.id.recentPaymentsRecyclerView);
    }

    public void bind() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecentPaymentsRecyclerViewAdapter adapter = new RecentPaymentsRecyclerViewAdapter();

        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(adapter);
    }
}
