package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTokenCard {

    @SerializedName("crypted_data")
    @Expose
    private String cryptedData;

    @SerializedName("address")
    @Expose
    private Address address;

    public CreateTokenCard(String cryptedData) {
        this.cryptedData = cryptedData;
    }
}
