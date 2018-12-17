package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum ElectricCharge implements MeasurementUnit{

  /**
   * A
   */
  @SerializedName("abcoulomb")                              ABCOULOMB,
  @SerializedName("amperehour")                             AMPER_HOUR,
  /**
   * C
   */
  @SerializedName("chargenumber")                           CHARGE_NUMBER,
  @SerializedName("coulomb")                                COULOMB,
  /**
   * E
   */
  @SerializedName("elementarycharge")                       ELEMENTARY_CHARGE,
  /**
   * F
   */
  @SerializedName("faradayconstant")                        FARADAY_CONSTANT,
  /**
   * P
   */
  @SerializedName("planckcharge")                           PLANK_CHARGE,

  /**
   * S
   */
  @SerializedName("statcoulomb")                           STATCOULOMB;
  ;


  @Override
  public Measurement getMeasurementGroup() {
    return Measurement.ELECTRIC_CHARGE;
  }
}
