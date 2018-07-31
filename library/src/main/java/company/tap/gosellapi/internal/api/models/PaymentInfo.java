package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentInfo {

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;

    @SerializedName("shipping")
    @Expose
    private ArrayList<ShippingCell> shipping;

    @SerializedName("taxes")
    @Expose
    private ArrayList<Tax> taxes;

    @SerializedName("currency")
    @Expose
    private String currencyCode;

    @SerializedName("customer")
    @Expose
    private String customerIdentifier;

    @SerializedName("total_amount")
    @Expose
    private double total_amount;

    public PaymentInfo(String currencyCode, String customerIdentifier, ArrayList<Tax> taxes, ArrayList<Item> items, ArrayList<ShippingCell> shipping) {
        this.currencyCode = currencyCode;
        this.customerIdentifier = customerIdentifier;
        this.items = items;
        this.shipping = shipping;
        this.taxes = taxes;

        calculateTotalAmount();
    }

    public PaymentInfo(ArrayList<Item> items, String currencyCode, double total_amount) {
        this.items = items;
        this.currencyCode = currencyCode;
        this.total_amount = total_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public String getCustomerIdentifier() {
        return customerIdentifier;
    }

    private void calculateTotalAmount() {
        for (Item item : items) {
            total_amount += item.getTotal_amount();
        }
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
