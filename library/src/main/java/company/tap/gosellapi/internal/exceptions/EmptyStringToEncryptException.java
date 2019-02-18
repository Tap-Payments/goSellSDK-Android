package company.tap.gosellapi.internal.exceptions;

public class EmptyStringToEncryptException extends IllegalArgumentException{
    private static final String MESSAGE = "Parameter jsonString cannot be empty";

    public EmptyStringToEncryptException() {
        super(MESSAGE);
    }
}
