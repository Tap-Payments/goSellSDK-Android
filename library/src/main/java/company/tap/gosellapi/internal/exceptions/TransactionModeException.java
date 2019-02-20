package company.tap.gosellapi.internal.exceptions;

/**
 * The type Transaction mode exception.
 */
public class TransactionModeException extends IllegalAccessException {
    private static final String MESSAGE = "Web payment not allowed for save card transaction";

    /**
     * Instantiates a new Transaction mode exception.
     */
    public TransactionModeException() {
        super(MESSAGE);
    }
}
