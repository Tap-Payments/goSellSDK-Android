package company.tap.gosellapi.internal.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Order.
 */
public final class Order implements Serializable {

    @SerializedName("id")
    @Expose
    @Nullable private String id;
    @SerializedName("reference")
    @Expose
    @Nullable private String reference;
    @SerializedName("store_url")
    @Expose
    @Nullable private String store_url;


    /**
     * Instantiates a new Order.
     *
     * @param id the id
     */
    public Order(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * Gets reference.
     *
     * @return the reference
     */
    @Nullable
    public String getReference() {
        return reference;
    }
    /**
     * Gets store_url.
     *
     * @return the store_url
     */
    @Nullable
    public String getStore_url() {
        return store_url;
    }


}
