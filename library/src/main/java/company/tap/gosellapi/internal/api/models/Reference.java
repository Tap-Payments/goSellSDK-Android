package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reference {

    @SerializedName("acquirer")
    @Expose
    private String acquirer;

    @SerializedName("gateway")
    @Expose
    private String gateway;

    @SerializedName("payment")
    @Expose
    private String payment;

    @SerializedName("track")
    @Expose
    private String track;

    @SerializedName("transaction")
    @Expose
    private String transaction;

    @SerializedName("order")
    @Expose
    private String order;

    public String getAcquirer() {
        return acquirer;
    }

    public String getGateway() {
        return gateway;
    }

    public String getPayment() {
        return payment;
    }

    public String getTrack() {
        return track;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getOrder() {
        return order;
    }
}
