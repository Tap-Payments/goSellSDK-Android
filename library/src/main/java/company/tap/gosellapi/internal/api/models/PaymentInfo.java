package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentInfo {
    @SerializedName("total_amount")
    @Expose
    private double total_amount;

    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;

    public PaymentInfo(String currency_code, Customer customer, ArrayList<Item> items) {
        this.currency_code = currency_code;
        this.customer = customer;
        this.items = items;

        calculateTotalAmount();
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    private void calculateTotalAmount() {
        for (Item item : items) {
            total_amount += item.getTotal_amount();
        }
    }
}
