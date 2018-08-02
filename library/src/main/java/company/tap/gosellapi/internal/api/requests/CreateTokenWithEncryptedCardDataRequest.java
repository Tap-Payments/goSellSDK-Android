package company.tap.gosellapi.internal.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.models.CreateTokenCard;

public final class CreateTokenWithEncryptedCardDataRequest {

    @SerializedName("card")
    @Expose
    private CreateTokenCard card;

    private CreateTokenWithEncryptedCardDataRequest(CreateTokenCard card) {
        this.card = card;
    }

    public final static class Builder {
        private CreateTokenWithEncryptedCardDataRequest createTokenWithEncryptedCardDataRequest;

        public Builder(CreateTokenCard card) {
            createTokenWithEncryptedCardDataRequest = new CreateTokenWithEncryptedCardDataRequest(card);
        }

        public CreateTokenWithEncryptedCardDataRequest build() {
            return createTokenWithEncryptedCardDataRequest;
        }
    }
}
