package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.AddressFieldInputType;
import company.tap.gosellapi.internal.api.models.AddressField;
import company.tap.gosellapi.internal.api.models.BillingAddressField;

public class AddressOnCardRecyclerViewAdapter extends RecyclerView.Adapter {
    private static final int VH_TYPE_DROPDOWN = 0;
    private static final int VH_TYPE_NUMBER = 1;
    private static final int VH_TYPE_TEXT = 2;

    public interface AddressonCardRecyclerViewInterface {
        void countryDropdownClicked();
    }

    private ArrayList<BillingAddressField> datasource;
    private ArrayList<AddressField> fields;
    private ArrayList<String> inputFieldsText;

    private AddressonCardRecyclerViewInterface listener;
    private String currentCountry;

    public AddressOnCardRecyclerViewAdapter(String currentCountry, ArrayList<BillingAddressField> datasource, ArrayList<AddressField> fields, AddressonCardRecyclerViewInterface listener) {
        this.datasource = datasource;
        this.fields = fields;
        this.listener = listener;
        this.currentCountry = currentCountry;
        this.inputFieldsText = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VH_TYPE_DROPDOWN:
                View dropdown = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_address_dropdown, parent, false);
                return new CardAddressDropdownViewHolder(dropdown);

            case VH_TYPE_NUMBER:
                View number = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_address_number, parent, false);
                return new CardAddressNumberInputViewHolder(number);

            case VH_TYPE_TEXT:
                default:
                View text = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_address_text, parent, false);
                return new CardAddressTextInputViewHolder(text);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BillingAddressField billingAddressField = datasource.get(position);

        String placeholder= "";
        for(AddressField field : fields) {
            if(billingAddressField.getName().equals(field.getName())) {
                placeholder = field.getPlaceholder();
                break;
            }
        }

        if(holder instanceof CardAddressDropdownViewHolder) {

            if(billingAddressField.getName().equals("country")) {

                Locale locale = new Locale("", currentCountry);
                String countryName = locale.getDisplayCountry(Locale.ENGLISH);

                ((CardAddressDropdownViewHolder) holder).tvTitle.setText(placeholder);
                ((CardAddressDropdownViewHolder) holder).tvValue.setText(countryName);
            }
        }

        if(holder instanceof CardAddressNumberInputViewHolder) {
            ((CardAddressNumberInputViewHolder) holder).tilNumberInput.setHint(placeholder);


        }

        if(holder instanceof CardAddressTextInputViewHolder) {
            ((CardAddressTextInputViewHolder) holder).tilTextInput.setHint(placeholder);


        }
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    @Override
    public int getItemViewType(int position) {

        BillingAddressField billingAddressField = datasource.get(position);

        AddressFieldInputType type = AddressFieldInputType.TEXT;
        for(AddressField field : fields) {
            if(billingAddressField.getName().equals(field.getName())) {
                type = field.getType();
                break;
            }
        }

        switch (type) {
            case TEXT:
                return VH_TYPE_TEXT;

            case NUMBER:
                return VH_TYPE_NUMBER;

            case DROPDOWN:
                return VH_TYPE_DROPDOWN;

            default:
                return 0;
        }
    }

    private class CardAddressDropdownViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvValue;

        public CardAddressDropdownViewHolder(View itemView)
        {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvValue = itemView.findViewById(R.id.tvValue);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // Temporary
            listener.countryDropdownClicked();
        }
    }

    private class CardAddressTextInputViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

        EditText etTextInput;
        TextInputLayout tilTextInput;

        public CardAddressTextInputViewHolder(View itemView) {
            super(itemView);

            etTextInput = itemView.findViewById(R.id.etTextInput);
            tilTextInput = itemView.findViewById(R.id.tilTextInput);

            etTextInput.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            inputFieldsText.add(s.toString());
        }
    }

    private class CardAddressNumberInputViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

        EditText etNumberInput;
        TextInputLayout tilNumberInput;

        public CardAddressNumberInputViewHolder(View itemView) {
            super(itemView);

            etNumberInput = itemView.findViewById(R.id.etNumberInput);
            tilNumberInput = itemView.findViewById(R.id.tilNumberInput);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            inputFieldsText.add(s.toString());
        }
    }
}
