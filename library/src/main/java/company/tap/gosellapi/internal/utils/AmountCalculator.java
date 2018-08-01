package company.tap.gosellapi.internal.utils;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.ExtraFee;
import company.tap.gosellapi.internal.api.models.PaymentItem;
import company.tap.gosellapi.internal.api.models.Shipping;
import company.tap.gosellapi.internal.api.models.Tax;

public abstract class AmountCalculator {

    public static double calculateTotalAmountOf(PaymentItem item) {

        double result = item.getPlainAmount() - item.getDiscountAmount() + item.getTaxesAmount();
        return result;
    }

    public static double calculateTotalAmountOf(ArrayList<PaymentItem> items, ArrayList<Tax> taxes, ArrayList<Shipping> shippings) {

        double itemsPlainAmount = 0.0f;
        double itemsDiscountAmount = 0.0f;
        double itemsTaxesAmount = 0.0f;
        for (PaymentItem item : items) {

            itemsPlainAmount += item.getPlainAmount();
            itemsDiscountAmount += item.getDiscountAmount();
            itemsTaxesAmount += item.getTaxesAmount();
        }

        double discountedAmount = itemsPlainAmount - itemsDiscountAmount;

        double shippingAmount = 0.0;
        if (shippings != null) {

            for (Shipping shipping : shippings) {

                shippingAmount += shipping.getAmount();
            }
        }

        double taxesAmount = calculateTaxesOn(discountedAmount + shippingAmount, taxes);
        double totalTaxesAmount = itemsTaxesAmount + taxesAmount;

        double result = discountedAmount + shippingAmount + totalTaxesAmount;

        return result;
    }

    public static double calculateTaxesOn(double amount, ArrayList<Tax> taxes) {

        double result = 0.0f;

        if (taxes == null) {

            return result;
        }

        for (Tax tax : taxes) {

            switch (tax.getAmount().getType()) {

                case PERCENTAGE:

                    result += amount * tax.getAmount().getNormalizedValue();

                case FIXED:

                    result += tax.getAmount().getValue();
            }
        }

        return result;
    }

    public static double calculateExtraFeesAmount(ArrayList<ExtraFee> fees, ArrayList<AmountedCurrency> supportedCurrencies, AmountedCurrency currency) {

        double result = 0.0f;

        for (ExtraFee fee : fees) {

            switch (fee.getType()) {

                case FIXED:

                    AmountedCurrency amountedCurrency = getAmountedCurrency(supportedCurrencies, fee.getCurrency());
                    result += currency.getAmount() * fee.getValue() / amountedCurrency.getAmount();
                    break;

                case PERCENTAGE:

                    result += currency.getAmount() * fee.getNormalizedValue();
                    break;
            }
        }

        return result;
    }

    private static AmountedCurrency getAmountedCurrency(ArrayList<AmountedCurrency> amountedCurrencies, String currency) {

        for ( AmountedCurrency amountedCurrency : amountedCurrencies ) {

            if (amountedCurrency.getCurrency().equals(currency)) {

                return amountedCurrency;
            }
        }

        return null;
    }
}
