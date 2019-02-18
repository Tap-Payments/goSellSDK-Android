package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.AddressFormat;

public class BillingAddressFormat {
    @SerializedName("name")
    @Expose
    AddressFormat name;

    @SerializedName("fields")
    @Expose
    ArrayList<BillingAddressField> fields;

    public AddressFormat getName() {
        return name;
    }

    public ArrayList<BillingAddressField> getFields() {
        return fields;
    }
}
