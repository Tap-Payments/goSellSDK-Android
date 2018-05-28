package company.tap.gosellapi.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.api.model.Card;
import company.tap.gosellapi.api.model.Customer;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Get all {@link Card}s for the {@link Customer} response
 */

public final class RetrieveCardsResponse implements BaseResponse {
    @SerializedName("object")
    @Expose
    private String object;

    @SerializedName("has_more")
    @Expose
    private boolean has_more;

    @SerializedName("data")
    @Expose
    private ArrayList<Card> data;

    public String getObject() {
        return object;
    }

    public boolean isHas_more() {
        return has_more;
    }

    /**
     * @return List of user {@link Card}s
     */
    public ArrayList<Card> getData() {
        return data;
    }
}
