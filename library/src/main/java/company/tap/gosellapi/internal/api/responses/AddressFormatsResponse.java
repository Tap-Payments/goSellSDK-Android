package company.tap.gosellapi.internal.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.AddressFormat;
import company.tap.gosellapi.internal.api.models.AddressField;
import company.tap.gosellapi.internal.api.models.BillingAddressFormat;

public class AddressFormatsResponse implements BaseResponse {
    @SerializedName("formats")
    @Expose
    ArrayList<BillingAddressFormat> formats;

    @SerializedName("country_formats")
    @Expose
    HashMap<String, AddressFormat> countryFormats;

    @SerializedName("fields")
    @Expose
    ArrayList<AddressField> fields;

    public ArrayList<BillingAddressFormat> getFormats() {
        return formats;
    }

    public HashMap<String, AddressFormat> getCountryFormats() {
        return countryFormats;
    }

    public ArrayList<AddressField> getFields() {
        return fields;
    }
}
