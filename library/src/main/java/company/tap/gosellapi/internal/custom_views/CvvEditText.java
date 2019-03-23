package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.utils.CardType;

/**
 * An {@link android.widget.EditText} that displays a CVV hint for a given Card type when focused.
 */
public class CvvEditText extends ErrorEditText implements TextWatcher {

    private static final int DEFAULT_MAX_LENGTH = 3;

    private CardType mCardType;

    /**
     * Instantiates a new Cvv edit text.
     *
     * @param context the context
     */
    public CvvEditText(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Cvv edit text.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CvvEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Cvv edit text.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CvvEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[]{new LengthFilter(DEFAULT_MAX_LENGTH)});
        addTextChangedListener(this);
    }

    /**
     * Sets the card type associated with the security code type. {@link CardType#AMEX} has a
     * different icon and length than other card types. Typically handled through
     *
     * @param cardType Type of card represented by the current value of card number input.
     */
    public void setCardType(CardType cardType) {
        mCardType = cardType;

        InputFilter[] filters = { new LengthFilter(cardType.getSecurityCodeLength()) };
        setFilters(filters);

        setContentDescription(getContext().getString(cardType.getSecurityCodeName()));
        //setFieldHint(cardType.getSecurityCodeName());

        invalidate();
    }

    /**
     * Gets card type.
     *
     * @return the card type
     */
    public CardType getmCardType() {
        return mCardType;
    }

    /**
     * Sets mask.
     *
     * @param mask if {@code true}, this field will be masked.
     */
    public void setMask(boolean mask) {
        if (mask) {
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        } else {
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mCardType == null) {
            return;
        }

        if (mCardType.getSecurityCodeLength() == editable.length() && getSelectionStart() == editable.length()) {
            validate();

            if (isValid()) {
                focusNextView();
            }
        }
    }

    @Override
    public boolean isValid() {
        return isOptional() || getText().toString().length() == getSecurityCodeLength();
    }

    @Override
    public String getErrorMessage() {
        String securityCodeName;
        if (mCardType == null) {
            securityCodeName = getContext().getString(R.string.bt_cvv);
        } else {
            securityCodeName = getContext().getString(mCardType.getSecurityCodeName());
        }

        if (TextUtils.isEmpty(getText())) {
            return getContext().getString(R.string.bt_cvv_required, securityCodeName);
        } else {
            return getContext().getString(R.string.bt_cvv_invalid, securityCodeName);
        }
    }

    private int getSecurityCodeLength() {
        if (mCardType == null) {
            return DEFAULT_MAX_LENGTH;
        } else {
            return mCardType.getSecurityCodeLength();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}
