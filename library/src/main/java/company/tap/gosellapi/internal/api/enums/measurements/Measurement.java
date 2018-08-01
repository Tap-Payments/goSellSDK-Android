package company.tap.gosellapi.internal.api.enums.measurements;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.SerializedName;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public enum Measurement {

    @SerializedName("area")             AREA,
    @SerializedName("duration")         DURATION,
    @SerializedName("electric_charge")  ELECTRIC_CHARGE,
    @SerializedName("electric_current") ELECTRIC_CURRENT,
    @SerializedName("energy")           ENERGY,
    @SerializedName("length")           LENGTH,
    @SerializedName("mass")             MASS,
    @SerializedName("power")            POWER,
    @SerializedName("volume")           VOLUME,
    @SerializedName("units")            UNITS
}
