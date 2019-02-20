package company.tap.gosellapi.internal.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.enums.AddressFormat;
import company.tap.gosellapi.internal.api.models.AddressField;
import company.tap.gosellapi.internal.api.models.BillingAddressFormat;

/**
 * The type Address formats response.
 */
public final class AddressFormatsResponse implements BaseResponse {
    /**
     * The Formats.
     */
    @SerializedName("formats")
    @Expose
    ArrayList<BillingAddressFormat> formats;

    /**
     * The Country formats.
     */
    @SerializedName("country_formats")
    @Expose
    HashMap<String, AddressFormat> countryFormats;

    /**
     * The Fields.
     */
    @SerializedName("fields")
    @Expose
    ArrayList<AddressField> fields;

    /**
     * Gets formats.
     *
     * @return the formats
     */
    public ArrayList<BillingAddressFormat> getFormats() {
        return formats;
    }

    /**
     * Gets country formats.
     *
     * @return the country formats
     */
    public HashMap<String, AddressFormat> getCountryFormats() {
        return countryFormats;
    }

    /**
     * Gets fields.
     *
     * @return the fields
     */
    public ArrayList<AddressField> getFields() {
        return fields;
    }
}
