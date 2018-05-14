package company.tap.gosellapi.internal.data_managers;

import company.tap.gosellapi.internal.api.responses.InitResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;

public class GlobalDataManager {
    private InitResponse initResponse;

    private PaymentOptionsDataManager paymentOptionsDataManager;

    private GlobalDataManager() {

    }

    private static class SingletonHolder {
        private static final GlobalDataManager INSTANCE = new GlobalDataManager();
    }

    public static GlobalDataManager getInstance() {
        return GlobalDataManager.SingletonHolder.INSTANCE;
    }

    public InitResponse getInitResponse() {
        return initResponse;
    }

    public void setInitResponse(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    public void setPaymentOptionsResponse(PaymentOptionsResponse paymentOptionsResponse) {
        paymentOptionsDataManager = new PaymentOptionsDataManager(paymentOptionsResponse);
    }

    public PaymentOptionsDataManager getPaymentOptionsDataManager(PaymentOptionsDataManager.PaymentOptionsDataListener dataListener) {
        return paymentOptionsDataManager.setListener(dataListener);
    }
}
