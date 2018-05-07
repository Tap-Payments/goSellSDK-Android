package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsDataSource;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentType;
import company.tap.gosellapi.internal.view_holders.PaymentOptionsBaseViewHolder;

public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder> {
    public interface PaymentOptionsViewAdapterListener {
        void cardScannerButtonClicked();
        void saveCardSwitchCheckedChanged();
        void paymentSystemViewHolderClicked();
        void recentPaymentItemClicked();
    }

    private PaymentOptionsDataSource dataSource;
    private PaymentOptionsViewAdapterListener listener;

    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataSource dataSource, PaymentOptionsViewAdapterListener listener) {
        this.dataSource = dataSource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaymentOptionsBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PaymentOptionsBaseViewHolder.newInstance(parent, PaymentType.getByViewType(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(dataSource.getDataList().get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getItemViewType(position);
    }
}
