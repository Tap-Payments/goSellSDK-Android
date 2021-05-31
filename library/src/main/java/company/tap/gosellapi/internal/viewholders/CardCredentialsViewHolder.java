package company.tap.gosellapi.internal.viewholders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.CardSystemsRecyclerViewAdapter;
import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.api.enums.Permission;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
import company.tap.gosellapi.internal.custom_views.CvvEditText;
import company.tap.gosellapi.internal.custom_views.ExpirationDateEditText;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.internal.utils.ActivityDataExchanger;
import company.tap.gosellapi.internal.utils.CardType;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.data_manager.PaymentDataSource;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidationState;
import company.tap.tapcardvalidator_android.CardValidator;
import company.tap.tapcardvalidator_android.DefinedCardBrand;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static company.tap.gosellapi.open.enums.CardType.ALL;

/**
 * The type Card credentials view holder.
 */
public class CardCredentialsViewHolder
    extends PaymentOptionsBaseViewHolder<CardCredentialsViewModelData, CardCredentialsViewHolder, CardCredentialsViewModel> {


    /**
     * Sets card number color.
     *
     * @param color the color
     */
    public void setCardNumberColor(int color) {
    cardNumberField.setTextColor(color);
  }

    /**
     * The interface Data.
     */
    public interface Data {
        /**
         * Bind card number field with watcher.
         *
         * @param cardNumberField the card number field
         */
        void bindCardNumberFieldWithWatcher(EditText cardNumberField);

        /**
         * Gets card number text.
         *
         * @return the card number text
         */
        SpannableString getCardNumberText();

        /**
         * Gets expiration date text.
         *
         * @return the expiration date text
         */
        String getExpirationDateText();

        /**
         * Gets cvv text.
         *
         * @return the cvv text
         */
        String getCVVText();

        /**
         * Gets cardholder name text.
         *
         * @return the cardholder name text
         */
        String getCardholderNameText();
    }

    private final static int BIN_NUMBER_LENGTH = 6;
    private final static int NAME_ON_CARD_MAX_LENGTH = 26;

    private TextInputLayout cardNumberFieldTextInputLayout;
    private EditText cardNumberField;

    private ImageButton cardScannerButton;

    private TextInputLayout expirationDateFieldTextInputLayout;
    private ExpirationDateEditText expirationDateField;

    private TextInputLayout cvvFieldTextInputLayout;
    private CvvEditText cvvField;

    private TextInputLayout nameOnCardFieldTextInputLayout;
    private EditText nameOnCardField;


    private TextInputLayout addressOnCardLayout;
    private EditText addressOnCardField;

    private ConstraintLayout saveCardLayout;
    private TextView saveCardDescriptionTextView;
    private Switch saveCardSwitch;

    private RecyclerView cardSystemsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CardCredentialsViewModel viewModel;
    private CardCredentialsTextWatcher cardCredentialsTextWatcher;


    /**
     * Instantiates a new Card credentials view holder.
     *
     * @param view the view
     */
    CardCredentialsViewHolder(View view) {

        super(view);

        cardNumberFieldTextInputLayout=itemView.findViewById(R.id.cardNumberFieldTextInputLayout);
        cvvFieldTextInputLayout=itemView.findViewById(R.id.cvvFieldContainer);
        expirationDateFieldTextInputLayout=itemView.findViewById(R.id.expirationDateContainer);
        nameOnCardFieldTextInputLayout=itemView.findViewById(R.id.cardholderNameContainer);

        cardCredentialsTextWatcher = new CardCredentialsTextWatcher();

        viewModel = ActivityDataExchanger.getInstance().getCardCredentialsViewModel();
/////////////////////////////////////////////////// CARD NUMBER START ///////////////////////////////////////////////////////
        cardNumberField = itemView.findViewById(R.id.cardNumberField);

         viewModel.ViewHolderReference(this);

         cardNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              PaymentDataManager.getInstance().getPaymentOptionsDataManager().clearFocus();
                String str =s.toString();

                DefinedCardBrand brand = validateCardNumber(str);

                CardBrand cardBrand = brand.getCardBrand();

                int[] spacings;
                if (cardBrand != CardBrand.americanExpress) {
                    spacings = new int[]{4, 8, 12};
                } else {
                    spacings = new int[]{4, 10};
//                    spacings = new int[]{4, 8,13};
                }

                String text = str;
                text = text.replace(" ", "");
                SpannableStringBuilder cardNumber = new SpannableStringBuilder(text);

                for (int i = spacings.length - 1; i >= 0; i--) {

                    int space = spacings[i];

                    if (space < text.length()) {
                        cardNumber.insert(space, " ");
                    }
                }

                cardNumberField.removeTextChangedListener(this);
                cardNumberField.setText(cardNumber.toString());

                int selectionIndex = start + count;

                for (int i = 0; i < spacings.length; i++) {

                    int space = spacings[i];

                    int incrementedSpace = space + i;

                    if (start <= incrementedSpace && incrementedSpace < start + count) {
                        selectionIndex++;
                    }

                    if (start - 1 == incrementedSpace && count == 0) {
                        selectionIndex--;
                    }
                }

                try {
                    cardNumberField.setSelection(selectionIndex);
                } catch (Exception e) {
                    cardNumberField.setSelection(cardNumber.length());
                }

                cardNumberField.addTextChangedListener(this);

                if(text.length()== BIN_NUMBER_LENGTH){
                    viewModel.binNumberEntered(text);
                }

                if(text.length() < BIN_NUMBER_LENGTH || text.length() == 0){
                  PaymentDataManager.getInstance().setBinLookupResponse(null);
                }

                BINLookupResponse binLookupResponse =  PaymentDataManager.getInstance().getBinLookupResponse();
                viewModel.setPaymentOption(cardBrand,binLookupResponse==null?null:binLookupResponse.getScheme());
            }

           @Override
            public void afterTextChanged(Editable s) {
             viewModel.cardDetailsFilled(validateCardFields(),viewModel);
             viewModel.checkShakingStatus();
            }
        });

         if(view!=null)
         setupRTL(view.getContext());

