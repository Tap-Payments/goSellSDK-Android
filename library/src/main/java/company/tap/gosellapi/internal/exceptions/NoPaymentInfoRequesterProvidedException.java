package company.tap.gosellapi.internal.exceptions;

/**
 * The type No payment info requester provided exception.
 */
public class NoPaymentInfoRequesterProvidedException extends IllegalStateException{
    private static final String MESSAGE = "GoSellPaymentInfoRequester implementation was not set, please use GoSellPayLayout.setPaymentInfoRequester() to provide payment information for GoSell SDK";

    /**
     * Instantiates a new No payment info requester provided exception.
     */
    public NoPaymentInfoRequesterProvidedException() {
        super(MESSAGE);
    }
}
