package company.tap.gosellapi.open.exception;

public class CurrencyException  extends ExceptionInInitializerError{

  public static final CurrencyException getUnknown(String code) {

    return  new CurrencyException("Unknown currency: " + code);
  }

  private CurrencyException(String exceptionMessage) {

    super(exceptionMessage);
  }

}
