package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {
    private Button cardScannerButton;

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
    }

    @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }
}