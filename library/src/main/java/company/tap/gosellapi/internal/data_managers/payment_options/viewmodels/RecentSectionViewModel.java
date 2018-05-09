package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.RecentSectionViewHolder;

public class RecentSectionViewModel
        extends PaymentOptionsBaseViewModel<ArrayList<Card>, RecentSectionViewHolder> {

    public RecentSectionViewModel(ArrayList<Card> data, int modelType) {
        super(data, modelType);
    }


}
