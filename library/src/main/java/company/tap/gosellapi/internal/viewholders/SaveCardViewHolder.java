package company.tap.gosellapi.internal.viewholders;

import android.content.res.Resources;
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
        itemView.measure(View.MeasureSpec.makeMeasureSpec(Resources.getSystem().getDisplayMetrics().widthPixels, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        viewModel.setNeededHeight(itemView.getMeasuredHeight());

        display();
    }

    private void display() {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = viewModel.isShouldBeShown() ? viewModel.getNeededHeight() : 0;
        itemView.setLayoutParams(layoutParams);
    }
}
