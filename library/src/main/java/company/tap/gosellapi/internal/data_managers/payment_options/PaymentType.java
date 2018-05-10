package company.tap.gosellapi.internal.data_managers.payment_options;

public enum PaymentType {
    CURRENCY(0),
    RECENT(1),
    WEB(2),
    CARD(3),
    EMPTY(4);

    private int viewType;

    PaymentType(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public static PaymentType getByViewType(int viewType) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.viewType == viewType) {
                return paymentType;
            }
        }

        return EMPTY;
    }
}
