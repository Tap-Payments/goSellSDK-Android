package company.tap.gosellapi.internal.data_source;

import company.tap.gosellapi.internal.api.responses.InitResponse;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsDataSource;

public class GlobalDataManager {
    private InitResponse initResponse;

    private PaymentOptionsDataSource paymentOptionsDataSource;
    private PaymentOptionsResponse paymentOptionsResponse;

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

    public PaymentOptionsResponse getPaymentOptionsResponse() {
        return paymentOptionsResponse;
    }

    public void setPaymentOptionsResponse(PaymentOptionsResponse paymentOptionsResponse) {
        this.paymentOptionsResponse = paymentOptionsResponse;
        paymentOptionsDataSource = new PaymentOptionsDataSource();
    }

    public PaymentOptionsDataSource getPaymentOptionsDataSource() {
        return paymentOptionsDataSource;
    }
}
