package company.tap.gosellapi.internal.data_managers.payment_options.view_models;


import android.support.annotation.NonNull;

import java.util.ArrayList;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.GroupViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;
import company.tap.gosellapi.internal.viewholders.RecentSectionViewHolder;

/**
 * The type Recent section view model.
 */
public class RecentSectionViewModel extends PaymentOptionViewModel<ArrayList<SavedCard>, RecentSectionViewHolder, RecentSectionViewModel> {

  private int recentItemClickedPosition;
  private RecentSectionViewHolder recentSectionViewHolder;

    /**
     * Instantiates a new Recent section view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     */
    public RecentSectionViewModel(PaymentOptionsDataManager parentDataManager,
                                ArrayList<SavedCard> data) {
    super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.SAVED_CARDS);
  }

    /**
     * Recent item clicked.
     *
     * @param recentItemClickedPosition the recent item clicked position
     */
    public void recentItemClicked(int recentItemClickedPosition) {
    this.recentItemClickedPosition = recentItemClickedPosition;
    if (recentItemClickedPosition != Constants.NO_FOCUS)
      parentDataManager.recentPaymentItemClicked(position, data.get(recentItemClickedPosition),this);
    else
      parentDataManager.recentPaymentItemClicked(position, null,this);
  }


  public void setRecentSectionViewHolder(RecentSectionViewHolder recentSectionViewHolder){
      this.recentSectionViewHolder = recentSectionViewHolder;
  }

  public void shakeAllCards(GroupViewHolder groupViewHolderListener) {
      if(this.recentSectionViewHolder!=null)
   this.recentSectionViewHolder.shakeAllCards(groupViewHolderListener);
  }

  public void stopShakingAllCards() {
      if(this.recentSectionViewHolder!=null)
   this.recentSectionViewHolder.stopShakingAllCards();
  }


  public void deleteCard(@NonNull String cardId){
        parentDataManager.deleteCard(cardId);
  }

  public void disablePayButton(){
        parentDataManager.disablePayButton();
  }
    public void disableRecentView(){
        if(this.recentSectionViewHolder!=null)
            this.recentSectionViewHolder.disableRecentView();


    }
    public void EnableRecentView(){
        if(this.recentSectionViewHolder!=null)
            this.recentSectionViewHolder.enableRecentView();

    }

}
