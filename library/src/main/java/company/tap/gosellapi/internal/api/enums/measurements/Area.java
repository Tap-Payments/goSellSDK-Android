package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Area.
 */
public enum Area implements MeasurementUnit {
  /**
   * Haitham update
   * Reorganize Area measurement units alphabetically
   */
    /**
     * A
     */
    @SerializedName("acres")                            ACRES,
    /**
     * Ares area.
     */
    @SerializedName("ares")                             ARES,
    /**
     * B
     */
    @SerializedName("barn")                             BARN,
    /**
     * Bigha area.
     */
    @SerializedName("bigha")                            BIGHA,
    /**
     * Bunder area.
     */
    @SerializedName("bunder")                           BUNDER,
    /**
     * C
     */
    @SerializedName("carucate")                         CARUCATE,
    /**
     * Cawnie area.
     */
    @SerializedName("Cawnie")                           CAWNIE,
    /**
     * Cent area.
     */
    @SerializedName("cent")                             CENT,
    /**
     * Circular mil area.
     */
    @SerializedName("circularmil")                      CIRCULAR_MIL,
    /**
     * Cuerda area.
     */
    @SerializedName("cuerda")                           CUERDA,
    /**
     * D
     */
    @SerializedName("davoch")                           DAVOCH,
    /**
     * Decimal area.
     */
    @SerializedName("decimal")                          DECIMAL,
    /**
     * Dunam area.
     */
    @SerializedName("dunam")                            DUNAM,
    /**
     * F
     */
    @SerializedName("feddan")                           FEDDAN,
    /**
     * G
     */

    @SerializedName("gunta")                            GUNTA,
    /**
     * H
     */
    @SerializedName("hectares")                         HECTARES,
    /**
     * J
     */
    @SerializedName("juchart")                          JUCHART,
    /**
     * K
     */
    @SerializedName("kanal")                            KANAL,
    /**
     * M
     */
    @SerializedName("mansus")                           MANSUS,
    /**
     * Mantal area.
     */
    @SerializedName("mantal")                           MANTAL,
    /**
     * Manzana area.
     */
    @SerializedName("manzana")                          MANZANA,
    /**
     * Morgen area.
     */
    @SerializedName("morgen")                           MORGEN,
    /**
     * S
     */
    @SerializedName("square_megameters")               SQUARE_MEGAMETERS,
    /**
     * Square kilometers area.
     */
    @SerializedName("square_kilometers")               SQUARE_KILOMETERS,
    /**
     * Square meters area.
     */
    @SerializedName("square_meters")                   SQUARE_METERS,
    /**
     * Square centimeters area.
     */
    @SerializedName("square_centimeters")              SQUARE_CENTIMETERS,
    /**
     * Square millimeters area.
     */
    @SerializedName("square_millimeters")              SQUARE_MILLIMETERS,
    /**
     * Square micrometers area.
     */
    @SerializedName("square_micrometers")              SQUARE_MICROMETERS,
    /**
     * Square nanometers area.
     */
    @SerializedName("square_nanometers")               SQUARE_NANOMETERS,
    /**
     * Square inches area.
     */
    @SerializedName("square_inches")                   SQUARE_INCHES,
    /**
     * Square feet area.
     */
    @SerializedName("square_feet")                     SQUARE_FEET,
    /**
     * Square yards area.
     */
    @SerializedName("square_yards")                    SQUARE_YARDS,
    /**
     * Square miles area.
     */
    @SerializedName("square_miles")                    SQUARE_MILES,
    /**
     * T
     */
    @SerializedName("toise")                           TOISE,

    /**
     * V
     */
    @SerializedName("Virgate")                         VIRGATE;


  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.AREA;
  }
}
