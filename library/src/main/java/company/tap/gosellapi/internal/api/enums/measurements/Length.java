package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Length.
 */
public enum  Length implements MeasurementUnit{

    /**
     * Millimeter length.
     */
    @SerializedName("millimeter")        MILLIMETER,

    /**
     * Centimeter length.
     */
    @SerializedName("centimeter")        CENTIMETER,

    /**
     * Meter length.
     */
    @SerializedName("meter")             METER,
  ;
  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.LENGTH;
  }
}
