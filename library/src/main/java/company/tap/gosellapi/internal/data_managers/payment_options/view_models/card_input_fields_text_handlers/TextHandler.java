package company.tap.gosellapi.internal.data_managers.payment_options.view_models.card_input_fields_text_handlers;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class TextHandler implements TextWatcher {

    private boolean isHandlingBeforeTextChanged = false;
    private boolean isHandlingOnTextChanged = false;
    @NonNull
    private EditText editText;
    @NonNull public EditText getEditText() { return editText; }

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

    protected abstract void textChanged(CharSequence s, int start, int before, int count);
    protected abstract void textWillChange(CharSequence s, int start, int count, int after);
}