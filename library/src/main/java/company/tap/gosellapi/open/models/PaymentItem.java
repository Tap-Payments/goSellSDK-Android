package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.internal.utils.AmountCalculator;

/**
 * The type Payment item.
 */
public class PaymentItem {

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

  /**
   * Constructor is private to prevent access from client app, it must be through inner Builder class only
   */
  private PaymentItem(String name,
                      @Nullable String description,
                      Quantity quantity,
                      BigDecimal amountPerUnit,
                      @Nullable AmountModificator discount,
                      @Nullable ArrayList<Tax> taxes) {

    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.amountPerUnit = amountPerUnit;
    this.discount = discount;
    this.taxes = taxes;
    this.totalAmount = AmountCalculator.calculateTotalAmountOf(this);
  }

    /**
     * Gets amount per unit.
     *
     * @return the amount per unit
     */
    public BigDecimal getAmountPerUnit() {
    return this.amountPerUnit;
  }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Quantity getQuantity() {
    return this.quantity;
  }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public AmountModificator getDiscount() {
    return this.discount;
  }

    /**
     * Gets plain amount.
     *
     * @return the plain amount
     */
    public BigDecimal getPlainAmount() {
    return this.getAmountPerUnit().multiply(this.getQuantity().getValue());
  }

    /**
     * Gets discount amount.
     *
     * @return the discount amount
     */
    public BigDecimal getDiscountAmount() {

    if (this.getDiscount() == null) {

      return BigDecimal.ZERO;
    }

    switch (this.getDiscount().getType()) {

      case PERCENTAGE:

        return this.getPlainAmount().multiply(this.getDiscount().getNormalizedValue());

      case FIXED:

        return this.getDiscount().getValue();

      default:
        return BigDecimal.ZERO;
    }
  }

    /**
     * Gets taxes amount.
     *
     * @return the taxes amount
     */
    public BigDecimal getTaxesAmount() {

    BigDecimal taxationAmount = this.getPlainAmount().subtract(this.getDiscountAmount());

    return AmountCalculator.calculateTaxesOn(taxationAmount, this.taxes);
  }

    /**
     * The type Payment item builder.
     */
////////////////////////// ############################ Start of Builder Region ########################### ///////////////////////
  public static class PaymentItemBuilder {

    private String nestedName;
    @Nullable private String nestedDescription;
    private Quantity nestedQuantity;
    private BigDecimal nestedAmountPerUnit;
    @Nullable private AmountModificator nestedDiscount;
    @Nullable private ArrayList<Tax> nestedTaxes;
    private BigDecimal nestedTotalAmount;

        /**
         * public constructor with only required data
         *
         * @param name          the name
         * @param quantity      the quantity
         * @param amountPerUnit the amount per unit
         */
        public PaymentItemBuilder(String name,
                              Quantity quantity,
                              BigDecimal amountPerUnit) {
      this.nestedName = name;
      this.nestedQuantity = quantity;
      this.nestedAmountPerUnit = amountPerUnit;
    }

        /**
         * Description payment item builder.
         *
         * @param innerDescription the inner description
         * @return the payment item builder
         */
        public PaymentItemBuilder description(String innerDescription) {
      this.nestedDescription = innerDescription;
      return this;
    }

        /**
         * Discount payment item builder.
         *
         * @param innerDiscount the inner discount
         * @return the payment item builder
         */
        public PaymentItemBuilder discount(AmountModificator innerDiscount) {
      this.nestedDiscount = innerDiscount;
      return this;
    }

        /**
         * Taxes payment item builder.
         *
         * @param innerTaxes the inner taxes
         * @return the payment item builder
         */
        public PaymentItemBuilder taxes(ArrayList<Tax> innerTaxes) {
      this.nestedTaxes = innerTaxes;
      return this;
    }

        /**
         * Total amount payment item builder.
         *
         * @param innerTotalAmount the inner total amount
         * @return the payment item builder
         */
        public PaymentItemBuilder totalAmount(BigDecimal innerTotalAmount) {
      this.nestedTotalAmount = innerTotalAmount;
      return this;
    }

        /**
         * Build payment item.
         *
         * @return the payment item
         */
        public PaymentItem build() {
      return new PaymentItem(nestedName, nestedDescription, nestedQuantity, nestedAmountPerUnit,
          nestedDiscount, nestedTaxes);
    }
  }
  ////////////////////////// ############################ End of Builder Region ########################### ///////////////////////
}
