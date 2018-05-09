package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import java.util.HashMap;

import company.tap.gosellapi.internal.viewholders_and_viewmodels.CurrencyViewHolder;

public class CurrencyViewModel
        extends PaymentOptionsBaseViewModel<HashMap<String, Double>, CurrencyViewHolder> {

    public CurrencyViewModel(HashMap<String, Double> data, int modelType) {
        super(data, modelType);
    }


}
