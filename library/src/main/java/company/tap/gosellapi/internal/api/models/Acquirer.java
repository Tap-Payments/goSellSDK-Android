package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by h.sheshtawy on 2.10.2019.
 * <br>
 * Model for Acquirer object
 */
public final class Acquirer implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("response")
    @Expose
    private Response response;


    /**
     *  get Acquirer ID
      * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Get Acquirer response object
     * @return Response
     */
    public Response getResponse() {
        return response;
    }
}
