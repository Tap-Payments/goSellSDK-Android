package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Electric current.
 */
public enum  ElectricCurrent implements MeasurementUnit {

    /**
     * Abampere electric current.
     */
    @SerializedName("abampere")                        ABAMPERE,
    /**
     * Milliampere electric current.
     */
    @SerializedName("milliampere")                     MILLIAMPERE,
    /**
     * Amperes electric current.
     */
    @SerializedName("amperes")                         AMPERES,
    /**
     * Kiloampere electric current.
     */
    @SerializedName("kiloampere")                      KILOAMPERE,
    /**
     * Megaampere electric current.
     */
    @SerializedName("megaampere")                      MEGAAMPERE,
    /**
     * Biot electric current.
     */
    @SerializedName("biot")                            BIOT,
    /**
     * Millibiot electric current.
     */
    @SerializedName("millibiot")                       MILLIBIOT,
    /**
     * Kilobiot electric current.
     */
    @SerializedName("kilobiot")                        KILOBIOT,
    /**
     * Megabiot electric current.
     */
    @SerializedName("megabiot")                        MEGABIOT,
    /**
     * Statampere electric current.
     */
    @SerializedName("statampere ")                     STATAMPERE;

  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.ELECTRIC_CURRENT;
  }
}
