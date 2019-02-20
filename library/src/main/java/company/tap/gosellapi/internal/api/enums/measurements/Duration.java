package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Duration.
 */
public enum Duration implements MeasurementUnit {

    /**
     * Nanosecond duration.
     */
    @SerializedName("nanosecond")     NANOSECOND,
    /**
     * Microsecond duration.
     */
    @SerializedName("microsecond")    MICROSECOND,
    /**
     * Seconds duration.
     */
    @SerializedName("seconds")        SECONDS,
    /**
     * Minutes duration.
     */
    @SerializedName("minutes")        MINUTES,
    /**
     * Hours duration.
     */
    @SerializedName("hours")          HOURS,
    /**
     * Days duration.
     */
    @SerializedName("days")           DAYS,
    /**
     * Weeks duration.
     */
    @SerializedName("weeks")          WEEKS,
    /**
     * Months duration.
     */
    @SerializedName("months")         MONTHS,
    /**
     * Quarters duration.
     */
    @SerializedName("quarters")       QUARTERS,
    /**
     * Years duration.
     */
    @SerializedName("years")          YEARS;

    @Override
    public Measurement getMeasurementGroup() {
        
        return Measurement.DURATION;
    }
}
