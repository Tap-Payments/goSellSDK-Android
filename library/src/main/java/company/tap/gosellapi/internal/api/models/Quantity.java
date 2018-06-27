package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.Measurement;
import company.tap.gosellapi.internal.api.enums.MeasurementUnit;

public class Quantity {
    @SerializedName("measurement_group")
    @Expose
    private Measurement measurementGroup;

    @SerializedName("measurement_unit")
    @Expose
    private MeasurementUnit measurementUnit;

    @SerializedName("value")
    @Expose
    private double value;

    public Measurement getMeasurementGroup() {
        return measurementGroup;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public double getValue() {
        return value;
    }
}
