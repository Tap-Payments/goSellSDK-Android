package company.tap.gosellapi.internal.data_managers.payment_options.view_models.card_input_fields_text_handlers;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * The type Text handler.
 */
public abstract class TextHandler implements TextWatcher {

    private boolean isHandlingBeforeTextChanged = false;
    private boolean isHandlingOnTextChanged = false;
    @NonNull
    private EditText editText;

    /**
     * Gets edit text.
     *
     * @return the edit text
     */
    @NonNull public EditText getEditText() { return editText; }

    /**
     * Instantiates a new Text handler.
     *
     * @param editText the edit text
     */
    public TextHandler(EditText editText) {

        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (isHandlingBeforeTextChanged) { return; }
        isHandlingBeforeTextChanged = true;

        textWillChange(s, start, count, after);

        isHandlingBeforeTextChanged = false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (isHandlingOnTextChanged) { return; }
        isHandlingOnTextChanged = true;

        textChanged(s, start, before, count);

        isHandlingOnTextChanged = false;
    }

    @Override
    public void afterTextChanged(Editable s) { }

    /**
     * Text changed.
     *
     * @param s      the s
     * @param start  the start
     * @param before the before
     * @param count  the count
     */
    protected abstract void textChanged(CharSequence s, int start, int before, int count);

    /**
     * Text will change.
     *
     * @param s     the s
     * @param start the start
     * @param count the count
     * @param after the after
     */
    protected abstract void textWillChange(CharSequence s, int start, int count, int after);
}