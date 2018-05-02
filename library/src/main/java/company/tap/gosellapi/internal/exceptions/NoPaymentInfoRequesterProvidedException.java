package company.tap.gosellapi.internal.exceptions;

public class NoPaymentInfoRequesterProvidedException extends IllegalStateException{
    private static final String MESSAGE = "GoSellPayButtonPaymentInfoRequester implementation was not set, please use GoSellPayButton.setPaymentInfoRequester() to provide payment information for GoSell SDK";

    public NoPaymentInfoRequesterProvidedException() {
        super(MESSAGE);
    }
}
