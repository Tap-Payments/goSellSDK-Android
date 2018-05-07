package company.tap.gosellapi.internal.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.PaymentOptionsViewHolderFocusedStateInterface;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentType;

public abstract class PaymentOptionsBaseViewHolder<T extends PaymentOptionsBaseModel> extends RecyclerView.ViewHolder {
    private final PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface;

    public static PaymentOptionsBaseViewHolder newInstance(ViewGroup parent, @NonNull PaymentType paymentType, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        View view = null;
        switch (paymentType) {
            case CURRENCY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_currency, parent, false);
                return new CurrencyViewHolder(view, focusedStateInterface);
            case RECENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view, focusedStateInterface);
            case WEB:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false);
                return new WebPaymentSystemsViewHolder(view, focusedStateInterface);
            case CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                return new CardCredentialsViewHolder(view, focusedStateInterface);
            default:
                return new DummyViewHolder(view, focusedStateInterface);
        }
    }

    boolean isFocused;
    private View itemView;

    PaymentOptionsBaseViewHolder(View itemView, PaymentOptionsViewHolderFocusedStateInterface focusedStateInterface) {
        super(itemView);
        this.itemView = itemView;
        this.focusedStateInterface = focusedStateInterface;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOptionsBaseViewHolder.this.focusedStateInterface.setFocused(PaymentOptionsBaseViewHolder.this, getAdapterPosition());
                setFocused(!isFocused);
            }
        });
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;

        if (isFocused) {
            itemView.setBackgroundResource(R.color.vibrant_green);
        } else {
            itemView.setBackground(null);
        }
    }

    abstract public void bind(T data);
}
