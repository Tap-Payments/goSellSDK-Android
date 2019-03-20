package company.tap.sample.viewholders;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import company.tap.sample.R;
import company.tap.sample.adapters.CustomerAdapter;
import company.tap.sample.viewmodels.CustomerViewModel;

public class CustomerViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView email;
    private TextView mobile;

    private ImageView right_arrow;

    private CustomerAdapter.OnClickListenerInterface listenerInterface;
    private CustomerViewModel viewModel;


    public CustomerViewHolder(@NonNull View itemView, CustomerAdapter.OnClickListenerInterface listener) {
        super(itemView);
        this.listenerInterface = listener;
        name    =   itemView.findViewById(R.id.customer_name);
        email   =   itemView.findViewById(R.id.customer_email);
        mobile  =   itemView.findViewById(R.id.customer_mobile);


        ConstraintLayout customer_container = itemView.findViewById(R.id.customer_container);
        customer_container.setOnClickListener(v -> {
            if(listenerInterface!=null){
                listenerInterface.onClick(viewModel);
            }
        });
     }

    public void bindData(CustomerViewModel viewModel){
         this.viewModel = viewModel;
        name.setText(viewModel.getName());
        email.setText(viewModel.getEmail());
        mobile.setText(viewModel.getMobile());
    }




}
