package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.models.CreateTokenSavedCard;

public class CreateTokenWithExistingCardDataRequest {

    @SerializedName("saved_card")
    @Expose
    private CreateTokenSavedCard savedCard;

    private CreateTokenWithExistingCardDataRequest(CreateTokenSavedCard savedCard) {
        this.savedCard = savedCard;
    }

    public final static class Builder {
        private CreateTokenWithExistingCardDataRequest createTokenWithExistingCardDataRequest;

        public Builder(CreateTokenSavedCard card) {
            createTokenWithExistingCardDataRequest = new CreateTokenWithExistingCardDataRequest(card);
        }

        public CreateTokenWithExistingCardDataRequest build() {
            return createTokenWithExistingCardDataRequest;
        }
    }
}
