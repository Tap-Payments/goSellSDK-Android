package company.tap.gosellapi.internal.viewholders;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.PaymentOptionViewModel;

/**
 * The type Payment options base view holder.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 * @param <Q> the type parameter
 */
//T - data, K - this holder, Q - model
public abstract class PaymentOptionsBaseViewHolder<T, K extends PaymentOptionsBaseViewHolder<T, K, Q>, Q extends PaymentOptionViewModel<T, K, Q>> extends RecyclerView.ViewHolder {
    /**
     * The View model.
     */
    Q viewModel;


    /**
     * The enum View holder type.
     */
    public enum ViewHolderType {

        /**
         * Currency view holder type.
         */
        CURRENCY(0),
        /**
         * Empty view holder type.
         */
        EMPTY(1),
        /**
         * Group view holder type.
         */
        GROUP(2),
        /**
         * Saved cards view holder type.
         */
        SAVED_CARDS(3),
        /**
         * Web view holder type.
         */
        WEB(4),
        /**
         * Card view holder type.
         */
        CARD(5);

        private int viewType;

        ViewHolderType(int viewType) {
            this.viewType = viewType;
        }

        /**
         * Gets view type.
         *
         * @return the view type
         */
        public int getViewType() { return viewType; }

        /**
         * Gets by view type.
         *
         * @param viewType the view type
         * @return the by view type
         */
        public static ViewHolderType getByViewType(int viewType) {

            for (ViewHolderType viewHolderType : ViewHolderType.values()) {

                if (viewHolderType.getViewType() == viewType) {

                    return viewHolderType;
                }
            }

            return EMPTY;
        }
    }

    /**
     * New instance payment options base view holder.
     *
     * @param parent         the parent
     * @param viewHolderType the view holder type
     * @return the payment options base view holder
     */
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

    /**
     * Instantiates a new Payment options base view holder.
     *
     * @param itemView the item view
     */
    PaymentOptionsBaseViewHolder(View itemView) {

        super(itemView);
    }

    /**
     * Attach to view model.
     *
     * @param viewModel the view model
     * @param position  the position
     */
    public final void attachToViewModel(Q viewModel, int position) {

        this.viewModel = viewModel;

        //noinspection unchecked
        viewModel.registerHolder((K) this, position);
    }

    /**
     * Detach from view model.
     */
    public final void detachFromViewModel() {

        viewModel.unregisterHolder();
    }

    /**
     * Bind.
     *
     * @param data the data
     */
    public abstract void bind(T data);


    /**
     * Unbind.
     */
    public final void unbind() {

        saveState();
    }

    /**
     * Sets focused.
     *
     * @param isFocused the is focused
     */
    public void setFocused(boolean isFocused) {}

    /**
     * Save state parcelable.
     *
     * @return the parcelable
     */
    public Parcelable saveState() { return null; }

    /**
     * Restore state.
     *
     * @param state the state
     */
    public void restoreState(Parcelable state) { }

}
