package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import java.util.ArrayList;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;
import company.tap.gosellapi.internal.viewholders.RecentSectionViewHolder;

public class RecentSectionViewModel extends PaymentOptionViewModel<ArrayList<SavedCard>, RecentSectionViewHolder, RecentSectionViewModel> {

  private int recentItemClickedPosition;

  public RecentSectionViewModel(PaymentOptionsDataManager parentDataManager,
                                ArrayList<SavedCard> data) {

    super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.SAVED_CARDS);
  }

  public void recentItemClicked(int recentItemClickedPosition) {
    this.recentItemClickedPosition = recentItemClickedPosition;
    if (recentItemClickedPosition != Constants.NO_FOCUS)
      parentDataManager.recentPaymentItemClicked(position, data.get(recentItemClickedPosition),this);
    else
      parentDataManager.recentPaymentItemClicked(position, null,this);
  }



}
