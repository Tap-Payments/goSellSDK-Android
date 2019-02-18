package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;

public final class CardCredentialsViewModelData {

    private ArrayList<PaymentOption> paymentOptions;
    private boolean displaysSaveCardSection;

    public CardCredentialsViewModelData(ArrayList<PaymentOption> paymentOptions, boolean displaysSaveCardSection) {

        this.paymentOptions = paymentOptions;
        this.displaysSaveCardSection = displaysSaveCardSection;
    }

    public ArrayList<PaymentOption> getPaymentOptions() {

        return paymentOptions;
    }

    public void setPaymentOptions(ArrayList<PaymentOption> paymentOptions) {

        this.paymentOptions = paymentOptions;
    }

    public boolean displaysSaveCardSection() {

        return displaysSaveCardSection;
    }
}
