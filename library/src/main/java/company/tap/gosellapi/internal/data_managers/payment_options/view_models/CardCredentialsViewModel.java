package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.api.models.BINLookupResponse;
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.card_input_fields_text_handlers.CardNumberTextHandler;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.CardCredentialsViewModelData;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.internal.viewholders.CardCredentialsViewHolder;
import company.tap.gosellapi.internal.viewholders.PaymentOptionsBaseViewHolder;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidationState;
import company.tap.tapcardvalidator_android.CardValidator;
import company.tap.tapcardvalidator_android.DefinedCardBrand;

public class CardCredentialsViewModel
        extends PaymentOptionViewModel<CardCredentialsViewModelData, CardCredentialsViewHolder, CardCredentialsViewModel>
        implements CardCredentialsViewHolder.Data, CardNumberTextHandler.DataProvider, CardNumberTextHandler.DataListener {

    //region Private Properties

    @Nullable private CardNumberTextHandler cardNumberTextHandler;
    private CardCredentialsViewHolder cardCredentialsViewHolder;
    private ArrayList<PaymentOption> paymentOptions;
    private PaymentOption selectedCardPaymentOption;

    @NonNull private CardNumberTextHandler getCardNumberTextHandler(EditText editText) {

        if ( cardNumberTextHandler == null ) {

            cardNumberTextHandler = new CardNumberTextHandler(editText, this, this);
            return cardNumberTextHandler;
        }

        if ( !cardNumberTextHandler.getEditText().equals(editText) ) {

            cardNumberTextHandler = new CardNumberTextHandler(editText, this, this);
            return cardNumberTextHandler;
        }

        return cardNumberTextHandler;
    }

    //endregion

    //region CardCredentialsViewHolder.Data

    @Override
    public void bindCardNumberFieldWithWatcher(EditText cardNumberField) {
        cardNumberField.addTextChangedListener(getCardNumberTextHandler(cardNumberField));
    }

    @Override
    public SpannableString getCardNumberText() {
        return null;
    }

    @Override
    public String getExpirationDateText() {
        return null;
    }

    @Override
    public String getCVVText() {
        return null;
    }

    @Override
    public String getCardholderNameText() {
        return null;
    }

    //endregion

    private CardCredentialsViewModelData dataOriginal;

    private boolean isShowAddressOnCardCell = false;

    // Card credentials fields
    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;
    private String CVVnumber;
    private String nameOnCard;
    private boolean shouldSaveCard;

    @Nullable private BINLookupResponse currentBINData;
    @Nullable private BINLookupResponse getCurrentBINData() { return currentBINData; }

    @NonNull private ArrayList<CardBrand> availableCardBrands;
    @NonNull private ArrayList<CardBrand> getAvailableCardBrands() { return availableCardBrands; }

    @NonNull private ArrayList<CardBrand> preferredCardBrands;
    @NonNull private ArrayList<CardBrand> getPreferredCardBrands() { return preferredCardBrands; }

    public CardCredentialsViewModel(PaymentOptionsDataManager parentDataManager, CardCredentialsViewModelData data) {

        super(parentDataManager, data, PaymentOptionsBaseViewHolder.ViewHolderType.CARD);

        ArrayList<CardBrand> preferredBrands = new ArrayList<>();
        for (PaymentOption paymentOption : data.getPaymentOptions()) {

            preferredBrands.add(paymentOption.getBrand());
        }

        this.preferredCardBrands = preferredBrands;

        this.dataOriginal = data;

        this.cardNumber = "";
        this.expirationMonth = "";
        this.expirationYear = "";
        this.CVVnumber = "";
        this.nameOnCard = "";
        this.shouldSaveCard = false;
    }

    public void cardScannerButtonClicked() {
        parentDataManager.cardScannerButtonClicked();
    }


    public void cardDetailsFilled(boolean isFilled, CardCredentialsViewModel cardCredentialsViewModel) {
        parentDataManager.cardDetailsFilled(isFilled, cardCredentialsViewModel);
    }

    public void addressOnCardClicked() {
        parentDataManager.addressOnCardClicked();
    }

    public void saveCardSwitchClicked(boolean state) {

        this.shouldSaveCard = state;
        parentDataManager.saveCardSwitchCheckedChanged(state, position + 1);
    }

    public void cardExpirationDateClicked() {
        parentDataManager.cardExpirationDateClicked();
    }

    public void setCardSwitchHeight(int cardSwitchHeight) {
        parentDataManager.setCardSwitchHeight(cardSwitchHeight);
    }

    public void binNumberEntered(String binNumber) { parentDataManager.binNumberEntered(binNumber);}

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public boolean shouldSaveCard() { return shouldSaveCard; }

    public boolean isShowAddressOnCardCell() {
        return isShowAddressOnCardCell;
    }

    public String getCVVnumber() {
        return CVVnumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    @Nullable public CreateTokenCard getCard() {

        String number           = getCardNumber();
        String expMonth         = getExpirationMonth();
        String expYear          = getExpirationYear();
        String cvc              = getCVVnumber();
        String cardholderName   = getNameOnCard();

        if (number == null || expMonth == null || expYear == null || cvc == null || cardholderName == null) {

            return null;
        }

        // TODO: Add address handling here.

        return new CreateTokenCard(
            number.replace(" ",""),
            expMonth,
            (expYear.length()==4)?expYear.substring(2):expirationYear,
            cvc,
            cardholderName,
            null);
    }

    public void showAddressOnCardCell(boolean isShow) {
        this.isShowAddressOnCardCell = isShow;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public void setCVVnumber(String CVVnumber) {
        this.CVVnumber = CVVnumber;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    //region CardNumberTextHandler.DataProvider

    @Override
    public BrandWithScheme getRecognizedCardType() {

        BINLookupResponse binData = getCurrentBINData();

        @Nullable CardScheme scheme = null;
        @NonNull CardBrand brand = null;
        if ( binData != null ) {

            scheme = binData.getScheme();
            brand = binData.getCardBrand();
        }

        DefinedCardBrand localData = CardValidator.validate(getCardNumber(), getPreferredCardBrands());
        CardValidationState validationState = localData.getValidationState();

        if ( brand == null ) { brand = localData.getCardBrand(); }
        if ( brand == null ) { brand = CardBrand.unknown; }

        return new BrandWithScheme(scheme, brand, validationState);
    }

    //endregion

    //region CardNumberTextHandler.DataListener

    @Override
    public void cardNumberTextHandlerDidUpdateCardNumber(String cardNumber) {

        setCardNumber(cardNumber);
    }


  public void ViewHolderReference(CardCredentialsViewHolder cardCredentialsViewHolder) {
      this.cardCredentialsViewHolder = cardCredentialsViewHolder;
  }


  public CardCredentialsViewHolder getCardCredentialsViewHolder(){
      return cardCredentialsViewHolder;
  }

  public void setPaymentOption(CardBrand cardBrand) {
      if(cardBrand!=null){
          System.out.println(" CardCredentialsViewModel..  data.getPaymentOptions() ="+ data.getPaymentOptions().size());
          for(PaymentOption paymentOption:  data.getPaymentOptions()){
              System.out.println(" card cred ... paymentOption. comparison :"+paymentOption.getBrand().name() + " selected :"+ cardBrand.name() + " >> " +paymentOption.getBrand().compareTo(cardBrand));
              if(paymentOption.getBrand().compareTo(cardBrand)==0){
                  this.selectedCardPaymentOption = paymentOption;
                  updatePayButtonWithExtraFees(paymentOption);
              }
          }
          if(selectedCardPaymentOption!=null)
              System.out.println("card cred ... paymentOption. final : "+selectedCardPaymentOption.getBrand().name());
      }else
      {
        selectedCardPaymentOption = null;
        updatePayButtonWithExtraFees(selectedCardPaymentOption);
      }
  }

  private void updatePayButtonWithExtraFees(PaymentOption paymentOption){
      parentDataManager.updatePayButtonWithExtraFees(paymentOption);
  }

  public PaymentOption getSelectedCardPaymentOption(){
      return selectedCardPaymentOption;
  }


  //endregion

    public class BrandWithScheme {

        @Nullable   private CardScheme          scheme;
        @NonNull    private CardBrand           brand;
        @NonNull    private CardValidationState validationState;

        private BrandWithScheme(@Nullable CardScheme scheme, @NonNull CardBrand brand, @NonNull CardValidationState validationState) {

            this.scheme             = scheme;
            this.brand              = brand;
            this.validationState    = validationState;
        }

        @NonNull public CardBrand getBrand() { return brand; }
    }
}
