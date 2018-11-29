package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.GroupViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

public class GroupViewModel extends PaymentOptionViewModel<String, GroupViewHolder, GroupViewModel> {

    public GroupViewModel(PaymentOptionsDataManager parentDataManager, String data) {

        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.GROUP);
    }
}
