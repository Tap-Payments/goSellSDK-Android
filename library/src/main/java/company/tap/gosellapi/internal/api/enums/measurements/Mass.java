package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Mass.
 */
public enum Mass implements MeasurementUnit {


    /**
     * Picograms mass.
     */
    @SerializedName("picograms")    PICOGRAMS,
    /**
     * Nanograms mass.
     */
    @SerializedName("nanograms")    NANOGRAMS,
    /**
     * Micrograms mass.
     */
    @SerializedName("micrograms")   MICROGRAMS,
    /**
     * Milligrams mass.
     */
    @SerializedName("milligrams")   MILLIGRAMS,
    /**
     * Centigrams mass.
     */
    @SerializedName("centigrams")   CENTIGRAMS,
    /**
     * Decigrams mass.
     */
    @SerializedName("decigrams")    DECIGRAMS,
    /**
     * Grams mass.
     */
    @SerializedName("grams")        GRAMS,
    /**
     * Kilograms mass.
     */
    @SerializedName("kilograms")    KILOGRAMS,
    /**
     * Ounces mass.
     */
    @SerializedName("ounces")       OUNCES,
    /**
     * Pounds mass.
     */
    @SerializedName("pounds")       POUNDS,
    /**
     * Stones mass.
     */
    @SerializedName("stones")       STONES,
    /**
     * Metric tons mass.
     */
    @SerializedName("metric_tons")  METRIC_TONS,
    /**
     * Short tons mass.
     */
    @SerializedName("short_tons")   SHORT_TONS,
    /**
     * Carats mass.
     */
    @SerializedName("carats")       CARATS,
    /**
     * Ounces troy mass.
     */
    @SerializedName("ounces_troy")  OUNCES_TROY,
    /**
     * Slugs mass.
     */
    @SerializedName("slugs")        SLUGS;

    @Override
    public Measurement getMeasurementGroup() {

        return Measurement.MASS;
    }
}
