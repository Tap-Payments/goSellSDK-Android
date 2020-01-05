package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

import company.tap.gosellapi.internal.api.enums.measurements.Measurement;
import company.tap.gosellapi.internal.api.enums.measurements.MeasurementUnit;

/**
 * The type Quantity.
 */
public final class Quantity implements Serializable {
    @SerializedName("measurement_group")
    @Expose
    private Measurement measurementGroup;

    @SerializedName("measurement_unit")
    @Expose
    private MeasurementUnit measurementUnit;

    @SerializedName("value")
    @Expose
    private BigDecimal value;

    /**
     * Gets measurement unit.
     *
     * @return the measurement unit
     */
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Instantiates a new Quantity.
     *
     * @param measurementUnit the measurement unit
     * @param value           the value
     */
    public Quantity(MeasurementUnit measurementUnit, BigDecimal value) {

        this.measurementGroup = measurementUnit.getMeasurementGroup();
        this.measurementUnit = measurementUnit;
        this.value = value;
    }
}
