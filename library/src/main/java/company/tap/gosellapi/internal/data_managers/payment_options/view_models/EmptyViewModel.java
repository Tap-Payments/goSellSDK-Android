package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.EmptyViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

public class EmptyViewModel extends PaymentOptionViewModel<EmptyViewModelData, EmptyViewHolder, EmptyViewModel> {

    public EmptyViewModel(PaymentOptionsDataManager parentDataManager, EmptyViewModelData data) {

        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.EMPTY);
    }

    @Override
    public void setData(EmptyViewModelData data) {

        boolean heightChanged = false;

        if ( this.data != data ) {

            if ( this.data.getContentHeight() != data.getContentHeight() ) {

                heightChanged = true;
            }

            super.setData(data);
        }

        if ( heightChanged ) {

            updateData();
        }
    }
}
