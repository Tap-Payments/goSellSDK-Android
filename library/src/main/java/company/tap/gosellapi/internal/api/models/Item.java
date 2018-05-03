package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 27.04.2018.
 * <br>
 * Model for Customer object
 */

public final class Item {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("quantity")
    @Expose
    private Quantity quantity;

    @SerializedName("amount")
    @Expose
    private double amount;

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
    public Item(String name, double value, String unitOfMeasurement, double amount) {
        this.name = name;
        this.quantity = new Quantity(value, unitOfMeasurement);
        this.amount = amount;

        this.total_amount = value * amount;
    }

    double getTotal_amount() {
        return total_amount;
    }
}
