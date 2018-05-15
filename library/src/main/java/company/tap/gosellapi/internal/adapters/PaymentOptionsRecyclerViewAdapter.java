package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentType;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder> {
    private PaymentOptionsDataManager dataSource;

    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataManager dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public PaymentOptionsBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PaymentOptionsBaseViewHolder.newInstance(parent, PaymentType.getByViewType(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.attachToViewModel(dataSource.getViewModel(position), position);
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getItemViewType(position);
    }

    @Override
    public void onViewRecycled(@NonNull PaymentOptionsBaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.detachFromViewModel();
    }
}
