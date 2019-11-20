package company.tap.gosellapi.internal.data_managers.payment_options.view_models.card_input_fields_text_handlers;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidationState;
import company.tap.tapcardvalidator_android.CardValidator;
import company.tap.tapcardvalidator_android.DefinedCardBrand;

/**
 * The type Card number text handler.
 */
public class CardNumberTextHandler extends TextHandler {
  private static final int BIN_NUMBER_LENGTH = 6;
  private boolean justRemovedWhitespace = false;
  @NonNull private CardNumberTextHandler.DataProvider dataProvider;
  @NonNull private CardNumberTextHandler.DataListener dataListener;

    /**
     * The interface Data listener.
     */
    public interface DataListener {
        /**
         * Card number text handler did update card number.
         *
         * @param cardNumber the card number
         */
        void cardNumberTextHandlerDidUpdateCardNumber(String cardNumber);

        /**
         * Update cards recycler view with card and schema.
         *
         * @param cardBrand  the card brand
         * @param cardScheme the card scheme
         */
        void updateCardsRecyclerViewWithCardAndSchema(CardBrand cardBrand, CardScheme cardScheme);
  }

    /**
     * The interface Data provider.
     */
    public interface DataProvider {

        /**
         * Gets recognized card type.
         *
         * @return the recognized card type
         */
        CardCredentialsViewModel.BrandWithScheme getRecognizedCardType();
  }

    /**
     * Instantiates a new Card number text handler.
     *
     * @param editText     the edit text
     * @param dataProvider the data provider
     * @param dataListener the data listener
     */
    public CardNumberTextHandler(@NonNull EditText editText, @NonNull
      CardNumberTextHandler.DataProvider dataProvider,
                                @NonNull CardNumberTextHandler.DataListener dataListener) {

    super(editText);
    this.dataProvider = dataProvider;
    this.dataListener = dataListener;
  }




  // This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after.
  protected void textWillChange(CharSequence s, int start, int count, int after) {

    String text = s.toString();
    if (text.isEmpty() || after != 0) {

      this.justRemovedWhitespace = false;
      return;
    }

    String textThatWillBeRemoved = text.substring(start, start + count);
    this.justRemovedWhitespace = textThatWillBeRemoved.startsWith(" ") && (textThatWillBeRemoved
        .length() == 1);
  }

  @Override
  protected void textChanged(CharSequence s, int start, int before, int count) {

    String text = s.toString();

    callListenerWithCardNumber(text);

    if ( this.justRemovedWhitespace && start > 0) {

      text = (new StringBuffer(text).deleteCharAt(start - 1)).toString();
    }

    text = text.replace(" ", "");

    SpannableStringBuilder cardNumber = new SpannableStringBuilder(text);

    if(cardNumber.length()>=BIN_NUMBER_LENGTH){
      callBINNumberAPI(cardNumber);
    }else {
      validateUsingCardValidator(cardNumber);
    }

    DefinedCardBrand cardBrand = CardValidator.validate(cardNumber.toString());

    setCardNumberColor(cardBrand);

    int[] spacings = CardValidator.spacings(cardBrand.getCardBrand());



    applySpaces(spacings, cardNumber);

    String textToSet = cardNumber.toString();
    getEditText().setText(textToSet);

    int selectionIndex = start + count;

    for (int i = 0; i < spacings.length; i++) {

      int space = spacings[i];

      int incrementedSpace = space + i;

      if (start <= incrementedSpace && incrementedSpace < start + count) {

        selectionIndex++;
        continue;
      }

      if (start - 1 == incrementedSpace && count == 0) {

        selectionIndex--;
      }
    }

    try {

      getEditText().setSelection(selectionIndex);

    } catch (Exception e) {

      getEditText().setSelection(cardNumber.length());
    }


  }

  private void setCardNumberColor(DefinedCardBrand cardBrand) {
    if(cardBrand!=null)
    {
      if(cardBrand.getValidationState()== CardValidationState.invalid){
        ((CardCredentialsViewModel) dataListener).setCardNumberColor(Color.RED);
      }else {
        if(ThemeObject.getInstance().getCardInputTextColor()!=0)
        ((CardCredentialsViewModel) dataListener).setCardNumberColor(ThemeObject.getInstance().getCardInputTextColor());
      }
    }
  }

  private void validateUsingCardValidator(SpannableStringBuilder cardNumber) {
    DefinedCardBrand brand = CardValidator.validate(cardNumber.toString());
    updateCardsRecyclerViewWithCardAndSchema(brand.getCardBrand(),null);
  }

  private void callBINNumberAPI(SpannableStringBuilder cardNumber) {

    ((CardCredentialsViewModel) dataListener).binNumberEntered(cardNumber.toString());
  }



  private void updateCardsRecyclerViewWithCardAndSchema(CardBrand cardBrand, CardScheme cardScheme) {
    dataListener.updateCardsRecyclerViewWithCardAndSchema(cardBrand,cardScheme);
  }

  private void callListenerWithCardNumber(String cardNumber) {

    String trimmedCardNumber = cardNumber.replace(" ", "");
    dataListener.cardNumberTextHandlerDidUpdateCardNumber(trimmedCardNumber);
  }

  private void applySpaces(int[] spaces, SpannableStringBuilder string) {

    for ( int spacingIndex = spaces.length - 1; spacingIndex > -1; spacingIndex-- ) {

      int space = spaces[spacingIndex] + 1;

      if ( string.length() == space ) {

        string.append(" ");
      }
      if (string.length() > space ) {

        string.insert(space, " ");
      }
    }
  }


}
