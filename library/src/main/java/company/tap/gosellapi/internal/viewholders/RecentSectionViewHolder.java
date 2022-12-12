package company.tap.gosellapi.internal.viewholders;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.RecentSectionViewModel;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;

/**
 * The type Recent section view holder.
 */
public class RecentSectionViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<SavedCard>, RecentSectionViewHolder, RecentSectionViewModel>
        implements RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterListener {

    /**
     * Instantiates a new Recent section view holder.
     *
     * @param itemView the item view
     */
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
        for(int i=0;  i<data.size();i++){
            if ((data != null && PaymentDataSource.getInstance().getCardType() != null &&  !PaymentDataSource.getInstance().getCardType().toString().equals("ALL"))){
                if( !PaymentDataSource.getInstance().getCardType().toString().equals(data.get(i).getFunding())) {
                    data.remove(i);
                }
            }
        }


        adapter = new RecentPaymentsRecyclerViewAdapter(data, this);
        recentPaymentsRecyclerView.setAdapter(adapter);
        if(viewModel!=null)
        viewModel.setRecentSectionViewHolder(this);
    }

    @Override
    public void setFocused(boolean isFocused) {
//        System.out.println("afterText changed ...");
        if (adapter != null) {
            adapter.clearFocus();
        }
    }

    @Override
    public void recentPaymentItemClicked(int position) {
        viewModel.recentItemClicked(position);
    }

    @Override
    public void deleteCard(@NonNull String cardId) {
      viewModel.deleteCard(cardId);
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


    public void shakeAllCards(GroupViewHolder groupViewHolderListener) {
//        Log.d("RecentSectionViewHolder","shake all cards...");
        viewModel.disablePayButton();
        adapter.shakeAllCards(groupViewHolderListener);
    }

    public void stopShakingAllCards() {
        if(adapter== null) return;
//        Log.d("RecentSectionViewHolder","stop shake all cards...");
        adapter.stopShakingAllCards();
    }
    public void disableRecentView() {

        if(recentPaymentsRecyclerView!=null) {
            int childCount = recentPaymentsRecyclerView.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                if (recentPaymentsRecyclerView.getChildAt(i) != null) {
                    recentPaymentsRecyclerView.getChildAt(i).setClickable(false);
                    recentPaymentsRecyclerView.getChildAt(i).setEnabled(false);
                    recentPaymentsRecyclerView.getChildAt(i).setFocusable(false);
                }
            }

           // Log.d("RecentSectionViewHolder", "disabled recyler...");
        }

    }
    public void enableRecentView() {
        if(recentPaymentsRecyclerView!=null) {
            int childCount = recentPaymentsRecyclerView.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                if (recentPaymentsRecyclerView.getChildAt(i) != null) {
                    recentPaymentsRecyclerView.getChildAt(i).setClickable(true);
                    recentPaymentsRecyclerView.getChildAt(i).setEnabled(true);
                    recentPaymentsRecyclerView.getChildAt(i).setFocusable(true);
                }
            }


        }
    }



}
