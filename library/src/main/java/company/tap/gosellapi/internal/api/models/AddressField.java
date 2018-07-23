package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AddressFieldInputType;

public class AddressField {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("type")
    @Expose
    AddressFieldInputType type;

    @SerializedName("place_holder")
    @Expose
    String placeholder;

    public String getName() {
        return name;
    }

    public AddressFieldInputType getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
