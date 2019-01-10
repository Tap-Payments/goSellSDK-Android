package company.tap.gosellapi.internal.interfaces;

public interface OTPListener {
  /**
   * To call this method when new message received and send back
   * @param message Message
   */
  void messageReceived(String message);
}
