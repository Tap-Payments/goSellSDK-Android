package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.AddressFormat;

/**
 * The type Billing address format.
 */
public class BillingAddressFormat implements Serializable {
    /**
     * The Name.
     */
    @SerializedName("name")
    @Expose
    AddressFormat name;

    /**
     * The Fields.
     */
    @SerializedName("fields")
    @Expose
    ArrayList<BillingAddressField> fields;

    /**
     * Gets name.
     *
     * @return the name
     */
    public AddressFormat getName() {
        return name;
    }

    /**
     * Gets fields.
     *
     * @return the fields
     */
    public ArrayList<BillingAddressField> getFields() {
        return fields;
    }
}
