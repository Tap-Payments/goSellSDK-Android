package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.utils.AmountCalculator;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class PaymentItem {
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("quantity")
    @Expose
    private Quantity quantity;

    @SerializedName("amount_per_unit")
    @Expose
    private double amountPerUnit;

    @SerializedName("discount")
    @Expose
    private AmountModificator discount;

    @SerializedName("taxes")
    @Expose
    private ArrayList<Tax> taxes;

    @SerializedName("total_amount")
    @Expose
    private double total_amount;

    private static class Quantity {
        @SerializedName("value")
        @Expose
        private double value;

        @SerializedName("unit_of_measurement")
        @Expose
        private String unit_of_measurement;

        private Quantity(double value, String unit_of_measurement) {
            this.value = value;
            this.unit_of_measurement = unit_of_measurement;
        }
    }

    /**
     * Constructor with all fields
     */
    public PaymentItem(String name, double value, String unitOfMeasurement, double amountPerUnit) {
        this.name = name;
        this.quantity = new Quantity(value, unitOfMeasurement);
        this.amountPerUnit = amountPerUnit;
    }

    public PaymentItem(String name, String description, Quantity quantity, double amountPerUnit, AmountModificator discount, ArrayList<Tax> taxes) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.amountPerUnit = amountPerUnit;
        this.discount = discount;
        this.taxes = taxes;
    }

    public double getPlainAmount() {

        return this.amountPerUnit * this.quantity.value;
    }

    public double getDiscountAmount() {

        if (this.discount == null) {

            return 0.0;
        }

        switch (this.discount.getType()) {

            case PERCENTAGE:

                return this.getPlainAmount() * this.discount.getNormalizedValue();

            case FIXED:

                return this.discount.getValue();

            default: return 0.0f;
        }
    }

    public double getTaxesAmount() {

        double taxationAmount = this.getPlainAmount() - this.getDiscountAmount();

        return AmountCalculator.calculateTaxesOn(taxationAmount, this.taxes);
    }
}
