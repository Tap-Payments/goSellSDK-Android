package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.Shipping;
import company.tap.gosellapi.internal.api.models.Tax;
import company.tap.gosellapi.internal.utils.AmountCalculator;

public final class PaymentOptionsRequest {

    @SerializedName("items")
    @Expose
    private ArrayList<PaymentItem> items;

    @SerializedName("shipping")
    @Expose
    private ArrayList<Shipping> shipping;

    @SerializedName("taxes")
    @Expose
    private ArrayList<Tax> taxes;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("customer")
    @Expose
    private String customerIdentifier;

    @SerializedName("total_amount")
    @Expose
    private double totalAmount;

    public PaymentOptionsRequest(ArrayList<PaymentItem> items, ArrayList<Shipping> shipping, ArrayList<Tax> taxes, String currency, String customerIdentifier) {

        this.items              = items;
        this.shipping           = shipping;
        this.taxes              = taxes;
        this.currency = currency;
        this.customerIdentifier = customerIdentifier;
        this.totalAmount        = AmountCalculator.calculateTotalAmountOf(items, taxes, shipping);
    }
}
