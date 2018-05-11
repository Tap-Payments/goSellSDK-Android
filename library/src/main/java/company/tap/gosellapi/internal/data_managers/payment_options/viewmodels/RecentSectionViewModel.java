package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.RecentSectionViewHolder;

public class RecentSectionViewModel
        extends PaymentOptionsBaseViewModel<ArrayList<Card>, RecentSectionViewHolder, RecentSectionViewModel> {
    private int recentItemClickedPosition;

    public RecentSectionViewModel(PaymentOptionsDataManager parentDataManager, ArrayList<Card> data, int modelType) {
        super(parentDataManager, data, modelType);
    }

    public void recentItemClicked(int recentItemClickedPosition) {
        this.recentItemClickedPosition = recentItemClickedPosition;
        parentDataManager.recentPaymentItemClicked(position, data.get(recentItemClickedPosition));
    }
}
