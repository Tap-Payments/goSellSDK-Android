package company.tap.gosellapi.internal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;

public class GoSellOTPScreenFragment extends Fragment {
    private static final int TICK_LENGTH = 1000;
    private static final String TIMER_STRING_FORMAT = "%02d:%02d";
    private static final int CONFIRMATION_CODE_LENGTH = 6;

    private CountDownTimer timer;
    private int resendConfirmationCodeTimeout;

    TextView resendTextView;
    private ArrayList<TextView> textViewsArray = new ArrayList<>();

    public GoSellOTPScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resendConfirmationCodeTimeout = GlobalDataManager.getInstance().getInitResponse().getData().getSdk_settings().getResend_interval() * TICK_LENGTH;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        startCountdown();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        timer.cancel();
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

        resendTextView = view.findViewById(R.id.resendConfirmationCode);
        resendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendConfirmationCode();
            }
        });
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

    private void resendConfirmationCode() {
        startCountdown();
    }

    private void startCountdown() {

        View view = getView();
        if(view == null) return;

        final TextView timerTextView = getView().findViewById(R.id.timerTextView);

        if(timer != null) timer.cancel();

        resendTextView.setEnabled(false);

        timer = new CountDownTimer(resendConfirmationCodeTimeout, TICK_LENGTH) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(formatMilliseconds(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timerTextView.setText(formatMilliseconds(0));
                resendTextView.setEnabled(true);
            }
        };

        timer.start();
    }

    private String formatMilliseconds(long time) {

        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

        return String.format(Locale.getDefault(), TIMER_STRING_FORMAT, minutes, seconds);
    }

}
