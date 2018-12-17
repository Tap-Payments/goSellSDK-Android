package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum Area implements MeasurementUnit {
  /**
   * Haitham update
   * Reorganize Area measurement units alphabetically
   */
  /**
   * A
   */
  @SerializedName("acres")                            ACRES,
  @SerializedName("ares")                             ARES,
  /**
   * B
   */
  @SerializedName("barn")                             BARN,
  @SerializedName("bigha")                            BIGHA,
  @SerializedName("bunder")                           BUNDER,
  /**
   * C
   */
  @SerializedName("carucate")                         CARUCATE,
  @SerializedName("Cawnie")                           CAWNIE,
  @SerializedName("cent")                             CENT,
  @SerializedName("circularmil")                      CIRCULAR_MIL,
  @SerializedName("cuerda")                           CUERDA,
  /**
   * D
   */
  @SerializedName("davoch")                           DAVOCH,
  @SerializedName("decimal")                          DECIMAL,
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
  @SerializedName("mantal")                           MANTAL,
  @SerializedName("manzana")                          MANZANA,
  @SerializedName("morgen")                           MORGEN,
  /**
   * S
   */
  @SerializedName("square_megameters")               SQUARE_MEGAMETERS,
  @SerializedName("square_kilometers")               SQUARE_KILOMETERS,
  @SerializedName("square_meters")                   SQUARE_METERS,
  @SerializedName("square_centimeters")              SQUARE_CENTIMETERS,
  @SerializedName("square_millimeters")              SQUARE_MILLIMETERS,
  @SerializedName("square_micrometers")              SQUARE_MICROMETERS,
  @SerializedName("square_nanometers")               SQUARE_NANOMETERS,
  @SerializedName("square_inches")                   SQUARE_INCHES,
  @SerializedName("square_feet")                     SQUARE_FEET,
  @SerializedName("square_yards")                    SQUARE_YARDS,
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