/////////////////////////////////////////////////// CARD SCANNER START ///////////////////////////////////////////////////////
//        viewModel.bindCardNumberFieldWithWatcher(cardNumberField);

          cardScannerButton = itemView.findViewById(R.id.cardScannerButton);

/////////////////////////////////////////////////// CARD EXPIRATION_DATE START ///////////////////////////////////////////////////////
        expirationDateField = itemView.findViewById(R.id.expirationDateField);
        // enable Expiration date dialog
        expirationDateField.useDialogForExpirationDateEntry((Activity) view.getContext(), false);

/////////////////////////////////////////////////// CVV START ///////////////////////////////////////////////////////
        cvvField = itemView.findViewById(R.id.cvvField);
        cvvField.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus ){
                cvvField.setText("");
//                    isFirstTimeGetFocused = false;
            }
        }) ;
/////////////////////////////////////////////////// NAME ON CARD START ///////////////////////////////////////////////////////
        // Name on card
        nameOnCardField = itemView.findViewById(R.id.cardholderNameField);

        nameOnCardField.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(NAME_ON_CARD_MAX_LENGTH),
            new InputFilter() {

                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dest_start, int dest_end) {

                    boolean keepOriginal = true;

                    StringBuilder sb = new StringBuilder(end - start);

                    for (int i = start; i < end; i++) {
                        char c = source.charAt(i);

                        if (isCharAllowed(c)) // put your condition here
                            sb.append(c);
                        else
                            keepOriginal = false;
                    }

                    if (keepOriginal)
                        return null;
                    else {
                        if (source instanceof Spanned) {
                            SpannableString sp = new SpannableString(sb);
                            TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                            return sp;
                        } else {
                            return sb;
                        }
                    }
                }

                private boolean isCharAllowed(char c) {
                    return Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || isDotChar(c);
                }

                private boolean isDotChar(char c){
                    return c == '.';
                }

            } });

/////////////////////////////////////////////////// ADDRESS ON CARD START ///////////////////////////////////////////////////////
        addressOnCardLayout = itemView.findViewById(R.id.addressOnCardContainer);
        addressOnCardField = itemView.findViewById(R.id.addressOnCardTextView);
        addressOnCardField.setText("Address will be displayed here, probably in multiple lines.");
        setupAddressOnCardField();

