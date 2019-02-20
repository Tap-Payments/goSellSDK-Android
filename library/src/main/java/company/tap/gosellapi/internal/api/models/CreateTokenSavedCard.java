package company.tap.gosellapi.internal.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Create token saved card.
 */
public final class CreateTokenSavedCard {

    @SerializedName("card_id")
    @Expose
    private String cardId;

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    /**
     * Instantiates a new Create token saved card.
     *
     * @param cardId     the card id
     * @param customerId the customer id
     */
    public CreateTokenSavedCard(String cardId, String customerId) {
        this.cardId = cardId;
        this.customerId = customerId;
    }

    /**
     * Gets card id.
     *
     * @return Card identifier.
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Gets customer id.
     *
     * @return Customer identifier.
     */
    public String getCustomerId() {
        return customerId;
    }
}
