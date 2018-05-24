package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.SaveCardViewHolder;

public class SaveCardViewModel
        extends PaymentOptionsBaseViewModel<EmptyType, SaveCardViewHolder, SaveCardViewModel> {
    private int neededHeight;
    private boolean shouldBeShown;

    public SaveCardViewModel(PaymentOptionsDataManager parentDataManager, EmptyType data, int modelType) {
        super(parentDataManager, data, modelType);
    }

    public void displaySaveCard(boolean show) {
        shouldBeShown = show;
        updateData();
    }

    public void setNeededHeight(int neededHeight) {
        parentDataManager.setSaveCardHeight(neededHeight);
        this.neededHeight = neededHeight;
    }

    public boolean isShouldBeShown() {
        return shouldBeShown;
    }

    public int getNeededHeight() {
        return neededHeight;
    }
}
