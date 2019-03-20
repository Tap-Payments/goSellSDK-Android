package company.tap.gosellapi.internal.exceptions;

/**
 * The type No auth token provided exception.
 */
public class NoAuthTokenProvidedException extends IllegalStateException{
    private static final String MESSAGE = "Auth token was not provided!";

    /**
     * Instantiates a new No auth token provided exception.
     */
    public NoAuthTokenProvidedException() {
        super(MESSAGE);
    }
}
