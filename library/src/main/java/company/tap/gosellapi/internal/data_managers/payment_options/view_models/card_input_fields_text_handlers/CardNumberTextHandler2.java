package company.tap.gosellapi.internal.data_managers.payment_options.view_models.card_input_fields_text_handlers;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import company.tap.gosellapi.internal.data_managers.payment_options.view_models.CardCredentialsViewModel;
import company.tap.tapcardvalidator_android.CardBrand;
import company.tap.tapcardvalidator_android.CardValidator;

public class CardNumberTextHandler2 extends TextHandler {

  public interface DataProvider {

    CardCredentialsViewModel.BrandWithScheme getRecognizedCardType();
  }

  public interface DataListener {

    void cardNumberTextHandlerDidUpdateCardNumber(String cardNumber);
  }

  public CardNumberTextHandler2(@NonNull EditText editText, @NonNull DataProvider dataProvider, @NonNull DataListener dataListener) {

    super(editText);
    this.dataProvider = dataProvider;
    this.dataListener = dataListener;
  }

  @Override
  // This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after.
  protected void textWillChange(CharSequence s, int start, int count, int after) {

    String text = s.toString();
    if ( text.isEmpty() || after != 0 ) {

      this.justRemovedWhitespace = false;
      return;
    }

    String textThatWillBeRemoved = text.substring(start, start + count);
    this.justRemovedWhitespace = textThatWillBeRemoved.startsWith(" ") && (textThatWillBeRemoved.length() == 1);
  }

  @Override
  protected void textChanged(CharSequence s, int start, int before, int count) {

    String text = s.toString();
    callListenerWithCardNumber(text);

    if ( this.justRemovedWhitespace && start > 0) {

      text = (new StringBuffer(text).deleteCharAt(start - 1)).toString();
    }

    CardCredentialsViewModel.BrandWithScheme recognizedType = dataProvider.getRecognizedCardType();
    CardBrand cardBrand = recognizedType.getBrand();

    int[] spacings = CardValidator.spacings(cardBrand);

    text = text.replace(" ", "");
    SpannableStringBuilder cardNumber = new SpannableStringBuilder(text);

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

  //region Private Properties

  @NonNull private DataProvider dataProvider;
  @NonNull private DataListener dataListener;

  private boolean justRemovedWhitespace = false;

  //endregion

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

  private void callListenerWithCardNumber(String cardNumber) {

    String trimmedCardNumber = cardNumber.replace(" ", "");
    dataListener.cardNumberTextHandlerDidUpdateCardNumber(trimmedCardNumber);
  }
}