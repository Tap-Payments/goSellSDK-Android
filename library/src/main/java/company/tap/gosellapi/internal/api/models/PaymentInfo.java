package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentInfo {
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
    }
}
