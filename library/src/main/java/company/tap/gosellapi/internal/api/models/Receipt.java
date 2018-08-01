package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receipt {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("email")
    @Expose
    private boolean email;

    @SerializedName("sms")
    @Expose
    private boolean sms;

    public Receipt(boolean email, boolean sms) {
        this.email = email;
        this.sms = sms;
    }

    public String getId() {
        return id;
    }

    public boolean isEmail() {
        return email;
    }

    public boolean isSms() {
        return sms;
    }
}
