package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;

public class PaymentSystemsViewHolder extends RecyclerView.ViewHolder {

    private MainRecyclerViewAdapter.MainRecyclerViewAdapterListener mListener;

    public PaymentSystemsViewHolder(View view, MainRecyclerViewAdapter.MainRecyclerViewAdapterListener listener) {
        super(view);

        mListener = listener;
    }

    public void bind() {

        // Configure paymentSystemsViewHolderLayout
        RelativeLayout paymentSystemsViewHolderLayout = itemView.findViewById(R.id.paymentSystemsViewHolderLayout);

        paymentSystemsViewHolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.paymentSystemViewHolderClicked();
            }
        });
    }

}
