package company.tap.gosellapi.internal.viewholders;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.RecentSectionViewModel;

public class RecentSectionViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<SavedCard>, RecentSectionViewHolder, RecentSectionViewModel>
        implements RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener {

    RecentSectionViewHolder(View itemView) {
        super(itemView);
    }

    private RecyclerView recentPaymentsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecentPaymentsRecyclerViewAdapter adapter;
    private ArrayList<SavedCard> data;

    @Override
    public void bind(ArrayList<SavedCard> data) {
        recentPaymentsRecyclerView = itemView.findViewById(R.id.recentPaymentsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recentPaymentsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecentPaymentsRecyclerViewAdapter(data, this);
        recentPaymentsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setFocused(boolean isFocused) {
        if (adapter != null) {
            adapter.setFocused(isFocused);
        }
    }

    @Override
    public void recentPaymentItemClicked(int position) {
        viewModel.recentItemClicked(position);
    }

    @Override
    public Parcelable saveState() {
        return linearLayoutManager.onSaveInstanceState();
    }

    @Override
    public void restoreState(Parcelable state) {
        if (state != null) {
            linearLayoutManager.onRestoreInstanceState(state);
        }
    }
}
