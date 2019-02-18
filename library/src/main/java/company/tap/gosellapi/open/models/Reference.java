package company.tap.gosellapi.open.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Reference {

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

    @Nullable public String getAcquirer() {
        return acquirer;
    }

    @Nullable public String getGateway() {
        return gateway;
    }

    @Nullable public String getPayment() {
        return payment;
    }

    @Nullable public String getTrack() {
        return track;
    }

    @Nullable public String getTransaction() {
        return transaction;
    }

    @Nullable public String getOrder() {
        return order;
    }

    public Reference(@Nullable String acquirer, @Nullable String gateway, @Nullable String payment, @Nullable String track, @Nullable String transaction, @Nullable String order) {

        this.acquirer = acquirer;
        this.gateway = gateway;
        this.payment = payment;
        this.track = track;
        this.transaction = transaction;
        this.order = order;
    }
}
