package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.responses.DeleteCardResponse;

public interface ICardDeleteListener {
    /**
     * Did card delete
     */
    void didCardDeleted(DeleteCardResponse deleteCardResponse);
}
