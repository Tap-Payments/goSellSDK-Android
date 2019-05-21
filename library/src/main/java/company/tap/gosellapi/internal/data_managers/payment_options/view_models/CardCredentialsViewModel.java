package company.tap.gosellapi.internal.data_managers.payment_options.view_models;

import android.graphics.Canvas;
import android.graphics.Color;
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

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.api.models.CreateTokenCard;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.api.responses.BINLookupResponse;
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

/**
 * The type Card credentials view model.
 */
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

    /**
     * Set current bin data.
     *
     * @param _currentBINData the current bin data
     */
    public void setCurrentBINData(
        company.tap.gosellapi.internal.api.responses.BINLookupResponse _currentBINData){
        currentBINData = _currentBINData;
        if(getCardCredentialsViewHolder()!=null)
        getCardCredentialsViewHolder().updateCardSystemsRecyclerView(getRecognizedCardType().getBrand(),getRecognizedCardType().getScheme());
    }

    @NonNull private ArrayList<CardBrand> availableCardBrands;
    @NonNull private ArrayList<CardBrand> getAvailableCardBrands() { return availableCardBrands; }

    @NonNull private ArrayList<CardBrand> preferredCardBrands;
    @NonNull private ArrayList<CardBrand> getPreferredCardBrands() { return preferredCardBrands; }

    /**
     * Instantiates a new Card credentials view model.
     *
     * @param parentDataManager the parent data manager
     * @param data              the data
     */
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

    /**
     * Card scanner button clicked.
     */
    public void cardScannerButtonClicked() {
        parentDataManager.cardScannerButtonClicked();
    }


    /**
     * Card details filled.
     *
     * @param isFilled                 the is filled
     * @param cardCredentialsViewModel the card credentials view model
     */
    public void cardDetailsFilled(boolean isFilled, CardCredentialsViewModel cardCredentialsViewModel) {
        parentDataManager.cardDetailsFilled(isFilled, cardCredentialsViewModel);
    }

    /**
     * Address on card clicked.
     */
    public void addressOnCardClicked() {
        parentDataManager.addressOnCardClicked();
    }

    /**
     * Save card switch clicked.
     *
     * @param state the state
     */
    public void saveCardSwitchClicked(boolean state) {

        this.shouldSaveCard = state;
        parentDataManager.saveCardSwitchCheckedChanged(state, position + 1);
    }

    /**
     * Card expiration date clicked.
     */
    public void cardExpirationDateClicked() {
        parentDataManager.cardExpirationDateClicked();
    }

    /**
     * Sets card switch height.
     *
     * @param cardSwitchHeight the card switch height
     */
    public void setCardSwitchHeight(int cardSwitchHeight) {
        parentDataManager.setCardSwitchHeight(cardSwitchHeight);
    }

    /**
     * Bin number entered.
     *
     * @param binNumber the bin number
     */
    public void binNumberEntered(String binNumber) { parentDataManager.binNumberEntered(binNumber);}

    /**
     * Gets card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets expiration month.
     *
     * @return the expiration month
     */
    public String getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * Gets expiration year.
     *
     * @return the expiration year
     */
    public String getExpirationYear() {
        return expirationYear;
    }

    /**
     * Should save card boolean.
     *
     * @return the boolean
     */
    public boolean shouldSaveCard() { return shouldSaveCard; }

    /**
     * Is show address on card cell boolean.
     *
     * @return the boolean
     */
    public boolean isShowAddressOnCardCell() {
        return isShowAddressOnCardCell;
    }

    /**
     * Gets cv vnumber.
     *
     * @return the cv vnumber
     */
    public String getCVVnumber() {
        return CVVnumber;
    }

    /**
     * Gets name on card.
     *
     * @return the name on card
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * Gets card.
     *
     * @return the card
     */
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

    /**
     * Show address on card cell.
     *
     * @param isShow the is show
     */
    public void showAddressOnCardCell(boolean isShow) {
        this.isShowAddressOnCardCell = isShow;
    }

    /**
     * Sets card number.
     *
     * @param cardNumber the card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Sets expiration month.
     *
     * @param expirationMonth the expiration month
     */
    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    /**
     * Sets expiration year.
     *
     * @param expirationYear the expiration year
     */
    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    /**
     * Sets cv vnumber.
     *
     * @param CVVnumber the cv vnumber
     */
    public void setCVVnumber(String CVVnumber) {
        this.CVVnumber = CVVnumber;
    }

    /**
     * Sets name on card.
     *
     * @param nameOnCard the name on card
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    //region CardNumberTextHandler.DataProvider

    @Override
    public BrandWithScheme getRecognizedCardType() {
//        System.out.println(" getRecognizedType: "+ getCurrentBINData());
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

    @Override
    public void updateCardsRecyclerViewWithCardAndSchema(CardBrand cardBrand,
                                                         CardScheme cardScheme) {
        if(getCardCredentialsViewHolder()!=null)
            getCardCredentialsViewHolder().updateCardSystemsRecyclerView(cardBrand,cardScheme);
    }


    /**
     * View holder reference.
     *
     * @param cardCredentialsViewHolder the card credentials view holder
     */
    public void ViewHolderReference(CardCredentialsViewHolder cardCredentialsViewHolder) {
      this.cardCredentialsViewHolder = cardCredentialsViewHolder;
  }


    /**
     * Get card credentials view holder card credentials view holder.
     *
     * @return the card credentials view holder
     */
    public CardCredentialsViewHolder getCardCredentialsViewHolder(){
      return cardCredentialsViewHolder;
  }

    /**
     * Sets payment option.
     *
     * @param cardBrand  the card brand
     * @param cardScheme the card scheme
     */
    public void setPaymentOption(CardBrand cardBrand,CardScheme cardScheme) {
      if(cardScheme!=null){
          for(PaymentOption paymentOption:  data.getPaymentOptions()){
              if(paymentOption.getName().equalsIgnoreCase(cardScheme.name())){
                  this.selectedCardPaymentOption = paymentOption;
                  updatePayButtonWithExtraFees(paymentOption);
              }
          }
      }else {
          if(cardBrand!=null){
              for(PaymentOption paymentOption:  data.getPaymentOptions()){
                  if(paymentOption.getBrand().compareTo(cardBrand)==0){
                      this.selectedCardPaymentOption = paymentOption;
                      updatePayButtonWithExtraFees(paymentOption);
                  }
              }
          }else
          {
              selectedCardPaymentOption = null;
              updatePayButtonWithExtraFees(selectedCardPaymentOption);
          }
      }
//      if(selectedCardPaymentOption!=null && selectedCardPaymentOption.getBrand()!=null)
//          System.out.println("card cred ... paymentOption. final : "+selectedCardPaymentOption.getBrand().name());
  }

  private void updatePayButtonWithExtraFees(PaymentOption paymentOption){
      parentDataManager.updatePayButtonWithExtraFees(paymentOption);
  }

    /**
     * Get selected card payment option payment option.
     *
     * @return the payment option
     */
    public PaymentOption getSelectedCardPaymentOption(){
      return selectedCardPaymentOption;
  }

    /**
     * Sets card number color.
     *
     * @param color the color
     */
    public void setCardNumberColor(int color) {
        cardCredentialsViewHolder.setCardNumberColor(color);
    }

    public void checkShakingStatus() {
        parentDataManager.checkShakingStatus();
    }


    //endregion

    /**
     * The type Brand with scheme.
     */
    public class BrandWithScheme {

        @Nullable   private CardScheme          scheme;
        @NonNull    private CardBrand           brand;
        @NonNull    private CardValidationState validationState;

        private BrandWithScheme(@Nullable CardScheme scheme, @NonNull CardBrand brand, @NonNull CardValidationState validationState) {

            this.scheme             = scheme;
            this.brand              = brand;
            this.validationState    = validationState;
        }

        /**
         * Gets brand.
         *
         * @return the brand
         */
        @NonNull public CardBrand getBrand() { return brand; }

        /**
         * Get scheme card scheme.
         *
         * @return the card scheme
         */
        @NonNull public CardScheme getScheme(){return scheme;}
    }
}
