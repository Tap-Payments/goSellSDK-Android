package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.measurements.Measurement;
import company.tap.gosellapi.internal.api.enums.measurements.MeasurementUnit;

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

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public double getValue() {
        return value;
    }

    public Quantity(MeasurementUnit measurementUnit, double value) {

        this.measurementGroup = measurementUnit.getMeasurementGroup();
        this.measurementUnit = measurementUnit;
        this.value = value;
    }
}
