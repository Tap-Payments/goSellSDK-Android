package company.tap.gosellapi.internal.data_managers.payment_options;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import company.tap.gosellapi.internal.Utils;

public class CurrencySectionData {
    private HashMap<String, Double> data;
    private Map.Entry<String, Double> initialData;
    private Map.Entry<String, Double> userChoiceData;

    CurrencySectionData(HashMap<String, Double> data, String initialCurrency, double initialAmount) {
        this.data = data;
        this.initialData = new AbstractMap.SimpleEntry<>(initialCurrency, initialAmount);
    }

    public void setUserChoiceData(String currencyCode, double amount) {
        this.userChoiceData = new AbstractMap.SimpleEntry<>(currencyCode, amount);
    }

    public HashMap<String, Double> getData() {
        return data;
    }

    public String getInitialData() {
        return Utils.getFormattedCurrency(initialData.getKey(), initialData.getValue());
    }

    public String getUserChoiceData() {
        return userChoiceData == null ? null : Utils.getFormattedCurrency(userChoiceData.getKey(), userChoiceData.getValue());
    }

    public String getSelectedCurrencyCode() {
        return userChoiceData == null ? initialData.getKey() : userChoiceData.getKey();
    }
}
