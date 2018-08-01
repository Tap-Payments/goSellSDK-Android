package company.tap.gosellapi.internal.data_managers.payment_options;

import java.util.ArrayList;

import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.internal.api.models.AmountedCurrency;

public class CurrencySectionData {
    private ArrayList<AmountedCurrency> data;
    private AmountedCurrency initialData;
    private AmountedCurrency userChoiceData;

    CurrencySectionData(ArrayList<AmountedCurrency> data, String initialCurrency) {
        this.data = data;
        this.initialData = getAmountedCurrencyByCurrencyCode(initialCurrency, data);
    }

    public void setUserChoiceData(AmountedCurrency userChoiceCurrency) {
        if (initialData.getIsoCode().equalsIgnoreCase(userChoiceCurrency.getIsoCode())) {
            userChoiceData = null;
        } else {
            userChoiceData = userChoiceCurrency;
        }
    }

    public ArrayList<AmountedCurrency> getData() {
        return data;
    }

    public static AmountedCurrency getAmountedCurrencyByCurrencyCode(String currencyCode, ArrayList<AmountedCurrency> data) {
        for (AmountedCurrency amountedCurrency : data) {
            if (amountedCurrency.getIsoCode().equalsIgnoreCase(currencyCode)) {
                return amountedCurrency;
            }
        }

        return null;
    }

    public String getInitialData() {
        return Utils.getFormattedCurrency(initialData);
    }

    public String getUserChoiceData() {
        return userChoiceData == null ? null : Utils.getFormattedCurrency(userChoiceData);
    }

    public AmountedCurrency getSelectedCurrency() {
        return userChoiceData == null ? initialData : userChoiceData;
    }
}
