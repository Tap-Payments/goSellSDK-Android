package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import company.tap.gosellapi.internal.api.enums.measurements.Measurement;
import company.tap.gosellapi.internal.api.enums.measurements.MeasurementUnit;

public final class Quantity {
    @SerializedName("measurement_group")
    @Expose
    private Measurement measurementGroup;

    @SerializedName("measurement_unit")
    @Expose
    private MeasurementUnit measurementUnit;

    @SerializedName("value")
    @Expose
    private BigDecimal value;

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Quantity(MeasurementUnit measurementUnit, BigDecimal value) {

        this.measurementGroup = measurementUnit.getMeasurementGroup();
        this.measurementUnit = measurementUnit;
        this.value = value;
    }
}
