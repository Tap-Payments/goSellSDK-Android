package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingAddressField {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("required")
    @Expose
    boolean required;

    @SerializedName("order_by")
    @Expose
    int orderBy;

    @SerializedName("display_order")
    @Expose
    int displayOrder;

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }
}
