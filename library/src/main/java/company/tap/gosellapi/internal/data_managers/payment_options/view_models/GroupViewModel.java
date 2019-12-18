package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.GroupViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Group view model.
 */
public class GroupViewModel extends PaymentOptionViewModel<String, GroupViewHolder, GroupViewModel> {
    private GroupViewHolder groupViewHolder;
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
        parentDataManager.editItemClicked(groupViewHolderListener);
    }

    public void cancelItemClicked(){
        parentDataManager.cancelItemClicked();
    }
    public void setGroupViewHolder(GroupViewHolder groupViewHolder){
        this.groupViewHolder = groupViewHolder;
    }
}
