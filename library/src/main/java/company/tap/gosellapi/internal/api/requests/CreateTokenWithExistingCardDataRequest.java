package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.models.CreateTokenSavedCard;

/**
 * The type Create token with existing card data request.
 */
public final class CreateTokenWithExistingCardDataRequest {

    @SerializedName("saved_card")
    @Expose
    private CreateTokenSavedCard savedCard;

    private CreateTokenWithExistingCardDataRequest(CreateTokenSavedCard savedCard) {
        this.savedCard = savedCard;
    }

    /**
     * The type Builder.
     */
    public final static class Builder {
        private CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest;

        /**
         * Instantiates a new Builder.
         *
         * @param card the card
         */
        public Builder(CreateTokenSavedCard card) {
            createTokenWithExistingCardDataRequest = new CreateTokenWithExistingCardDataRequest(card);
        }

        /**
         * Build create token with existing card data request.
         *
         * @return the create token with existing card data request
         */
        public CreateTokenWithExistingCardDataRequest build() {
            return createTokenWithExistingCardDataRequest;
        }
    }
}
