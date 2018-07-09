package company.tap.gosellapi.internal.interfaces;

public interface CardRequestInterface {
    void onCardRequestSuccess();
    void onCardRequestFailure();
    void onCardRequestOTP();
    void onCardRequestRedirect();
}
