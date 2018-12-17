package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountModificator;
import company.tap.gosellapi.internal.api.models.Quantity;
import company.tap.gosellapi.internal.utils.AmountCalculator;

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

  public BigDecimal getAmountPerUnit() {
    return this.amountPerUnit;
  }

  public Quantity getQuantity() {
    return this.quantity;
  }

  public AmountModificator getDiscount() {
    return this.discount;
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

      default:
        return BigDecimal.ZERO;
    }
  }

  public BigDecimal getTaxesAmount() {

    BigDecimal taxationAmount = this.getPlainAmount().subtract(this.getDiscountAmount());

    return AmountCalculator.calculateTaxesOn(taxationAmount, this.taxes);
  }
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
     */
    public PaymentItemBuilder(String innerName,
                              Quantity innerQuantity,
                              BigDecimal innerAmountPerUnit) {
      this.nestedName = innerName;
      this.nestedQuantity = innerQuantity;
      this.nestedAmountPerUnit = innerAmountPerUnit;
    }

    public PaymentItemBuilder description(String innerDescription) {
      this.nestedDescription = innerDescription;
      return this;
    }

    public PaymentItemBuilder discount(AmountModificator innerDiscount) {
      this.nestedDiscount = innerDiscount;
      return this;
    }

    public PaymentItemBuilder taxes(ArrayList<Tax> innerTaxes) {
      this.nestedTaxes = innerTaxes;
      return this;
    }

    public PaymentItemBuilder totalAmount(BigDecimal innerTotalAmount) {
      this.nestedTotalAmount = innerTotalAmount;
      return this;
    }

    public PaymentItem build() {
      return new PaymentItem(nestedName, nestedDescription, nestedQuantity, nestedAmountPerUnit,
          nestedDiscount, nestedTaxes);
    }
  }
  ////////////////////////// ############################ End of Builder Region ########################### ///////////////////////
}
