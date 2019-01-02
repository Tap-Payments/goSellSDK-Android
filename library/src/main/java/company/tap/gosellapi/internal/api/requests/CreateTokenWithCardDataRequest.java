package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.RestrictTo;

import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateTokenWithCardDataRequest implements CreateTokenRequest {

    @SerializedName("card")
    @Expose
    private CreateTokenCard card;

    public CreateTokenWithCardDataRequest(CreateTokenCard card) {
        this.card = card;
    }
}
