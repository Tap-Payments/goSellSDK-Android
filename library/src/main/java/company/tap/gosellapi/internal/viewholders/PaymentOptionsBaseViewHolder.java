package company.tap.gosellapi.internal.viewholders;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentType;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.PaymentOptionsBaseViewModel;

//T - data, K - this holder, Q - model
public abstract class PaymentOptionsBaseViewHolder<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionsBaseViewModel<T, K, Q>> extends RecyclerView.ViewHolder {
    Q viewModel;

    public static PaymentOptionsBaseViewHolder newInstance(ViewGroup parent, @NonNull PaymentType paymentType) {
        View view;
        switch (paymentType) {
            case CURRENCY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_currency, parent, false);
                return new CurrencyViewHolder(view);
            case RECENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view);
            case WEB:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false);
                return new WebPaymentViewHolder(view);
            case CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                return new CardCredentialsViewHolder(view);
            case SAVE_CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_save_card, parent, false);
                return new SaveCardViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_empty, parent, false);
                return new EmptyViewHolder(view);
        }
    }

    PaymentOptionsBaseViewHolder(View itemView) {
        super(itemView);
    }

    public final void attachToViewModel(Q viewModel, int position) {
        this.viewModel = viewModel;

        //noinspection unchecked
        viewModel.registerHolder((K) this, position);
    }

    public final void detachFromViewModel() {
        viewModel.unregisterHolder();
    }

    public abstract void bind(T data);
    public final void unbind() {
        saveState();
    }

    public void setFocused(boolean isFocused) {}

    public Parcelable saveState() { return null; }
    public void restoreState(Parcelable state) { }
}
