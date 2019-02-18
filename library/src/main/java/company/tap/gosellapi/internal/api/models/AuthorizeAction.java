package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthorizeActionType;

public final class AuthorizeAction {

    @SerializedName("type")
    @Expose
    private AuthorizeActionType type;

    @SerializedName("time")
    @Expose
    private int timeInHours;

    public static AuthorizeAction getDefault() {

        return new AuthorizeAction(AuthorizeActionType.VOID, 168);
    }

    public AuthorizeActionType getType() {
        return type;
    }

    public int getTimeInHours() {
        return timeInHours;
    }

    public AuthorizeAction(AuthorizeActionType type, int timeInHours) {

        this.type = type;
        this.timeInHours = timeInHours;
    }
}
