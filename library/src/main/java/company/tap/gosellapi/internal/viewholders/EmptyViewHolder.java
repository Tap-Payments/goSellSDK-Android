package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.EmptyViewModel;

/**
 * The type Empty view holder.
 */
public class EmptyViewHolder
        extends PaymentOptionsBaseViewHolder<EmptyViewModelData, EmptyViewHolder, EmptyViewModel> {

    /**
     * Instantiates a new Empty view holder.
     *
     * @param view the view
     */
    EmptyViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(EmptyViewModelData data) {
        display();
    }


    private void display() {

        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = viewModel.getData().getContentHeight();
        itemView.setLayoutParams(layoutParams);
    }
}