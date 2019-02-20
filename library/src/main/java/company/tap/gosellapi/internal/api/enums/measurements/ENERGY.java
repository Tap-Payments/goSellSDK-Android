package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Energy.
 */
public enum ENERGY implements MeasurementUnit {
    /**
     * Joule
     */
    @SerializedName("joule")                        JOULE,
    /**
     * Millijoule energy.
     */
    @SerializedName("millijoule")                   MILLIJOULE,
    /**
     * Kilojoule energy.
     */
    @SerializedName("kilojoule")                    KILOJOULE,
    /**
     * Megajoule energy.
     */
    @SerializedName("megajoule")                    MEGAJOULE,
    /**
     * Quad energy.
     */
    @SerializedName("quad")                         QUAD,
    /**
     * Thermie energy.
     */
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
