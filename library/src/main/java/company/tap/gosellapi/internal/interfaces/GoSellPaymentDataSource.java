package company.tap.gosellapi.internal.interfaces;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.internal.api.models.CustomerInfo;
import company.tap.gosellapi.internal.api.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.Receipt;
import company.tap.gosellapi.internal.api.models.Reference;
import company.tap.gosellapi.internal.api.models.Shipping;
import company.tap.gosellapi.internal.api.models.Tax;

public interface GoSellPaymentDataSource {

    String getCurrency();
    CustomerInfo getCustomerInfo();
    ArrayList<PaymentItem> getItems();
    ArrayList<Tax> getTaxes();
    ArrayList<Shipping> getShipping();
    String getPostURL();
    String getPaymentDescription();
    HashMap<String, String> getPaymentMetadata();
    Reference getPaymentReference();
    String getPaymentStatementDescriptor();
    boolean getRequires3DSecure();
    Receipt getReceiptSettings();
}
