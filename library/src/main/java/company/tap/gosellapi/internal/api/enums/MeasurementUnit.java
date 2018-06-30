package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum MeasurementUnit {
    @SerializedName("kilograms") KILOGRAMS,
    @SerializedName("grams") GRAMS,
    @SerializedName("decigrams") DECIGRAMS,
    @SerializedName("centigrams") CENTIGRAMS,
    @SerializedName("milligrams") MILLIGRAMS,
    @SerializedName("micrograms") MICROGRAMS,
    @SerializedName("nanograms") NANOGRAMS,
    @SerializedName("picograms") PICOGRAMS,
    @SerializedName("ounces") OUNCES,
    @SerializedName("pounds") POUNDS,
    @SerializedName("stones") STONES,
    @SerializedName("metric_tons") METRIC_TONS,
    @SerializedName("short_tons") SHORT_TONS,
    @SerializedName("carats") CARATS,
    @SerializedName("ounces_troy") OUNCES_TROY,
    @SerializedName("slugs") SLUGS
}
