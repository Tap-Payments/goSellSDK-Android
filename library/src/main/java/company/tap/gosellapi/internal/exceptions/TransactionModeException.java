package company.tap.gosellapi.internal.exceptions;

public class TransactionModeException extends IllegalAccessException {
    private static final String MESSAGE = "Web payment not allowed for save card transaction";

    public TransactionModeException() {
        super(MESSAGE);
    }
}
