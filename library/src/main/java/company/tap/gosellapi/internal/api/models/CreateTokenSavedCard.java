package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTokenSavedCard {

    @SerializedName("card_id")
    @Expose
    private String cardId;

    @SerializedName("")
    @Expose
    private String customerId;
}
