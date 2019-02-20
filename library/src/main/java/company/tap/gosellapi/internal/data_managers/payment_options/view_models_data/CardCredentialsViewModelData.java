package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentOption;

/**
 * The type Card credentials view model data.
 */
public final class CardCredentialsViewModelData {

    private ArrayList<PaymentOption> paymentOptions;
    private boolean displaysSaveCardSection;

    /**
     * Instantiates a new Card credentials view model data.
     *
     * @param paymentOptions          the payment options
     * @param displaysSaveCardSection the displays save card section
     */
    public CardCredentialsViewModelData(ArrayList<PaymentOption> paymentOptions, boolean displaysSaveCardSection) {

        this.paymentOptions = paymentOptions;
        this.displaysSaveCardSection = displaysSaveCardSection;
    }

    /**
     * Gets payment options.
     *
     * @return the payment options
     */
    public ArrayList<PaymentOption> getPaymentOptions() {

        return paymentOptions;
    }

    /**
     * Sets payment options.
     *
     * @param paymentOptions the payment options
     */
    public void setPaymentOptions(ArrayList<PaymentOption> paymentOptions) {

        this.paymentOptions = paymentOptions;
    }

    /**
     * Displays save card section boolean.
     *
     * @return the boolean
     */
    public boolean displaysSaveCardSection() {

        return displaysSaveCardSection;
    }
}
