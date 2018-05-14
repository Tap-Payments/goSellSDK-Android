package company.tap.gosellapi.internal.data_managers.payment_options;

public enum CardPaymentType {
    WEB("web"),
    CARD("card");

    private String value;

    CardPaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
