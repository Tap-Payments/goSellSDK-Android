package company.tap.gosellapi.open.exception;

/**
 * The type Currency exception.
 */
public class CurrencyException  extends ExceptionInInitializerError{

    /**
     * Gets unknown.
     *
     * @param code the code
     * @return the unknown
     */
    public static final CurrencyException getUnknown(String code) {

    return  new CurrencyException("Unknown currency: " + code);
  }

  private CurrencyException(String exceptionMessage) {

    super(exceptionMessage);
  }

}
