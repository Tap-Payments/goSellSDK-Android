package company.tap.gosellapi.internal.viewholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CardSystemsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {
    private ImageButton cardScannerButton;

    CardCredentialsViewHolder(View view) {
        super(view);
        cardScannerButton = itemView.findViewById(R.id.cardScannerButton);
    }

    @Override
    public void bind(ArrayList<PaymentOption> data) {
        cardScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.cardScannerButtonClicked();
            }
        });

        initCardSystemsRecyclerView(data);
    }

    @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }

    private void initCardSystemsRecyclerView(ArrayList<PaymentOption> data) {
        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL,false);
        cardSystemsRecyclerView.setLayoutManager(linearLayoutManager);

        CardSystemsRecyclerViewAdapter adapter = new CardSystemsRecyclerViewAdapter(data);
        cardSystemsRecyclerView.setAdapter(adapter);
    }
}