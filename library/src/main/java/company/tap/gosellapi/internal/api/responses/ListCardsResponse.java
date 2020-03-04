package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;

/**
 * Created by haitham elsheshtawy on 25.06.2019.
 * <br>
 * Model for {@link ListCardsResponse} object
 * * Copyright © 2019 Tap Payments. All rights reserved.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class ListCardsResponse implements BaseResponse {

    @SerializedName("object")
    @Expose
    @NonNull
    private String object;


    @SerializedName("has_more")
    @Expose
    @Nullable
    private boolean has_more;

    @SerializedName("data")
    @Expose
    @Nullable
    private ArrayList<SavedCard> data;


    @NonNull
    public String getObject() {
        return object;
    }

    public boolean isHas_more() {
        return has_more;
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    @NonNull public ArrayList<SavedCard> getCards() {

        if ( data == null ) {

            data = new ArrayList<>();
        }
        /**
         * Added to filter the cardType based on Merchant preference CREDIT/DEBIT
         * */
        for(int i=0;  i<data.size();i++){
            if ((data != null && PaymentDataSource.getInstance().getCardType() != null) && !PaymentDataSource.getInstance().getCardType().toString().equals(data.get(i).getFunding())) {
                data.remove(i);
            }
        }

        return data;
    }

}
