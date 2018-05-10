package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.data_managers.payment_options.EmptyType;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.EmptyViewHolder;

public class EmptyViewModel
        extends PaymentOptionsBaseViewModel<EmptyType, EmptyViewHolder, EmptyViewModel> {

    EmptyViewModel(EmptyType data, int modelType) {
        super(data, modelType);
    }
}
