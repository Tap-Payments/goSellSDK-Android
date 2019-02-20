package company.tap.gosellapi.internal.interfaces;

/**
 * The interface Otp listener.
 */
public interface OTPListener {
    /**
     * To call this method when new message received and send back
     *
     * @param message Message
     */
    void messageReceived(String message);
}
