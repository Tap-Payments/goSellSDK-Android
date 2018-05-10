package company.tap.gosellapi.internal.data_managers.payment_options.viewmodels;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.WebPaymentViewHolder;

public class WebPaymentViewModel
        extends PaymentOptionsBaseViewModel<PaymentOption, WebPaymentViewHolder, WebPaymentViewModel> {

    public WebPaymentViewModel(PaymentOption data, int modelType) {
        super(data, modelType);
    }


}
