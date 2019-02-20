package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;

/**
 * The type Payment options recycler view adapter.
 */
public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder> {

    private PaymentOptionsDataManager dataSource;

    /**
     * Instantiates a new Payment options recycler view adapter.
     *
     * @param dataSource the data source
     */
    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataManager dataSource) {

        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public PaymentOptionsBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("  onCreateViewHolder >>>>  viewType >> "+ viewType);
        return PaymentOptionsBaseViewHolder.newInstance(parent, PaymentOptionsBaseViewHolder.ViewHolderType.getByViewType(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder holder, int position) {
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
