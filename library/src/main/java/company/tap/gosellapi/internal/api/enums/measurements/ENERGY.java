package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum ENERGY implements MeasurementUnit {
  /**
   * Joule
   */
  @SerializedName("joule")                        JOULE,
  @SerializedName("millijoule")                   MILLIJOULE,
  @SerializedName("kilojoule")                    KILOJOULE,
  @SerializedName("megajoule")                    MEGAJOULE,
  @SerializedName("quad")                         QUAD,
  @SerializedName("thermie")                      THERMIE,

  /**
   * Calorie
   */

  @SerializedName("kilocalorie")                   KILOCALORIE;

  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.ENERGY;
  }
}
