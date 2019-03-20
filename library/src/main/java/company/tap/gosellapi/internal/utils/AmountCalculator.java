package company.tap.gosellapi.internal.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.ExtraFee;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.Tax;

/**
 * The type Amount calculator.
 */
public abstract class AmountCalculator {

    /**
     * Calculate total amount of big decimal.
     *
     * @param item the item
     * @return the big decimal
     */
    public static BigDecimal calculateTotalAmountOf(PaymentItem item) {

        BigDecimal result = item.getPlainAmount().subtract(item.getDiscountAmount()).add(item.getTaxesAmount());
        return result;
    }

    /**
     * Calculate total amount of big decimal.
     *
     * @param items     the items
     * @param taxes     the taxes
     * @param shippings the shippings
     * @return the big decimal
     */
    public static BigDecimal calculateTotalAmountOf(ArrayList<PaymentItem> items, ArrayList<Tax> taxes, ArrayList<Shipping> shippings) {

        BigDecimal itemsPlainAmount     = BigDecimal.ZERO;
        BigDecimal itemsDiscountAmount  = BigDecimal.ZERO;
        BigDecimal itemsTaxesAmount     = BigDecimal.ZERO;

        for (PaymentItem item : items) {

            itemsPlainAmount    = itemsPlainAmount.add(item.getPlainAmount());
            itemsDiscountAmount = itemsDiscountAmount.add(item.getDiscountAmount());
            itemsTaxesAmount    = itemsTaxesAmount.add(item.getTaxesAmount());
        }

        BigDecimal discountedAmount = itemsPlainAmount.subtract(itemsDiscountAmount);

        BigDecimal shippingAmount = BigDecimal.ZERO;
        if (shippings != null) {

            for (Shipping shipping : shippings) {

                shippingAmount = shippingAmount.add(shipping.getAmount());
            }
        }

        BigDecimal taxesAmount = calculateTaxesOn(discountedAmount.add(shippingAmount), taxes);
        BigDecimal totalTaxesAmount = itemsTaxesAmount.add(taxesAmount);

        BigDecimal result = discountedAmount.add(shippingAmount).add(totalTaxesAmount);

        return result;
    }

    /**
     * Calculate taxes on big decimal.
     *
     * @param amount the amount
     * @param taxes  the taxes
     * @return the big decimal
     */
    public static BigDecimal calculateTaxesOn(BigDecimal amount, ArrayList<Tax> taxes) {

        BigDecimal result = BigDecimal.ZERO;

        if (taxes == null) {

            return result;
        }

        for (Tax tax : taxes) {

            switch (tax.getAmount().getType()) {

                case PERCENTAGE:

                    result = result.add(amount.multiply(tax.getAmount().getNormalizedValue()));

                case FIXED:

                    result = result.add(tax.getAmount().getValue());
            }
        }

        return result;
    }

    /**
     * Calculate extra fees amount big decimal.
     *
     * @param fees                the fees
     * @param supportedCurrencies the supported currencies
     * @param currency            the currency
     * @return the big decimal
     */
    public static BigDecimal calculateExtraFeesAmount(ArrayList<ExtraFee> fees, ArrayList<AmountedCurrency> supportedCurrencies, AmountedCurrency currency) {

        BigDecimal result = BigDecimal.ZERO;

        if(fees!=null){
            for (ExtraFee fee : fees) {

                BigDecimal increase = BigDecimal.ZERO;

                switch (fee.getType()) {

                    case FIXED:

                        AmountedCurrency amountedCurrency = getAmountedCurrency(supportedCurrencies, fee.getCurrency());
                        increase = currency.getAmount().multiply(fee.getValue()).divide(amountedCurrency.getAmount());
                        break;

                    case PERCENTAGE:

                        increase = currency.getAmount().multiply(fee.getNormalizedValue());
                        break;
                }

                result = result.add(increase);
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
