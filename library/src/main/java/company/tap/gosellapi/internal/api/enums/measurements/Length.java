package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum  Length implements MeasurementUnit{

  @SerializedName("millimeter")        MILLIMETER,

  @SerializedName("centimeter")        CENTIMETER,

  @SerializedName("meter")             METER,
  ;
  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.LENGTH;
  }
}
