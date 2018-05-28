package company.tap.gosellapi.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * General delete response
 */

public final class GeneralDeleteResponse implements BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("deleted")
    @Expose
    private boolean deleted;

    /**
     * @return The ID of the deleted object.
     */
    public String getId() {
        return id;
    }

    /**
     * @return True on success
     */
    public boolean getDeleted() {
        return deleted;
    }
}
