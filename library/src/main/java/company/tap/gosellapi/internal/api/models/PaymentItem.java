package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

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

    public PaymentItem(String name, Quantity quantity, double amountPerUnit) {

        this(name, null, quantity, amountPerUnit);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, double amountPerUnit) {

        this(name, description, quantity, amountPerUnit,null);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, double amountPerUnit, @Nullable AmountModificator discount) {

        this(name, description, quantity, amountPerUnit, discount, null);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, double amountPerUnit, @Nullable AmountModificator discount, @Nullable ArrayList<Tax> taxes) {

        this.name           = name;
        this.description    = description;
        this.quantity       = quantity;
        this.amountPerUnit  = amountPerUnit;
        this.discount       = discount;
        this.taxes          = taxes;
    }

    public double getAmountPerUnit() {

        return amountPerUnit;
    }

    public Quantity getQuantity() {

        return quantity;
    }

    public AmountModificator getDiscount() {

        return discount;
    }

    public double getPlainAmount() {

        return this.getAmountPerUnit() * this.getQuantity().getValue();
    }

    public double getDiscountAmount() {

        if (this.getDiscount() == null) {

            return 0.0;
        }

        switch (this.getDiscount().getType()) {

            case PERCENTAGE:

                return this.getPlainAmount() * this.getDiscount().getNormalizedValue();

            case FIXED:

                return this.getDiscount().getValue();

            default: return 0.0f;
        }
    }

    public double getTaxesAmount() {

        double taxationAmount = this.getPlainAmount() - this.getDiscountAmount();

        return AmountCalculator.calculateTaxesOn(taxationAmount, this.taxes);
    }
}
