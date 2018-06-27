package company.tap.gosellapi.internal.viewholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CardSystemsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.viewmodels.CardCredentialsViewModel;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidator;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<ArrayList<PaymentOption>, CardCredentialsViewHolder, CardCredentialsViewModel> {

    ImageButton cardScannerButton;
    private View addressOnCardLayout;
    private SwitchCompat saveCardSwitch;
    private ArrayList<PaymentOption> data;

    private EditText cardNumberField;
    private EditText CVVField;
    private EditText dateField;
    private EditText nameOnCard;

    CardCredentialsViewHolder(View view) {
        super(view);

        cardScannerButton = itemView.findViewById(R.id.cardScannerButton);
        addressOnCardLayout = itemView.findViewById(R.id.addressOnCardLayout);
        saveCardSwitch = itemView.findViewById(R.id.saveCardSwitch);

        // Configure edit fields

        // Card number field
//        cardNumberField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        // CVVField
//        CVVField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        // Date field
//        dateField.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        // Name on card
//        nameOnCard.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
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

    private void openDatePicker() {

    }

    private void validateCardNumber(String cardNumber) {

        CardValidator.validate(cardNumber);
    }
}