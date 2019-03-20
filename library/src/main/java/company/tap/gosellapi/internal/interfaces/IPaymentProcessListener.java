package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.SaveCard;
import company.tap.gosellapi.internal.api.models.Token;

/**
 * The interface Payment process listener.
 */
public interface IPaymentProcessListener {
    /**
     * Did receive charge.
     *
     * @param charge the charge
     */
    void didReceiveCharge(Charge charge);

    /**
     * Did receive authorize.
     *
     * @param authorize the authorize
     */
    void didReceiveAuthorize(Authorize authorize);

    /**
     * Did receive error.
     *
     * @param error the error
     */
    void didReceiveError(GoSellError error);

    /**
     * Did receive save card.
     *
     * @param saveCard the save card
     */
    void didReceiveSaveCard(SaveCard saveCard);

    /**
     * Did card saved before.
     */
    void didCardSavedBefore();

    /**
     * Card Tokenization process completed
     * @param token
     */
    void fireCardTokenizationProcessCompleted(Token token);


}