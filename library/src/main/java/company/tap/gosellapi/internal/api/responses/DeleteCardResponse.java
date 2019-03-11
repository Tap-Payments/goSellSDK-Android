package company.tap.gosellapi.internal.api.responses;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created By Haitham Elsheshtawy on 10 March 2019
 * Model for {@link DeleteCardResponse} object
 * <p>
 * Copyright © 2019 Tap Payments. All rights reserved.
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DeleteCardResponse implements BaseResponse {
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("deleted")
    @Expose
    boolean deleted;

    public String getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
