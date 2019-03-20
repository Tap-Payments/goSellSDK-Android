package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Permission.
 */
public enum Permission {

    /**
     * Pci permission.
     */
    @SerializedName("pci")                      PCI,
    /**
     * Merchant checkout permission.
     */
    @SerializedName("merchant_checkout")        MERCHANT_CHECKOUT,
    /**
     * Threedsecure disabled permission.
     */
    @SerializedName("threeDSecure_disabled")    THREEDSECURE_DISABLED
}
