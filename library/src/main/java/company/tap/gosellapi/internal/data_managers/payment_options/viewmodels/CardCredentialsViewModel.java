package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.CardCredentialsViewHolder;

public class CardCredentialsViewModel
        extends PaymentOptionsBaseViewModel<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {
    private ArrayList<PaymentOption> dataOriginal;

    public CardCredentialsViewModel(PaymentOptionsDataManager parentDataManager, ArrayList<PaymentOption> data, int modelType) {
        super(parentDataManager, data, modelType);
        dataOriginal = new ArrayList<>(data);
    }

    public void cardScannerButtonClicked() {
        parentDataManager.cardScannerButtonClicked();
    }

    public void addressOnCardClicked() {
        parentDataManager.addressOnCardClicked();
    }

    public void saveCardSwitchClicked(boolean state) {
        parentDataManager.saveCardSwitchCheckedChanged(state, position + 1);
    }

    public void cardExpirationDateClicked() {
        parentDataManager.cardExpirationDateClicked();
    }

    public void setCardSwitchHeight(int cardSwitchHeight) {
        parentDataManager.setCardSwitchHeight(cardSwitchHeight);
    }

    public void filterByCurrency(String currencyCode) {
        data = new ArrayList<>();
        for (PaymentOption paymentOption : dataOriginal) {
            if (paymentOption.getSupported_currencies().contains(currencyCode)) {
                data.add(paymentOption);
            }
        }

        updateData();
    }
}
