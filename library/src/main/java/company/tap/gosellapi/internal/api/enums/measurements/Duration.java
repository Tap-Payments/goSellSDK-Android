package company.tap.gosellapi.internal.api.enums.measurements;

import com.google.gson.annotations.SerializedName;

public enum Duration implements MeasurementUnit {

    @SerializedName("nanosecond")     NANOSECOND,
    @SerializedName("microsecond")    MICROSECOND,
    @SerializedName("seconds")        SECONDS,
    @SerializedName("minutes")        MINUTES,
    @SerializedName("hours")          HOURS,
    @SerializedName("days")           DAYS,
    @SerializedName("weeks")          WEEKS,
    @SerializedName("months")         MONTHS,
    @SerializedName("quarters")       QUARTERS,
    @SerializedName("years")          YEARS;

    @Override
    public Measurement getMeasurementGroup() {
        
        return Measurement.DURATION;
    }
}
