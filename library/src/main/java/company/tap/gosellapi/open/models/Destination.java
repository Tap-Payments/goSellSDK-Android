package company.tap.gosellapi.open.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * The Destination array contains list of Merchant desired destinations accounts to receive money from payment transactions
 */
public class Destination {

    @SerializedName("id")
    @Expose
    private String id;                              // Destination unique identifier (Required)

    @SerializedName("amount")
    @Expose
    private BigDecimal amount;                      // Amount to be transferred to the destination account (Required)

    @SerializedName("currency")
    @Expose
    private String currency;                   // Currency code (three digit ISO format) (Required)

    @SerializedName("description")
    @Expose
    private String description;                    //  Description about the transfer (Optional)

    @SerializedName("reference")
    @Expose
    private String reference;                     //   Merchant reference number to the destination (Optional)


    /**
     * Create an instance of destination
     * @param id
     * @param amount
     * @param currency
     * @param description
     * @param reference
     */
    public Destination(String id, BigDecimal amount, TapCurrency currency,String description,String reference) {
        this.id = id;
        this.amount = amount;
        this.currency = (currency!=null)? currency.getIsoCode().toUpperCase():null;
        this.description =description;
        this.reference = reference;
    }

    /**
     *
     * @return Destination unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Amount to be transferred to the destination account
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *
     * @return Currency code (three digit ISO format)
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @return Description about the transfer
     */
    public String getDescription() {
        return description;
    }

    /**
     *  Merchant reference number to the destination
     * @return
     */
    public String getReference() {
        return reference;
    }
}
