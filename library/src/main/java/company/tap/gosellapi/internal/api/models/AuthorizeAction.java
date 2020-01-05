package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;

/**
 * The type Authorize action.
 */
public final class AuthorizeAction implements Serializable {

    @SerializedName("type")
    @Expose
    private AuthorizeActionType type;

    @SerializedName("time")
    @Expose
    private int timeInHours;

    /**
     * Gets default.
     *
     * @return the default
     */
    public static AuthorizeAction getDefault() {

        return new AuthorizeAction(AuthorizeActionType.VOID, 168);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AuthorizeActionType getType() {
        return type;
    }

    /**
     * Gets time in hours.
     *
     * @return the time in hours
     */
    public int getTimeInHours() {
        return timeInHours;
    }

    /**
     * Instantiates a new Authorize action.
     *
     * @param type        the type
     * @param timeInHours the time in hours
     */
    public AuthorizeAction(AuthorizeActionType type, int timeInHours) {

        this.type = type;
        this.timeInHours = timeInHours;
    }
}
