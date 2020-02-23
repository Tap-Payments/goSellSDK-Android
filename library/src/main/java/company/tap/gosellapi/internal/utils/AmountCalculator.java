package company.tap.gosellapi.internal.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.api.models.ExtraFee;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
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
/***
 * Based on JS Lib added check for Settlement currency equals the amounted currency.
 */
                        if(PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getSettlement_currency()!=null)
                            if (PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getSettlement_currency().equals(currency.getCurrency())) {
                                return fee.getValue();
                            } else {
                                /***
                                 * Based on JS Lib Condition for extra fees calculation changed.
                                 *  var rate =  settlement_currency.amount / current_currency.amount
                                 *    extra_fee = fee.value * rate;
                                 *
                                 */


                                /***
                                Commented by Ahlaam
                                 **/
                                // increase = currency.getAmount().multiply(fee.getValue()).divide(amountedCurrency.getAmount(), MathContext.DECIMAL64); /// handling issue of  quotient has a non terminating decimal expansion
                                BigDecimal rate = amountedCurrency.getAmount().divide(currency.getAmount(),MathContext.DECIMAL64);
                                increase= fee.getValue().multiply(rate);
                            }

                        break;

                    case PERCENTAGE:

                       /**
                        * Commented to replace with JS*/
                        // increase = currency.getAmount().multiply(fee.getNormalizedValue());

                        /***
                         * Increase i.e change in currency changed as per JS Lib FORMULA: extra_fee =  {amount} / (1 - fee.value / 100) - {amount};
                         */
                        increase = (currency.getAmount().divide(BigDecimal.valueOf(1).subtract((fee.getValue().divide(BigDecimal.valueOf(100)))),MathContext.DECIMAL64)).subtract(currency.getAmount());
                      //  System.out.println("increase = " + increase );
                        /**
                         *  Applying Min and Max values based on the calculated extra fees.
                         */
                        if((fee.getMinimum_fee()!=null && fee.getMinimum_fee()!=0)|| (fee.getMaximum_fee()!= null && fee.getMaximum_fee()!=0)){
                            if(Double.valueOf(String.valueOf(increase))>fee.getMinimum_fee() && Double.valueOf(String.valueOf(increase)) < fee.getMaximum_fee()){
                                increase= increase;
                            }else if(Double.valueOf(String.valueOf(increase)) < fee.getMinimum_fee()){
                                increase = BigDecimal.valueOf(fee.getMinimum_fee());
                            }else if(Double.valueOf(String.valueOf(increase))> fee.getMaximum_fee()){
                                increase = BigDecimal.valueOf(fee.getMaximum_fee());
                            }

                        }

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
