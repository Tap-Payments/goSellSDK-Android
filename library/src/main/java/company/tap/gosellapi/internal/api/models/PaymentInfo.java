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
    private CustomerInfo customer;

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;

    @SerializedName("shipping")
    @Expose
    private ArrayList<ShippingCell> shipping;

//    public PaymentInfo(String currency_code, Customer customer, ArrayList<Item> items, ArrayList<ShippingCell> shipping) {
//        this.currency_code = currency_code;
//        // TODO: 26.05.2018 now this is disabled cause backend won't give currency list if customer is passed, need to change later
//        this.customer = customer;
//        this.items = items;
//        this.shipping = shipping;
//
//        calculateTotalAmount();
//    }

    public PaymentInfo(String currency_code, CustomerInfo customer, ArrayList<Item> items, ArrayList<ShippingCell> shipping) {
        this.currency_code = currency_code;
        this.customer = customer;
        this.items = items;
        this.shipping = shipping;

        calculateTotalAmount();
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    private void calculateTotalAmount() {
        for (Item item : items) {
            total_amount += item.getTotal_amount();
        }
    }
}
