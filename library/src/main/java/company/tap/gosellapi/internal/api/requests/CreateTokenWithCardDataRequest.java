package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.interfaces.CreateTokenRequest;

/**
 * The type Create token with card data request.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateTokenWithCardDataRequest implements CreateTokenRequest {

    @SerializedName("card")
    @Expose
    private CreateTokenCard card;

    /**
     * Instantiates a new Create token with card data request.
     *
     * @param card the card
     */
    public CreateTokenWithCardDataRequest(CreateTokenCard card) {
        this.card = card;
    }
}
