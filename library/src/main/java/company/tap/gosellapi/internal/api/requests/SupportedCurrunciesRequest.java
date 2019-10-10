package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.From;
import company.tap.gosellapi.internal.api.models.To;


/**
 * Created by h.sheshtawy on 8.10.2019.
 * <br>
 * Model for {@link SupportedCurrunciesRequest} object
 */

/**
 * The Conversion Currency request.
 */

public final class SupportedCurrunciesRequest {

    @SerializedName("from")
    @Expose
    private From from;

    @SerializedName("to")
    @Expose
    private ArrayList<To> to;

    @SerializedName("by")
    @Expose
    private String by;


    /**
     * Instantiates a new Payment options request.
     *
     * @param from source currency
     * @param to   destination currencies
     */
    public SupportedCurrunciesRequest(@NotNull From from,
                                      @NotNull ArrayList<To> to,
                                      @NotNull String by) {
        this.from = from;
        this.to = to;
        this.by = by;
    }

}

