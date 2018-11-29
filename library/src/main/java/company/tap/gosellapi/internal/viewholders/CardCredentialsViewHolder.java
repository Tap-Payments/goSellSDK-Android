package company.tap.gosellapi.internal.viewholders;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CardSystemsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidationState;
import company.tap.tapcardvalidator_android.CardValidator;
import company.tap.tapcardvalidator_android.DefinedCardBrand;

public class CardCredentialsViewHolder
        extends PaymentOptionsBaseViewHolder<CardCredentialsViewModelData, CardCredentialsViewHolder, CardCredentialsViewModel> {

    public interface Data {

        void bindCardNumberFieldWithWatcher(EditText cardNumberField);

        SpannableString getCardNumberText();
        String getExpirationDateText();
        String getCVVText();
        String getCardholderNameText();
    }

    private final static int BIN_NUMBER_LENGTH = 6;
    private final static int NAME_ON_CARD_MAX_LENGTH = 26;

    private EditText cardNumberField;
    private ImageButton cardScannerButton;

    private EditText expirationDateField;
    private EditText cvvField;

    private EditText nameOnCardField;

    private TextInputLayout addressOnCardLayout;
    private EditText addressOnCardField;

    private ConstraintLayout saveCardLayout;
    private TextView saveCardDescriptionTextView;
    private Switch saveCardSwitch;

    private EditText getAddressOnCardField() {

        return addressOnCardField;
    }

    CardCredentialsViewHolder(View view) {

        super(view);

        cardNumberField = itemView.findViewById(R.id.cardNumberField);

        cardScannerButton = itemView.findViewById(R.id.cardScannerButton);

        expirationDateField = itemView.findViewById(R.id.expirationDateField);
        cvvField = itemView.findViewById(R.id.cvvField);

        nameOnCardField = itemView.findViewById(R.id.cardholderNameField);

        addressOnCardLayout = itemView.findViewById(R.id.addressOnCardContainer);
        addressOnCardField = itemView.findViewById(R.id.addressOnCardTextView);
        setupAddressOnCardField();

        saveCardLayout = itemView.findViewById(R.id.saveCardContainer);
        saveCardDescriptionTextView = itemView.findViewById(R.id.saveCardDescriptionTextView);
        saveCardSwitch = itemView.findViewById(R.id.saveCardSwitch);

        addressOnCardField.setText("Address will be displayed here, probably in multiple lines.");
//
//
//        addressOnCardLayout = itemView.findViewById(R.id.addressOnCardLayout);
//        saveCardSwitch = itemView.findViewById(R.id.saveCardSwitch);
//
//        // Configure edit fields
//        cardNumberField = itemView.findViewById(R.id.cardNumberField);
//
//
//        // Card number field
//        cardNumberField.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                DefinedCardBrand brand = validateCardNumber(s.toString());
//                CardBrand cardBrand = brand.getCardBrand();
//
//                int[] spacings;
//                if (cardBrand != CardBrand.americanExpress) {
//                    spacings = new int[]{4, 8, 12};
//                } else {
//                    spacings = new int[]{3, 9};
//                }
//
//                String text = s.toString();
//                text = text.replace(" ", "");
//                SpannableStringBuilder cardNumber = new SpannableStringBuilder(text);
//
//                for (int i = spacings.length - 1; i >= 0; i--) {
//
//                    int space = spacings[i];
//
//                    if (space < text.length()) {
//                        cardNumber.insert(space, " ");
//                    }
//                }
//
//                cardNumberField.removeTextChangedListener(this);
//                cardNumberField.setText(cardNumber.toString());
//
//                int selectionIndex = start + count;
//
//                for (int i = 0; i < spacings.length; i++) {
//
//                    int space = spacings[i];
//
//                    int incrementedSpace = space + i;
//
//                    if (start <= incrementedSpace && incrementedSpace < start + count) {
//                        selectionIndex++;
//                    }
//
//                    if (start - 1 == incrementedSpace && count == 0) {
//                        selectionIndex--;
//                    }
//                }
//
//                try {
//                    cardNumberField.setSelection(selectionIndex);
//                } catch (Exception e) {
//                    cardNumberField.setSelection(cardNumber.length());
//                }
//
//                cardNumberField.addTextChangedListener(this);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        // CVVField
//        CVVField = itemView.findViewById(R.id.CVVField);
//        CVVField.setTypeface(Typeface.DEFAULT);
//        CVVField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        CVVField.setTransformationMethod(new PasswordTransformationMethod());
//
//        CVVField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    CVVField.setText("");
//                }
//            }
//        });

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
//
//        // Date field
//        expirationDateField = itemView.findViewById(R.id.expirationDateField);
//
//        expirationDateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    viewModel.cardExpirationDateClicked();
//                    expirationDateField.clearFocus();
//                }
//            }
//        });
//
//        // Name on card
//        nameOnCardField = itemView.findViewById(R.id.nameOnCardField);
//
//        nameOnCardField.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(NAME_ON_CARD_MAX_LENGTH),
//        new InputFilter() {
//
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//                boolean keepOriginal = true;
//
//                StringBuilder sb = new StringBuilder(end - start);
//
//                for (int i = start; i < end; i++) {
//                    char c = source.charAt(i);
//
//                    if (isCharAllowed(c)) // put your condition here
//                        sb.append(c);
//                    else
//                        keepOriginal = false;
//                }
//
//                if (keepOriginal)
//                    return null;
//                else {
//                    if (source instanceof Spanned) {
//                        SpannableString sp = new SpannableString(sb);
//                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
//                        return sp;
//                    } else {
//                        return sb;
//                    }
//                }
//            }
//
//            private boolean isCharAllowed(char c) {
//                return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
//            }
//
//        } });
    }

    @Override
    public void bind(CardCredentialsViewModelData data) {

        viewModel.bindCardNumberFieldWithWatcher(cardNumberField);

//        cardScannerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.cardScannerButtonClicked();
//            }
//        });
//
//        addressOnCardLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.addressOnCardClicked();
//            }
//        });
//
//        saveCardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                viewModel.saveCardSwitchClicked(isChecked);
//            }
//        });
//
//        if (!viewModel.getCardNumber().isEmpty()) {
//            cardNumberField.setText(viewModel.getCardNumber());
//        }
//
//        if (!viewModel.getExpirationMonth().isEmpty() && !viewModel.getExpirationYear().isEmpty()) {
//            String expirationDate = viewModel.getExpirationMonth() + "/" + String.format("%02d", Integer.valueOf(viewModel.getExpirationYear()) % 100);
//            expirationDateField.setText(expirationDate);
//        }
//
//        if (viewModel.isShowAddressOnCardCell()) {
//            addressOnCardLayout.setVisibility(View.VISIBLE);
//        } else {
//            addressOnCardLayout.setVisibility(View.GONE);
//        }
////        if (!viewModel.getCVVnumber().isEmpty()) {
////            CVVField.setText(viewModel.getCVVnumber());
////        }
//
////        if (!viewModel.getNameOnCard().isEmpty()) {
////            nameOnCardField.setText(viewModel.getNameOnCard());
////        }
//
//        RelativeLayout saveCardSwitchContainer = itemView.findViewById(R.id.saveCardSwitchContainer);
//        saveCardSwitchContainer.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        viewModel.setCardSwitchHeight(saveCardSwitchContainer.getMeasuredHeight());
//
//        initCardSystemsRecyclerView(data.getPaymentOptions());
    }

    @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }

    private void initCardSystemsRecyclerView(ArrayList<PaymentOption> paymentOptions) {

        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        cardSystemsRecyclerView.setLayoutManager(linearLayoutManager);

        CardSystemsRecyclerViewAdapter adapter = new CardSystemsRecyclerViewAdapter(paymentOptions);
        cardSystemsRecyclerView.setAdapter(adapter);
    }

    private void updateCardSystemsRecyclerView(CardBrand brand) {
        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);

        CardSystemsRecyclerViewAdapter adapter = (CardSystemsRecyclerViewAdapter) cardSystemsRecyclerView.getAdapter();
        adapter.updateForCardBrand(brand);
    }

    public void updateAddressOnCardView(boolean isShow) {
        if (isShow) {
            addressOnCardLayout.setVisibility(View.VISIBLE);
        } else {
            addressOnCardLayout.setVisibility(View.GONE);
        }
    }

    private DefinedCardBrand validateCardNumber(String cardNumber) {

        cardNumber = cardNumber.replace(" ", "");

        DefinedCardBrand brand = CardValidator.validate(cardNumber);
        updateCardSystemsRecyclerView(brand.getCardBrand());

        if (brand.getValidationState().equals(CardValidationState.invalid)) {
            cardNumberField.setTextColor(itemView.getResources().getColor(R.color.red));
        } else {
            cardNumberField.setTextColor(itemView.getResources().getColor(R.color.greyish_brown));
        }

        if (cardNumber.length() == BIN_NUMBER_LENGTH) {
            viewModel.binNumberEntered(cardNumber);
        }

        return brand;
    }

    private void setupAddressOnCardField() {

        getAddressOnCardField().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                int topPadding      = s.length() > 0 ? 16 : 0;
                float scale         = itemView.getResources().getDisplayMetrics().density;
                topPadding          = (int) (topPadding * scale + 0.5);

                int leftPadding     = getAddressOnCardField().getPaddingLeft();
                int rightPadding    = getAddressOnCardField().getPaddingRight();
                int bottomPadding   = getAddressOnCardField().getPaddingBottom();

                getAddressOnCardField().setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
            }
        });
    }

    private void validateCardCVV(String CVVNumber) {

    }

    private static class TrackingSpan extends ReplacementSpan {

        private float mTrackingPx;

        public TrackingSpan(float tracking) {
            mTrackingPx = tracking;
        }

        @Override
        public int getSize(Paint paint, CharSequence text,
                           int start, int end, Paint.FontMetricsInt fm) {
            return (int) (paint.measureText(text, start, end)
                    + mTrackingPx * (end - start - 1));
        }

        @Override
        public void draw(Canvas canvas, CharSequence text,
                         int start, int end, float x, int top, int y,
                         int bottom, Paint paint) {
            float dx = x;
            for (int i = start; i < end; i++) {
                canvas.drawText(text, i, i + 1, dx, y, paint);
                dx += paint.measureText(text, i, i + 1) + mTrackingPx;
            }
        }
    }
}