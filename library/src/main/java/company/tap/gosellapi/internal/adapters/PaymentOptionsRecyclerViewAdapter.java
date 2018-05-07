package company.tap.gosellapi.internal.adapters;

        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.ViewGroup;

        import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsDataSource;
        import company.tap.gosellapi.internal.data_source.payment_options.PaymentType;
        import company.tap.gosellapi.internal.view_holders.PaymentOptionsBaseViewHolder;

public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder> implements PaymentOptionsBaseViewHolder.PaymentOptionsViewHolderFocusedStateInterface {
    public interface PaymentOptionsViewAdapterListener {
        void currencyHolderClicked();
        void recentPaymentItemClicked();
        void webPaymentSystemViewHolderClicked();
        void cardScannerButtonClicked();
        void saveCardSwitchCheckedChanged();
        void cardDetailsFilled(boolean isFilled);
    }

    private PaymentOptionsDataSource dataSource;
    private PaymentOptionsViewAdapterListener listener;

    private final static int NO_FOCUS = -1;
    private int focusedPosition = NO_FOCUS;

    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataSource dataSource, PaymentOptionsViewAdapterListener listener) {
        this.dataSource = dataSource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaymentOptionsBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PaymentOptionsBaseViewHolder.newInstance(parent, PaymentType.getByViewType(viewType), this);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(dataSource.getDataList().get(position), position == focusedPosition);
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getItemViewType(position);
    }


    //focus interaction between holders
    @Override
    public void setFocused(int position) {
        int oldPosition = focusedPosition;
        focusedPosition = position;
        if (oldPosition != NO_FOCUS) {
            notifyItemChanged(oldPosition);
        }
        notifyItemChanged(focusedPosition);
    }

    public void clearFocus() {
        focusedPosition = NO_FOCUS;
        notifyDataSetChanged();
    }
}
