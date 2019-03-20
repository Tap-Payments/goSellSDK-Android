package company.tap.gosellapi.open.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The type Reference.
 */
public final class Reference implements Serializable {

    @SerializedName("acquirer")
    @Expose
    @Nullable private String acquirer;

    @SerializedName("gateway")
    @Expose
    @Nullable private String gateway;

    @SerializedName("payment")
    @Expose
    @Nullable private String payment;

    @SerializedName("track")
    @Expose
    @Nullable private String track;

    @SerializedName("transaction")
    @Expose
    @Nullable private String transaction;

    @SerializedName("order")
    @Expose
    @Nullable private String order;

    /**
     * Gets acquirer.
     *
     * @return the acquirer
     */
    @Nullable public String getAcquirer() {
        return acquirer;
    }

    /**
     * Gets gateway.
     *
     * @return the gateway
     */
    @Nullable public String getGateway() {
        return gateway;
    }

    /**
     * Gets payment.
     *
     * @return the payment
     */
    @Nullable public String getPayment() {
        return payment;
    }

    /**
     * Gets track.
     *
     * @return the track
     */
    @Nullable public String getTrack() {
        return track;
    }

    /**
     * Gets transaction.
     *
     * @return the transaction
     */
    @Nullable public String getTransaction() {
        return transaction;
    }

    /**
     * Gets order.
     *
     * @return the order
     */
    @Nullable public String getOrder() {
        return order;
    }

    /**
     * Instantiates a new Reference.
     *
     * @param acquirer    the acquirer
     * @param gateway     the gateway
     * @param payment     the payment
     * @param track       the track
     * @param transaction the transaction
     * @param order       the order
     */
    public Reference(@Nullable String acquirer, @Nullable String gateway, @Nullable String payment, @Nullable String track, @Nullable String transaction, @Nullable String order) {

        this.acquirer = acquirer;
        this.gateway = gateway;
        this.payment = payment;
        this.track = track;
        this.transaction = transaction;
        this.order = order;
    }
}
