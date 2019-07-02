package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;

public interface ICardDeleteListener {
    /**
     * Did card deleted successfully
     */
    void didCardDeleted(DeleteCardResponse deleteCardResponse);


    /**
     * Did card delete cause an error
     */
    void didDeleteCardReceiveError(GoSellError errorDetails);
}