/////////////////////////////////////////////////// SAVE CARD START ///////////////////////////////////////////////////////
        saveCardLayout = itemView.findViewById(R.id.saveCardContainer);
        saveCardDescriptionTextView = itemView.findViewById(R.id.saveCardDescriptionTextView);
        saveCardSwitch = itemView.findViewById(R.id.saveCardSwitch);

        if (PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() == TransactionMode.SAVE_CARD){
            saveCardSwitch.setVisibility(View.GONE);
            saveCardDescriptionTextView.setText(itemView.getResources().getString(R.string.textview_disclaimer_save_card_info));
            TextViewCompat.setTextAppearance(saveCardDescriptionTextView,R.style.SecurityText);
        }


        /// stop hiding save card switch in case of TransactionMode.TOKENIZE_CARD.
//        if(PaymentDataManager.getInstance().getPaymentOptionsRequest().getTransactionMode() == TransactionMode.TOKENIZE_CARD
//                ||
//                (PaymentDataManager.getInstance().getExternalDataSource()!= null && !PaymentDataManager.getInstance().getExternalDataSource().getAllowedToSaveCard())
//        )
        if( (PaymentDataManager.getInstance().getExternalDataSource()!= null && !PaymentDataManager.getInstance().getExternalDataSource().getAllowedToSaveCard()) )
        {
            saveCardSwitch.setVisibility(View.GONE);
            saveCardDescriptionTextView.setText("");

        }else if(PaymentDataManager.getInstance().getSDKSettings() != null &&
           PaymentDataManager.getInstance().getSDKSettings().getData() != null){
           if(PaymentDataManager.getInstance().getSDKSettings().getData().getPermissions() == null){
               saveCardSwitch.setVisibility(View.GONE);
               saveCardDescriptionTextView.setText("");
           }else if(!PaymentDataManager.getInstance().getSDKSettings().getData().getPermissions().contains(Permission.MERCHANT_CHECKOUT)){
               saveCardSwitch.setVisibility(View.GONE);
               saveCardDescriptionTextView.setText("");
           }
        }

