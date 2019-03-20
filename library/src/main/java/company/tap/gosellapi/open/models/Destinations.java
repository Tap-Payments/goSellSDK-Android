package company.tap.gosellapi.open.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Destinations  implements Serializable {
    @SerializedName("amount")
    @Expose
    private BigDecimal             amount;

    @SerializedName("currency")
    @Expose
    private String                currency;

    @SerializedName("count")
    @Expose
    private int                     count;

    @SerializedName("destination")
    @Expose
    private ArrayList<Destination>  destination;

    /**
     * @param amount
     * @param currency
     * @param count
     * @param destinations
     */
    public Destinations(@NonNull  BigDecimal amount, @NonNull TapCurrency currency, int count, @NonNull ArrayList<Destination> destinations)
    {
        this.amount = amount;
        this.currency = (currency!=null)? currency.getIsoCode().toUpperCase():null;
        this.count = count;
        this.destination = destinations;
    }

    /**
     * Total amount, transferred to the destination account
     * @return
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Tap transfer currency code
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Total number of destinations transfer involved
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * List of destinations object
     * @return
     */
    public ArrayList<Destination> getDestination() {
        return destination;
    }
}
