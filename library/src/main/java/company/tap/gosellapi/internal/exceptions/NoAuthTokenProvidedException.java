package company.tap.gosellapi.internal.exceptions;

public class NoAuthTokenProvidedException extends IllegalStateException{
    private static final String MESSAGE = "Auth token was not provided!";

    public NoAuthTokenProvidedException() {
        super(MESSAGE);
    }
}
