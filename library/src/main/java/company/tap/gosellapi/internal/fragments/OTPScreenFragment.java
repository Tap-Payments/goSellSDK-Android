package company.tap.gosellapi.internal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;

public class OTPScreenFragment extends Fragment {

    private static final int CONFIRMATION_CODE_LENGTH = 6;
    private ArrayList<TextView> textViewsArray = new ArrayList<>();

    public OTPScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.gosellapi_fragment_otpscreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareTextViews(view);
        handleConfirmationCodeInputEditText(view);
        handleConfirmButton(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void prepareTextViews(View view) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout textViewsLayout = view.findViewById(R.id.textViewsLayout);
        Utils.showKeyboard(view.getContext(), textViewsLayout);

        int index = 0;
        while (index < CONFIRMATION_CODE_LENGTH) {

            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            textViewParams.weight = 1;
            textViewParams.setMargins(10, 10, 10, 10);

            TextView textView = (TextView) inflater.inflate(R.layout.gosellapi_layout_textview_password_cell, null);
            textViewsLayout.addView(textView, textViewParams);

            textViewsArray.add(textView);
            index++;
        }
    }

    private void handleConfirmationCodeInputEditText(final View view) {

        final EditText confirmationCodeInput = view.findViewById(R.id.confirmationCodeInput);

        confirmationCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = before == 0 ? String.valueOf(s.charAt(start)) : "";
                updateConfirmationCodeCells(start, text);

                updateConfirmButtonStatus(view, s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateConfirmationCodeCells(Integer index, String text) {
        if (index < 0 || index > CONFIRMATION_CODE_LENGTH) return;

        TextView passwordCell = textViewsArray.get(index);
        passwordCell.setText(text);
    }

    private void updateConfirmButtonStatus(View view, Integer passwordLength) {
        Button confirmButton = view.findViewById(R.id.confirmButton);
        boolean state = passwordLength == CONFIRMATION_CODE_LENGTH;
        confirmButton.setEnabled(state);
    }

    private void handleConfirmButton(View view) {

        Button confirmButton = view.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
