package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder1;

/**
 * The type Payment options recycler view adapter 1.
 */
public class PaymentOptionsRecyclerViewAdapter1 extends RecyclerView.Adapter<PaymentOptionsBaseViewHolder1> {

  private final PaymentOptionsDataManager dataSource;

    /**
     * Instantiates a new Payment options recycler view adapter 1.
     *
     * @param dataSource the data source
     */
    public PaymentOptionsRecyclerViewAdapter1(PaymentOptionsDataManager dataSource) {
    this.dataSource = dataSource;
  }

  @NonNull
  @Override
  public PaymentOptionsBaseViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Log.d(" >> onCreateViewHolder",viewType+"");
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull PaymentOptionsBaseViewHolder1 paymentOptionsBaseViewHolder1,
                               int i) {
    Log.d(" >> onBindViewHolder","");
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  @Override
  public int getItemViewType(int position) {

    return position;
  }
}
