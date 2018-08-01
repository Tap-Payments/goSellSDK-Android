package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum Area implements MeasurementUnit {

    @SerializedName("square_megameters")    SQUARE_MEGAMETERS,
    @SerializedName("square_kilometers")    SQUARE_KILOMETERS,
    @SerializedName("square_meters")        SQUARE_METERS,
    @SerializedName("square_centimeters")   SQUARE_CENTIMETERS,
    @SerializedName("square_millimeters")   SQUARE_MILLIMETERS,
    @SerializedName("square_micrometers")   SQUARE_MICROMETERS,
    @SerializedName("square_nanometers")    SQUARE_NANOMETERS,
    @SerializedName("square_inches")        SQUARE_INCHES,
    @SerializedName("square_feet")          SQUARE_FEET,
    @SerializedName("square_yards")         SQUARE_YARDS,
    @SerializedName("square_miles")         SQUARE_MILES,
    @SerializedName("acres")                ACRES,
    @SerializedName("ares")                 ARES,
    @SerializedName("hectares")             HECTARES;

    @Override
    public Measurement getMeasurementGroup() {

        return Measurement.AREA;
    }
}
