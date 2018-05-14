package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.EmptyViewHolder;

public class EmptyViewModel
        extends PaymentOptionsBaseViewModel<EmptyType, EmptyViewHolder, EmptyViewModel> {

    EmptyViewModel(PaymentOptionsDataManager parentDataManager, EmptyType data, int modelType) {
        super(parentDataManager, data, modelType);
    }
}
