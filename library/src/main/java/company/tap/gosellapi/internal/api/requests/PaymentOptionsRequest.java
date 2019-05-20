package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;

import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.Shipping;
import company.tap.gosellapi.open.models.Tax;
import company.tap.gosellapi.internal.utils.AmountCalculator;

/**
 * The type Payment options request.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PaymentOptionsRequest {

    @SerializedName("transaction_mode")
    @Expose
    @NonNull private TransactionMode transactionMode;

    @SerializedName("items")
    @Expose
    @Nullable private ArrayList<PaymentItem> items;

    @SerializedName("shipping")
    @Expose
    @Nullable private ArrayList<Shipping> shipping;

    @SerializedName("taxes")
    @Expose
    @Nullable private ArrayList<Tax> taxes;

    @SerializedName("customer")
    @Expose
    @Nullable private String customer;

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("total_amount")
    @Expose
    @NonNull private BigDecimal totalAmount;

    @SerializedName("merchant_id")
    @Expose
    @NonNull private String merchant_id;

    /**
     * Instantiates a new Payment options request.
     *
     * @param transactionMode the transaction mode
     * @param amount          the amount
     * @param items           the items
     * @param shipping        the shipping
     * @param taxes           the taxes
     * @param currency        the currency
     * @param customer        the customer
     */
    public PaymentOptionsRequest(@Nullable TransactionMode transactionMode,
                                 @Nullable BigDecimal amount,
                                 @Nullable ArrayList<PaymentItem> items,
                                 @Nullable ArrayList<Shipping> shipping,
                                 @Nullable ArrayList<Tax> taxes,
                                 @Nullable String currency,
                                 @Nullable String customer,
                                 @Nullable String merchant_id

    ) {

        this.transactionMode    = transactionMode == null ? TransactionMode.PURCHASE : transactionMode;
        this.shipping           = shipping;
        this.taxes              = taxes;
        this.currency           = currency;
        this.customer           = customer;
        this.merchant_id        = merchant_id;

        if (items != null && items.size() > 0) {

            this.items          = items;
            this.totalAmount    = AmountCalculator.calculateTotalAmountOf(items, taxes, shipping);
        }
        else {

            this.items = null;

            BigDecimal plainAmount = amount == null ? BigDecimal.ZERO : amount;
            this.totalAmount = AmountCalculator.calculateTotalAmountOf(new ArrayList<PaymentItem>(), taxes, shipping).add(plainAmount);
        }
    }

    /**
     * Gets transaction mode.
     *
     * @return the transaction mode
     */
    @NonNull
    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    /**
     * Get payment option request info string.
     *
     * @return the string
     */
    public String getPaymentOptionRequestInfo(){
        return
            "trx_mode : " + this.transactionMode + " /n " +
            "shipping : " + this.shipping + " /n " +
            "taxes : " + this.taxes + " /n " +
            "currency : " + this.currency + " /n " +
            "customer : " + this.customer + " /n " +
            "total_amout : " + this.totalAmount + " /n " +
            "merchant_id : " + this.merchant_id + " /n "
        ;
    }
}
