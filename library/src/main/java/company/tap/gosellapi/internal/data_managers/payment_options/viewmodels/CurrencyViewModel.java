package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.CurrencySectionData;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.CurrencyViewHolder;

public class CurrencyViewModel
        extends PaymentOptionsBaseViewModel<CurrencySectionData, CurrencyViewHolder, CurrencyViewModel> {
    public CurrencyViewModel(PaymentOptionsDataManager parentDataManager, CurrencySectionData data, int modelType) {
        super(parentDataManager, data, modelType);
    }

    public void holderClicked() {
        parentDataManager.currencyHolderClicked(position, data.getData());
    }
}
