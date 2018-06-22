package company.tap.gosellapi.internal.viewholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CardSystemsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {
    private ImageButton cardScannerButton;
    private View addressOnCardLayout;
    private SwitchCompat saveCardSwitch;
    private ArrayList<PaymentOption> data;

    CardCredentialsViewHolder(View view) {
        super(view);
        cardScannerButton = itemView.findViewById(R.id.cardScannerButton);
        addressOnCardLayout = itemView.findViewById(R.id.addressOnCardLayout);
        saveCardSwitch = itemView.findViewById(R.id.saveCardSwitch);
    }

    @Override
    public void bind(ArrayList<PaymentOption> data) {
        this.data = data;

        cardScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewModel.cardScannerButtonClicked();
                updateCardSystemsRecyclerView();
            }
        });

        addressOnCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addressOnCardClicked();
            }
        });


        saveCardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.saveCardSwitchClicked(isChecked);
            }
        });

        RelativeLayout saveCardSwitchContainer = itemView.findViewById(R.id.saveCardSwitchContainer);
        saveCardSwitchContainer.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewModel.setCardSwitchHeight(saveCardSwitchContainer.getMeasuredHeight());

        initCardSystemsRecyclerView();
    }

    @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }

    private void initCardSystemsRecyclerView() {
        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL,false);
        cardSystemsRecyclerView.setLayoutManager(linearLayoutManager);

        CardSystemsRecyclerViewAdapter adapter = new CardSystemsRecyclerViewAdapter(data);
        cardSystemsRecyclerView.setAdapter(adapter);
    }

    private void updateCardSystemsRecyclerView() {
        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);
        cardSystemsRecyclerView.getAdapter().notifyItemRemoved(0);
    }

    public void updateAddressOnCardView(boolean isShow) {
        if(isShow) {
            addressOnCardLayout.setVisibility(View.VISIBLE);
        }
        else {
            addressOnCardLayout.setVisibility(View.GONE);
        }
    }
}