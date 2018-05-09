package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.CardCredentialsViewHolder;

public class CardCredentialsViewModel
        extends PaymentOptionsBaseViewModel<ArrayList<PaymentOption>, CardCredentialsViewHolder> {

    public CardCredentialsViewModel(ArrayList<PaymentOption> data, int modelType) {
        super(data, modelType);
    }


}
