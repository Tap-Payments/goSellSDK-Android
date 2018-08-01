package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

public enum Permission {

    @SerializedName("pci") PCI,
    @SerializedName("merchant_checkout") MERCHANT_CHECKOUT,
    @SerializedName("threeDSecure_disabled") THREEDSECURE_DISABLED
}
