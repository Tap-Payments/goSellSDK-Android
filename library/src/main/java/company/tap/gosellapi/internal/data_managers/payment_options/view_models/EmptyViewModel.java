package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.EmptyViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Empty view model.
 */
public class EmptyViewModel extends PaymentOptionViewModel<EmptyViewModelData, EmptyViewHolder, EmptyViewModel> {

    /**
     * Instantiates a new Empty view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     */
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
