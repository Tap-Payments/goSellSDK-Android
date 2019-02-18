package company.tap.gosellapi.internal.interfaces;

import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.SaveCard;

public interface IPaymentProcessListener {
    void didReceiveCharge(Charge charge);
    void didReceiveAuthorize(Authorize authorize);
    void didReceiveError(GoSellError error);
    void didReceiveSaveCard(SaveCard saveCard);
    void didCardSavedBefore();
}
