package company.tap.sample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import company.tap.sample.R;
import company.tap.sample.viewholders.CustomerViewHolder;
import company.tap.sample.viewmodels.CustomerViewModel;

public class CustomerAdapter extends RecyclerView.Adapter {

    List<CustomerViewModel> customers ;
    OnClickListenerInterface listener ;


    public CustomerAdapter(List<CustomerViewModel> customers, OnClickListenerInterface listener){
        this.customers = customers;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new CustomerViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((CustomerViewHolder)viewHolder).bindData(getCustomer(i));
    }


    private CustomerViewModel getCustomer(int index){
        return this.customers.get(index);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.customer_layout;
    }

    public interface OnClickListenerInterface {
        void onClick(CustomerViewModel customerViewModel);
    }
}
