package company.tap.gosellapi.internal.viewholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.RecentSectionViewModel;

public class RecentSectionViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<Card>, RecentSectionViewHolder, RecentSectionViewModel>
        implements RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener {

    RecentSectionViewHolder(View itemView) {
        super(itemView);
    }

    private RecyclerView recentPaymentsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecentPaymentsRecyclerViewAdapter adapter;
    private ArrayList<Card> data;

    @Override
    public void bind(ArrayList<Card> data) {
        if (recentPaymentsRecyclerView == null) {
            recentPaymentsRecyclerView = itemView.findViewById(R.id.recentPaymentsRecyclerView);
        }

        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recentPaymentsRecyclerView.setLayoutManager(linearLayoutManager);
        }

        if (adapter == null) {
            adapter = new RecentPaymentsRecyclerViewAdapter(data, this);
            recentPaymentsRecyclerView.setAdapter(adapter);
        }

//        if (this.data == null || this.data != data) {
//            this.data = data;
//
//        }
    }

    @Override
    public void setFocused(boolean isFocused) {
        if (adapter != null) {
            adapter.setFocused(isFocused);
        }
    }

    @Override
    public void recentPaymentItemClicked(int position, Card card) {
        viewModel.recentItemClicked(getAdapterPosition());
    }
}
