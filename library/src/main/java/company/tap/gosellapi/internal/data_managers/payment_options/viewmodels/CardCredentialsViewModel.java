package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.CardCredentialsViewHolder;

public class CardCredentialsViewModel
        extends PaymentOptionsBaseViewModel<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {

    public CardCredentialsViewModel(PaymentOptionsDataManager parentDataManager, ArrayList<PaymentOption> data, int modelType) {
        super(parentDataManager, data, modelType);
    }

    public void cardScannerButtonClicked() {
        parentDataManager.cardScannerButtonClicked();
    }

    public void addressOnCardClicked() {
        parentDataManager.addressOnCardClicked();
    }

    public void saveCardSwitchClicked(boolean state) {
        parentDataManager.saveCardSwitchCheckedChanged(state);
    }
}
