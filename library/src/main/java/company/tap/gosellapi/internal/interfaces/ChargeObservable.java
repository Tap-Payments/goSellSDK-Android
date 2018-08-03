package company.tap.gosellapi.internal.interfaces;

public interface ChargeObservable {
    void addObserver(ChargeObserver observer);
    void removeObserver(ChargeObserver observer);
    void notifyObserversOtpScreenNeedToShown();
    void notifyObserversWebScreenNeedToShown();
    void notifyObserversResponseSucceed();
    void notifyObserversResponseFailed();
}
