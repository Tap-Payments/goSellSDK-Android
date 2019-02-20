package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CurrencyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.CurrencyViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Currency view model.
 */
public class CurrencyViewModel extends PaymentOptionViewModel<CurrencyViewModelData, CurrencyViewHolder, CurrencyViewModel> {

    /**
     * Instantiates a new Currency view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     */
    public CurrencyViewModel(PaymentOptionsDataManager parentDataManager, CurrencyViewModelData data) {

        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.CURRENCY);
    }

    /**
     * Holder clicked.
     */
    public void holderClicked() {
        System.out.println(" Currency ViewModel >>  parentDataManager :"+parentDataManager + " >> position :"+position);
        parentDataManager.currencyHolderClicked(position);
    }
}
