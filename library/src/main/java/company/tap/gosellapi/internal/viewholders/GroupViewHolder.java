package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.GroupViewModel;

/**
 * The type Group view holder.
 */
public class GroupViewHolder extends PaymentOptionsBaseViewHolder<String, GroupViewHolder, GroupViewModel> implements RecentPaymentsRecyclerViewAdapter.RecentPaymentsRecyclerViewAdapterShakingListener {

    /**
     * Instantiates a new Group view holder.
     *
     * @param view the view
     */
    public GroupViewHolder(View view) {

        super(view);
        this.titleTextView = view.findViewById(R.id.titleTextView);
        this.editTextView =  view.findViewById(R.id.editTitleTextView);
        this.cancelTextView =  view.findViewById(R.id.cancelTitleTextView);
        this.editTextView.setOnClickListener(v -> {
            editCard();
            if(!PaymentDataManager.getInstance().isCardPaymentProcessStarted())editCard();
        });

        this.cancelTextView.setOnClickListener(v->{
            cancelCardDelete();
        });
    }

    @Override
    public void bind(String data) {

        if("RECENT".equalsIgnoreCase(data)){
            titleTextView.setText(this.titleTextView.getContext().getString(R.string.textview_title_recent));
        }else if("OTHERS".equalsIgnoreCase(data)){
            titleTextView.setText(this.titleTextView.getContext().getString(R.string.textview_title_others));
        }else {
            titleTextView.setText(data);
        }


        if("RECENT".equalsIgnoreCase(data))
            this.editTextView.setVisibility(View.VISIBLE);
            else
            this.editTextView.setVisibility(View.INVISIBLE);
        if(viewModel!=null)
            viewModel.setGroupViewHolder(this);
    }

    private TextView titleTextView;
    private TextView editTextView;
    private TextView cancelTextView;

    public void editCard(){
      this.cancelTextView.setVisibility(View.VISIBLE);
      this.editTextView.setVisibility(View.INVISIBLE);
        viewModel.editItemClicked(this);
    }

    public void cancelCardDelete(){
        this.cancelTextView.setVisibility(View.INVISIBLE);
        this.editTextView.setVisibility(View.VISIBLE);
        viewModel.cancelItemClicked();
    }

    @Override
    public void changeGroupActionTitle() {
        this.cancelTextView.setVisibility(View.INVISIBLE);
        this.editTextView.setVisibility(View.VISIBLE);
    }
}
