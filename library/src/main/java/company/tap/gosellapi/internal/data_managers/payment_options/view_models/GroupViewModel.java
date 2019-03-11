package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import android.util.Log;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.GroupViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Group view model.
 */
public class GroupViewModel extends PaymentOptionViewModel<String, GroupViewHolder, GroupViewModel> {

    /**
     * Instantiates a new Group view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     */
    public GroupViewModel(PaymentOptionsDataManager parentDataManager, String data) {

        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.GROUP);
    }

    public void editItemClicked(GroupViewHolder groupViewHolderListener){
        Log.d("GroupViewModel","editItemClicked......");
        parentDataManager.editItemClicked(groupViewHolderListener);
    }

    public void cancelItemClicked(){
        Log.d("GroupViewModel","cancelItemClicked......");
        parentDataManager.cancelItemClicked();
    }
}
