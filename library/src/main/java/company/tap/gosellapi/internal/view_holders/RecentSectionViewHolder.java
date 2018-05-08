package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class RecentSectionViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<ArrayList<Card>>>
        implements RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener {
    RecentSectionViewHolder(View itemView, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(itemView, focusedStateInterface);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<ArrayList<Card>> data) {

        RecyclerView recentPaymentsRecyclerView = itemView.findViewById(R.id.recentPaymentsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recentPaymentsRecyclerView.setLayoutManager(linearLayoutManager);

        RecentPaymentsRecyclerViewAdapter adapter = new RecentPaymentsRecyclerViewAdapter(data.getData(), new RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener() {
            @Override
            public void recentPaymentItemClicked() {

            }
        });
        recentPaymentsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setFocused(boolean isFocused) {

    }

    @Override
    public void recentPaymentItemClicked() {
        focusedStateInterface.setFocused(getAdapterPosition());
    }
}
