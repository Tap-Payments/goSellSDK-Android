package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum  ElectricCurrent implements MeasurementUnit {

  @SerializedName("abampere")                        ABAMPERE,
  @SerializedName("milliampere")                     MILLIAMPERE,
  @SerializedName("amperes")                         AMPERES,
  @SerializedName("kiloampere")                      KILOAMPERE,
  @SerializedName("megaampere")                      MEGAAMPERE,
  @SerializedName("biot")                            BIOT,
  @SerializedName("millibiot")                       MILLIBIOT,
  @SerializedName("kilobiot")                        KILOBIOT,
  @SerializedName("megabiot")                        MEGABIOT,
  @SerializedName("statampere ")                     STATAMPERE;

  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.ELECTRIC_CURRENT;
  }
}
