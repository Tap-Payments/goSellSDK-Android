package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.SaveCardViewModel;

public class SaveCardViewHolder
        extends PaymentOptionsBaseViewHolder<EmptyType, SaveCardViewHolder, SaveCardViewModel>{

    SaveCardViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(EmptyType data) {
        display();

        itemView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewModel.setNeededHeight(itemView.getMeasuredHeight());
    }

    private void display() {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = viewModel.isShouldBeShown() ? viewModel.getNeededHeight() : 0;
        itemView.setLayoutParams(layoutParams);
    }
}