/////////////////////////////////////////////////// SETUP CARD PAYMENT OPTIONS START ///////////////////////////////////////////////////////
        initCardSystemsRecyclerView(getPaymentOption());
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        setupCardTheme();
}


        private void setupCardTheme(){

        if(ThemeObject.getInstance().getCardInputFontTypeFace()!=null) {
            cardNumberField.setTypeface(ThemeObject.getInstance().getCardInputFontTypeFace());
            expirationDateField.setTypeface(ThemeObject.getInstance().getCardInputFontTypeFace());
            cvvField.setTypeface(ThemeObject.getInstance().getCardInputFontTypeFace());
            nameOnCardField.setTypeface(ThemeObject.getInstance().getCardInputFontTypeFace());
            saveCardDescriptionTextView.setTypeface(ThemeObject.getInstance().getCardInputFontTypeFace());
        }
            if(ThemeObject.getInstance().getCardInputTextColor()!=0) {
                cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                expirationDateField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                cvvField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                nameOnCardField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                saveCardDescriptionTextView.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
            }
            if(ThemeObject.getInstance().getCardInputPlaceholderTextColor()!=0) {
                setHintTextColor(cardNumberFieldTextInputLayout, ThemeObject.getInstance().getCardInputPlaceholderTextColor());
                setHintTextColor(cvvFieldTextInputLayout, ThemeObject.getInstance().getCardInputPlaceholderTextColor());
                setHintTextColor(expirationDateFieldTextInputLayout, ThemeObject.getInstance().getCardInputPlaceholderTextColor());
                setHintTextColor(nameOnCardFieldTextInputLayout, ThemeObject.getInstance().getCardInputPlaceholderTextColor());
            }
            if(ThemeObject.getInstance().getSaveCardSwitchOffThumbTint()!=0 && ThemeObject.getInstance().getSaveCardSwitchOnThumbTint()!=0&&ThemeObject.getInstance().getSaveCardSwitchOffTrackTint()!=0&&
                    ThemeObject.getInstance().getSaveCardSwitchOnTrackTint()!=0 ){
                configureSaveCardSwitch();

            }

            if(itemView!=null && itemView.getContext()!=null)
                if(ThemeObject.getInstance().getScanIconDrawable(itemView.getContext())!=null)
                    cardScannerButton.setImageDrawable(ThemeObject.getInstance().getScanIconDrawable(itemView.getContext()));

        }

        private void configureSaveCardSwitch(){
            ColorStateList thumbStates = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked},
                            new int[]{}
                    },
                    new int[]{
                            ThemeObject.getInstance().getSaveCardSwitchOffThumbTint(),
                            ThemeObject.getInstance().getSaveCardSwitchOnThumbTint(),
                            Color.GRAY
                    }
            );


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                saveCardSwitch.setThumbTintList(thumbStates);
            }

            if (Build.VERSION.SDK_INT >= 24) {
                ColorStateList trackStates = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_checked},
                                new int[]{android.R.attr.state_checked},
                                new int[]{}
                        },
                        new int[]{
                                ThemeObject.getInstance().getSaveCardSwitchOffTrackTint(),
                                ThemeObject.getInstance().getSaveCardSwitchOnTrackTint(),
                                Color.LTGRAY,
                        }
                );
                saveCardSwitch.setTrackTintList(trackStates);
                saveCardSwitch.setTrackTintMode(PorterDuff.Mode.OVERLAY);
            }
        }

    private void setHintTextColor(TextInputLayout textInputLayout,int color) {
        if(textInputLayout==null)return;
        try {
            Field field = textInputLayout.getClass().getDeclaredField("defaultHintTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(textInputLayout, myList);

            Method method = textInputLayout.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(textInputLayout, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ArrayList<PaymentOption> getPaymentOption() {
        return PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions();
    }

    @Override
    public void bind(CardCredentialsViewModelData data) {

        cardScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.cardScannerButtonClicked();
            }
        });

        if (!viewModel.getCardNumber().isEmpty()) {
            handleScanbinLookupResponse();
           // cardNumberField.setText(viewModel.getCardNumber());
        }

        if (!viewModel.getExpirationMonth().isEmpty() && !viewModel.getExpirationYear().isEmpty()) {
          //  String expirationDate = viewModel.getExpirationMonth() + "/" + String.format("%02d", Integer.valueOf(viewModel.getExpirationYear()) % 100);
            String expirationDate = viewModel.getExpirationMonth() + String.format("%02d", Integer.valueOf(viewModel.getExpirationYear()) % 100);
            expirationDateField.setText(expirationDate);
        }

        if (!viewModel.getNameOnCard().isEmpty()) {
            nameOnCardField.setText(viewModel.getNameOnCard());
        } else if(PaymentDataSource.getInstance().getDefaultCardHolderName()!=null){
            if(!PaymentDataSource.getInstance().getDefaultCardHolderName().isEmpty())
                nameOnCardField.setText(PaymentDataSource.getInstance().getDefaultCardHolderName());
        }

        if(PaymentDataSource.getInstance()!=null && PaymentDataSource.getInstance().getEnableEditCardHolderName()== true ){
            nameOnCardField.setEnabled(true);
        }else if(PaymentDataSource.getInstance().getDefaultCardHolderName()==null){
            nameOnCardField.setEnabled(true);
        }else if(PaymentDataSource.getInstance().getDefaultCardHolderName()!=null &&PaymentDataSource.getInstance().getDefaultCardHolderName().isEmpty() ){
            nameOnCardField.setEnabled(true);
        }else {
            nameOnCardField.setEnabled(false);
        }



        saveCardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          viewModel.saveCardSwitchClicked(isChecked);
        }
      });


        nameOnCardField.addTextChangedListener(cardCredentialsTextWatcher);
        expirationDateField.addTextChangedListener(cardCredentialsTextWatcher);
        cvvField.addTextChangedListener(cardCredentialsTextWatcher);



    }

    /**
     * The type Card credentials text watcher.
     */
    class CardCredentialsTextWatcher implements TextWatcher{

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewModel.cardDetailsFilled(validateCardFields(), viewModel);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    }


  private boolean validateCardFields() {

        boolean status=false;
      if( cardNumberField.getText()!= null
          && !"".equalsIgnoreCase(cardNumberField.getText().toString().trim())
          && expirationDateField.getText()!=null
          && cvvField.getText()!=null && nameOnCardField.getText()!=null) {

              if(validateCardNumber(cardNumberField.getText().toString())
                .getValidationState() == CardValidationState.valid
                &&
                validateCVV()
                &&
                validateEXPDate()
                &&
                validateCardHolderName()){
                status= true;
                viewModel.setCardNumber(cardNumberField.getText().toString());
                viewModel.setExpirationMonth(expirationDateField.getMonth());
                viewModel.setExpirationYear(expirationDateField.getYear());
                viewModel.setCVVnumber(cvvField.getText().toString());
                viewModel.setNameOnCard(nameOnCardField.getText().toString().trim());
              }
      }

      if (status
          && PaymentDataManager.getInstance().getExternalDataSource() != null
          && PaymentDataManager.getInstance().getExternalDataSource().getAllowedToSaveCard())
          saveCardSwitch.setChecked(true);

      else saveCardSwitch.setChecked(false);

      return status;

  }

  private boolean validateCVV(){
    return cvvField.getmCardType().getSecurityCodeLength()== cvvField.getText().toString().length();
  }

  private boolean validateEXPDate(){
        return expirationDateField.isValid();
  }

  private boolean validateCardHolderName(){
      return nameOnCardField.getText().toString().trim().length() >= 3;
  }

  @Override
    public void setFocused(boolean isFocused) {
        itemView.setSelected(isFocused);
    }

    private void initCardSystemsRecyclerView(ArrayList<PaymentOption> paymentOptions) {

        cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);

        linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        cardSystemsRecyclerView.setLayoutManager(linearLayoutManager);

        CardSystemsRecyclerViewAdapter adapter = new CardSystemsRecyclerViewAdapter(paymentOptions);
        cardSystemsRecyclerView.setAdapter(adapter);
    }

    /**
     * Update card systems recycler view.
     *
     * @param brand      the brand
     * @param cardScheme the card scheme
     */
    public void updateCardSystemsRecyclerView(CardBrand brand,CardScheme cardScheme) {
        RecyclerView cardSystemsRecyclerView = itemView.findViewById(R.id.cardSystemsRecyclerView);
        cardSystemsRecyclerView.invalidate();
        CardSystemsRecyclerViewAdapter adapter = (CardSystemsRecyclerViewAdapter) cardSystemsRecyclerView.getAdapter();
        adapter.updateForCardBrand(brand,cardScheme);
    }

    private DefinedCardBrand validateCardNumber(String cardNumber) {

        cardNumber = cardNumber.replace(" ", "");

        // get preferred card brands from payment option
        ArrayList<CardBrand> paymentOptionsCardBrands = PaymentDataManager.getInstance().getAvailablePaymentOptionsCardBrands();
        DefinedCardBrand brand = CardValidator.validate(cardNumber,paymentOptionsCardBrands);

        // update CCVEditText CardType: to set CCV Length according to CardType
        updateCCVEditTextCardType(brand.getCardBrand());
        // update card types
       BINLookupResponse binLookupResponse =  PaymentDataManager.getInstance().getBinLookupResponse();
       updateCardSystemsRecyclerView(brand.getCardBrand(),binLookupResponse==null?null:binLookupResponse.getScheme());

     //   if (binLookupResponse != null && PaymentDataSource.getInstance().getCardType() != null)
           // if (!PaymentDataSource.getInstance().getCardType().toString().equals(binLookupResponse.getCardType())) {
        if(binLookupResponse != null && PaymentDataSource.getInstance().getCardType()!=null && PaymentDataSource.getInstance().getCardType() == ALL) {
             if (brand.getValidationState().equals(CardValidationState.invalid)) {
                    saveCardSwitch.setChecked(false);
                    viewModel.saveCardSwitchClicked(false);
                    if (ThemeObject.getInstance().getCardInputInvalidTextColor() != 0) {
                        cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputInvalidTextColor());
                    }
                } else {
                    if (PaymentDataManager.getInstance().getExternalDataSource() != null
                            && PaymentDataManager.getInstance().getExternalDataSource().getAllowedToSaveCard()) {
                        saveCardSwitch.setChecked(true);
                        viewModel.saveCardSwitchClicked(true);
                    } else {
                        saveCardSwitch.setChecked(false);
                        viewModel.saveCardSwitchClicked(false);
                    }
                    if (ThemeObject.getInstance().getCardInputTextColor() != 0) {
                        cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                    }
                }
        }else
                if (binLookupResponse != null && PaymentDataSource.getInstance().getCardType() != null?!PaymentDataSource.getInstance().getCardType().toString().equals(binLookupResponse.getCardType()):false) {
                if (ThemeObject.getInstance().getCardInputInvalidTextColor() != 0) {
                    cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputInvalidTextColor());
                }

                showDialog(itemView.getResources().getString(R.string.alert_un_supported_card_title), itemView.getResources().getString(R.string.alert_un_supported_card_message));


            }else {

                if (brand.getValidationState().equals(CardValidationState.invalid)) {
                    saveCardSwitch.setChecked(false);
                    viewModel.saveCardSwitchClicked(false);
                    if (ThemeObject.getInstance().getCardInputInvalidTextColor() != 0) {
                        cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputInvalidTextColor());
                    }
                } else {
                    if (PaymentDataManager.getInstance().getExternalDataSource() != null
                            && PaymentDataManager.getInstance().getExternalDataSource().getAllowedToSaveCard()) {
                        saveCardSwitch.setChecked(true);
                        viewModel.saveCardSwitchClicked(true);
                    } else {
                        saveCardSwitch.setChecked(false);
                        viewModel.saveCardSwitchClicked(false);
                    }
                    if (ThemeObject.getInstance().getCardInputTextColor() != 0) {
                        cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputTextColor());
                    }
                }
            }

        return brand;
    }

    /**
     * Update ccv edit text card type.
     *
     * @param cardBrand the card brand
     */
    public void updateCCVEditTextCardType(CardBrand cardBrand){
//      System.out.println("updateCCVEditTextCardType : " + cardBrand);
      if (cardBrand == null) return;

//      System.out.println("updateCCVEditTextCardType : " + cardBrand.getRawValue());

      if (cardBrand.getRawValue().contains("AMEX") || cardBrand.getRawValue().contains("AMERICAN_EXPRESS"))
        cvvField.setCardType(CardType.AMEX);

        else if (cardBrand.getRawValue().contains("VISA"))
            cvvField.setCardType(CardType.VISA);

        else if (cardBrand.getRawValue().contains("DISCOVER"))
            cvvField.setCardType(CardType.DISCOVER);

        else if (cardBrand.getRawValue().contains("DINERS_CLUB") || cardBrand.getRawValue().contains("DINERS"))
            cvvField.setCardType(CardType.DINERS_CLUB);

        else if (cardBrand.getRawValue().contains("MADA"))
            cvvField.setCardType(CardType.MADA);

        else if (cardBrand.getRawValue().contains("MAESTRO"))
            cvvField.setCardType(CardType.MAESTRO);

        else if (cardBrand.getRawValue().contains("MASTERCARD"))
            cvvField.setCardType(CardType.MASTERCARD);

        else if (cardBrand.getRawValue().contains("UNION_PAY") || cardBrand.getRawValue().contains("UNIONPAY"))
            cvvField.setCardType(CardType.UNIONPAY);
        else
        cvvField.setCardType(CardType.UNKNOWN);
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


    /**
     * Update address on card view.
     *
     * @param isShow the is show
     */
    public void updateAddressOnCardView(boolean isShow) {
    if (isShow) {
      addressOnCardLayout.setVisibility(View.VISIBLE);
    } else {
      addressOnCardLayout.setVisibility(View.GONE);
    }
  }

    private static class TrackingSpan extends ReplacementSpan {

        private float mTrackingPx;

        /**
         * Instantiates a new Tracking span.
         *
         * @param tracking the tracking
         */
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

     private String validateLength(String cardNumber) {

                cardNumber = cardNumber.replace(" ", "");
//                System.out.println("brand >>  card number : replaced card no = " + cardNumber);
                DefinedCardBrand brand = CardValidator.validate(cardNumber);
//                System.out.println("brand >>  card number :" + cardNumber + " brand:"+brand.getCardBrand());

                String str = cardNumberField.getText().toString();
                String newStr = str;
                if (brand.getValidationState().equals(CardValidationState.invalid)) {
                    newStr =  str.substring(0,str.length()-1);
                }
                return newStr;
}

    private EditText getAddressOnCardField() {

        return addressOnCardField;
    }

  @Override
  public Parcelable saveState() {
    return linearLayoutManager.onSaveInstanceState();
  }

  @Override
  public void restoreState(Parcelable state) {
    if (state != null) {
      linearLayoutManager.onRestoreInstanceState(state);
    }
  }

    private void setupRTL(Context context) {
       if(context!=null){

           if (SDK_INT >= JELLY_BEAN_MR1) {
               if (context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                   cardNumberField.setTextDirection(View.TEXT_DIRECTION_LTR);
                   cardNumberField.setGravity(Gravity.START);
               }
           }

       }
    }
    public void disableCardScanView() {
      //  System.out.println("cardscaniscalled");
        cardScannerButton.setEnabled(false);
        cardScannerButton.setFocusableInTouchMode(false);
        cardScannerButton.setClickable(false);
    }
    public void enableCardScanView() {
        cardScannerButton.setEnabled(true);
        cardScannerButton.setClickable(true);
        cardScannerButton.setFocusableInTouchMode(true);
    }

    private void showDialog(String title,String message){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);


        dialogBuilder.setPositiveButton(itemView.getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PaymentDataManager.getInstance().setBinLookupResponse(null);
                cardNumberField.setText(null);
                if (SDK_INT >= Build.VERSION_CODES.M) {
                    cardNumberField.setTextColor(itemView.getContext().getColor(R.color.greyish_brown));
                }
                cardNumberField.setText("");
                cardNumberField.setText(null);
                dialog.dismiss();

            }

        });

        PaymentDataManager.getInstance().setBinLookupResponse(null);
       // cardNumberField.setText(null);
        AlertDialog dialog = dialogBuilder.create();

        // Finally, display the alert dialog
        dialog.show();

        // Get the alert dialog buttons reference
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if(positiveButton!=null){
            if(ThemeObject.getInstance().getDialogbuttonColor()!=0)
                positiveButton.setBackgroundColor(ThemeObject.getInstance().getDialogbuttonColor()); // change button color
            if(ThemeObject.getInstance().getDialogTextColor()!=0)
                positiveButton.setTextColor(ThemeObject.getInstance().getDialogTextColor());
            if(ThemeObject.getInstance().getDialogTextSize()!=0)
                positiveButton.setTextSize(ThemeObject.getInstance().getDialogTextSize());
            try {
                Resources resources = dialog.getContext().getResources();
                int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
                TextView alertTitle = (TextView) dialog.getWindow().getDecorView().findViewById(alertTitleId);
                if(ThemeObject.getInstance().getDialogTextColor()!=0)
                    alertTitle.setTextColor(ThemeObject.getInstance().getDialogTextColor()); // change title text color
                TextView alertMessage = (TextView) dialog.getWindow().getDecorView().findViewById(android.R.id.message);
                if(ThemeObject.getInstance().getDialogTextColor()!=0)
                    alertMessage.setTextColor(ThemeObject.getInstance().getDialogTextColor()); // change title text color

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
    private void handleScanbinLookupResponse(){
        DefinedCardBrand brand = validateCardNumber(viewModel.getCardNumber());
        CardBrand cardBrand = brand.getCardBrand();
        if(viewModel.getCardNumber().length()> BIN_NUMBER_LENGTH){
            viewModel.binNumberEntered(viewModel.getCardNumber().substring(0,6));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something after 1s = 1000 miliseconds since set response takes time
                BINLookupResponse binLookupResponse  =  PaymentDataManager.getInstance().getBinLookupResponse();
                viewModel.setPaymentOption(cardBrand, binLookupResponse ==null?null: binLookupResponse.getScheme());
                System.out.println("card = " + viewModel.getCardNumber() +"binlookup "+ PaymentDataManager.getInstance().getBinLookupResponse().getCardType());
                if (binLookupResponse!=null && PaymentDataSource.getInstance().getCardType() != null?!PaymentDataSource.getInstance().getCardType().toString().equals(binLookupResponse.getCardType()):false) {
                    if (ThemeObject.getInstance().getCardInputInvalidTextColor() != 0)
                        cardNumberField.setTextColor(ThemeObject.getInstance().getCardInputInvalidTextColor());
                    showDialog(itemView.getResources().getString(R.string.alert_un_supported_card_title), itemView.getResources().getString(R.string.alert_un_supported_card_message));
                }else{
                    cardNumberField.setText(viewModel.getCardNumber());
                }
            }
        }, 1000); //Time in mis


    }

}