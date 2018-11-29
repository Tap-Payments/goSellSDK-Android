package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.internal.utils.AmountCalculator;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Payment Item object
 */

public final class PaymentItem {

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("description")
    @Expose
    @Nullable private String description;

    @SerializedName("quantity")
    @Expose
    private Quantity quantity;

    @SerializedName("amount_per_unit")
    @Expose
    private BigDecimal amountPerUnit;

    @SerializedName("discount")
    @Expose
    @Nullable private AmountModificator discount;

    @SerializedName("taxes")
    @Expose
    @Nullable private ArrayList<Tax> taxes;

    @SerializedName("total_amount")
    @Expose
    private BigDecimal totalAmount;

    public PaymentItem(String name, Quantity quantity, BigDecimal amountPerUnit) {

        this(name, null, quantity, amountPerUnit);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, BigDecimal amountPerUnit) {

        this(name, description, quantity, amountPerUnit,null);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, BigDecimal amountPerUnit, @Nullable AmountModificator discount) {

        this(name, description, quantity, amountPerUnit, discount, null);
    }

    public PaymentItem(String name, @Nullable String description, Quantity quantity, BigDecimal amountPerUnit, @Nullable AmountModificator discount, @Nullable ArrayList<Tax> taxes) {

        this.name           = name;
        this.description    = description;
        this.quantity       = quantity;
        this.amountPerUnit  = amountPerUnit;
        this.discount       = discount;
        this.taxes          = taxes;
        this.totalAmount    = AmountCalculator.calculateTotalAmountOf(this);
    }

    public BigDecimal getAmountPerUnit() {

        return amountPerUnit;
    }

    public Quantity getQuantity() {

        return quantity;
    }

    public AmountModificator getDiscount() {

        return discount;
    }

    public BigDecimal getPlainAmount() {

        return this.getAmountPerUnit().multiply(this.getQuantity().getValue());
    }

    public BigDecimal getDiscountAmount() {

        if (this.getDiscount() == null) {

            return BigDecimal.ZERO;
        }

        switch (this.getDiscount().getType()) {

            case PERCENTAGE:

                return this.getPlainAmount().multiply(this.getDiscount().getNormalizedValue());

            case FIXED:

                return this.getDiscount().getValue();

            default: return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTaxesAmount() {

        BigDecimal taxationAmount = this.getPlainAmount().subtract(this.getDiscountAmount());

        return AmountCalculator.calculateTaxesOn(taxationAmount, this.taxes);
    }
}
