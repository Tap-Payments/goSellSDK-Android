package company.tap.gosellapi.internal.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentType;

public abstract class PaymentOptionsBaseViewHolder<T extends PaymentOptionsBaseModel> extends RecyclerView.ViewHolder {
    public static PaymentOptionsBaseViewHolder newInstance(ViewGroup parent, @NonNull PaymentType paymentType) {
        View view = null;
        switch (paymentType) {
            case CURRENCY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_currency, parent, false);
                return new CurrencyViewHolder(view);
            case RECENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view);
            case WEB:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false);
                return new WebPaymentSystemsViewHolder(view);
            case CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                return new CardCredentialsViewHolder(view);
            default:
                return new DummyViewHolder(view);
        }
    }

    PaymentOptionsBaseViewHolder(View itemView) {
        super(itemView);
    }
    
    abstract public void bind(T data);
}
