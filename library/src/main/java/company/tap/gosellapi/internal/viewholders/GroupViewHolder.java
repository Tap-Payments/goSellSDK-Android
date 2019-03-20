package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.TextView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.RecentPaymentsRecyclerViewAdapter;
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
        });

        this.cancelTextView.setOnClickListener(v->{
            cancelCardDelete();
        });
    }

    @Override
    public void bind(String data) {

        titleTextView.setText(data);
        if("RECENT".equalsIgnoreCase(data))
            this.editTextView.setVisibility(View.VISIBLE);
            else
            this.editTextView.setVisibility(View.INVISIBLE);
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
