package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsDataSource;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentType;
import company.tap.gosellapi.internal.view_holders.PaymentOptionsBaseViewHolder;

public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder> implements PaymentOptionsBaseViewHolder.PaymentOptionsViewHolderFocusedStateInterface {
    public interface PaymentOptionsViewAdapterListener {
        RecyclerView.ViewHolder getHolderForAdapterPosition(int position);

        void currencyHolderClicked();
        void recentPaymentItemClicked(int position, Card recentItem);
        void webPaymentSystemViewHolderClicked(int position);
        void cardScannerButtonClicked();
        void saveCardSwitchCheckedChanged(int position, boolean isChecked);
        void cardDetailsFilled(boolean isFilled, CardRawData cardRawData); //pass null, if isFilled = false
    }

    private int focusedPosition = Constants.NO_FOCUS;

    private PaymentOptionsDataSource dataSource;
    private PaymentOptionsViewAdapterListener listener;

    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataSource dataSource, PaymentOptionsViewAdapterListener listener) {
        this.dataSource = dataSource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaymentOptionsBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PaymentOptionsBaseViewHolder.newInstance(parent, PaymentType.getByViewType(viewType), listener, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(dataSource.getDataList().get(position), position == focusedPosition, position);
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getItemViewType(position);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PaymentOptionsBaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    //focus interaction between holders
    @Override
    public void setFocused(int position) {
        PaymentOptionsBaseViewHolder oldHolder;

        if (focusedPosition != Constants.NO_FOCUS) {
            oldHolder = (PaymentOptionsBaseViewHolder) listener.getHolderForAdapterPosition(focusedPosition);
            if (oldHolder != null) {
                oldHolder.setFocused(false);
            }
        }

        focusedPosition = position;
        PaymentOptionsBaseViewHolder newHolder = (PaymentOptionsBaseViewHolder) listener.getHolderForAdapterPosition(focusedPosition);
        if (newHolder != null) {
            newHolder.setFocused(true);
        }
    }

    public void clearFocus() {
        if (focusedPosition != Constants.NO_FOCUS) {
            PaymentOptionsBaseViewHolder oldHolder = (PaymentOptionsBaseViewHolder) listener.getHolderForAdapterPosition(focusedPosition);
            if (oldHolder != null) {
                oldHolder.setFocused(false);
            }
        }
        focusedPosition = Constants.NO_FOCUS;
    }
}
