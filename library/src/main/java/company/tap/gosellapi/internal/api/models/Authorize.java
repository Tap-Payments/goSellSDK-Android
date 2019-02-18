package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Authorize extends Charge {

    @SerializedName("auto")
    @Expose
    @NonNull private AuthorizeActionResponse autorizeAction;
}
