package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Billing address field.
 */
public class BillingAddressField implements Serializable {

    /**
     * The Name.
     */
    @SerializedName("name")
    @Expose
    String name;

    /**
     * The Required.
     */
    @SerializedName("required")
    @Expose
    boolean required;

    /**
     * The Order by.
     */
    @SerializedName("order_by")
    @Expose
    int orderBy;

    /**
     * The Display order.
     */
    @SerializedName("display_order")
    @Expose
    int displayOrder;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Is required boolean.
     *
     * @return the boolean
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Gets order by.
     *
     * @return the order by
     */
    public int getOrderBy() {
        return orderBy;
    }

    /**
     * Gets display order.
     *
     * @return the display order
     */
    public int getDisplayOrder() {
        return displayOrder;
    }
}
