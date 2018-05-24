package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.EmptyViewHolder;

public class EmptyViewModel
        extends PaymentOptionsBaseViewModel<EmptyType, EmptyViewHolder, EmptyViewModel> {
    private int specifiedHeight;
    private boolean shouldBeShown;

    public EmptyViewModel(PaymentOptionsDataManager parentDataManager, EmptyType data, int modelType) {
        super(parentDataManager, data, modelType);
    }

    public void displayEmpty(boolean show) {
        shouldBeShown = show;
        updateData();
    }

    public void setSpecifiedHeight(int specifiedHeight) {
        if (this.specifiedHeight != specifiedHeight) {
            this.specifiedHeight = specifiedHeight;
            updateData();
        }
    }

    public int getSpecifiedHeight() {
        return specifiedHeight;
    }

    public boolean isShouldBeShown() {
        return shouldBeShown;
    }
}
