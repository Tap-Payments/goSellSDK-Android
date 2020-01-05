package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import company.tap.gosellapi.internal.api.enums.AddressFieldInputType;

/**
 * The type Address field.
 */
public final class AddressField implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private AddressFieldInputType type;

    @SerializedName("place_holder")
    @Expose
    private String placeholder;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AddressFieldInputType getType() {
        return type;
    }

    /**
     * Gets placeholder.
     *
     * @return the placeholder
     */
    public String getPlaceholder() {
        return placeholder;
    }
}
