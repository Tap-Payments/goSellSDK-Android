package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;
import company.tap.gosellapi.internal.viewholders.WebPaymentViewHolder;

public class WebPaymentViewModel extends PaymentOptionViewModel<PaymentOption, WebPaymentViewHolder, WebPaymentViewModel> {

    public WebPaymentViewModel(PaymentOptionsDataManager parentDataManager, PaymentOption data) {
        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.WEB);
    }

    public void itemClicked() {
        parentDataManager.webPaymentSystemViewHolderClicked(this, position);
    }
}
