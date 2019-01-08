package company.tap.gosellapi.internal.viewholders;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;

//T - data, K - this holder, Q - model
public abstract class PaymentOptionsBaseViewHolder<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionViewModel<T, K, Q>> extends RecyclerView.ViewHolder {
    Q viewModel;




  public enum ViewHolderType {

        CURRENCY(0),
        EMPTY(1),
        GROUP(2),
        SAVED_CARDS(3),
        WEB(4),
        CARD(5);

        private int viewType;

        ViewHolderType(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() { return viewType; }

        public static ViewHolderType getByViewType(int viewType) {

            for (ViewHolderType viewHolderType : ViewHolderType.values()) {

                if (viewHolderType.getViewType() == viewType) {

                    return viewHolderType;
                }
            }

            return EMPTY;
        }
    }

    public static PaymentOptionsBaseViewHolder newInstance(ViewGroup parent, @NonNull ViewHolderType viewHolderType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewHolderType) {

            case CURRENCY:

                view = inflater.inflate(R.layout.gosellapi_viewholder_currency, parent, false);
                return new CurrencyViewHolder(view);

            case GROUP:

                view = inflater.inflate(R.layout.gosellapi_viewholder_group, parent, false);
                return new GroupViewHolder(view);

            case SAVED_CARDS:

//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_save_card, parent, false);
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view);

            case WEB:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellsdk_viewholder_web_payment_option, parent, false);
                return new WebPaymentViewHolder(view);

            case CARD:

//              view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellsdk_viewholder_card_payment_option, parent, false);
                return new CardCredentialsViewHolder(view);

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
