package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum Mass implements MeasurementUnit {


    @SerializedName("picograms")    PICOGRAMS,
    @SerializedName("nanograms")    NANOGRAMS,
    @SerializedName("micrograms")   MICROGRAMS,
    @SerializedName("milligrams")   MILLIGRAMS,
    @SerializedName("centigrams")   CENTIGRAMS,
    @SerializedName("decigrams")    DECIGRAMS,
    @SerializedName("grams")        GRAMS,
    @SerializedName("kilograms")    KILOGRAMS,
    @SerializedName("ounces")       OUNCES,
    @SerializedName("pounds")       POUNDS,
    @SerializedName("stones")       STONES,
    @SerializedName("metric_tons")  METRIC_TONS,
    @SerializedName("short_tons")   SHORT_TONS,
    @SerializedName("carats")       CARATS,
    @SerializedName("ounces_troy")  OUNCES_TROY,
    @SerializedName("slugs")        SLUGS;

    @Override
    public Measurement getMeasurementGroup() {

        return Measurement.MASS;
    }
}
