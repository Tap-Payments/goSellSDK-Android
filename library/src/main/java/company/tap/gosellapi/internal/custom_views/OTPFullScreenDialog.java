package company.tap.gosellapi.internal.custom_views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.open.buttons.PayButtonView;

/**
 * The type Otp full screen dialog.
 */
public class OTPFullScreenDialog extends DialogFragment {

    /**
     * The constant TAG.
     */
    public static String TAG = "OTPFullScreenDialog";
  private static final int TICK_LENGTH = 1000;
  private static final String TIMER_STRING_FORMAT = "%02d:%02d";
  private static final int CONFIRMATION_CODE_LENGTH = 6;
  private CountDownTimer timer;
  private int resendConfirmationCodeTimeout;

  private TextView timerTextView;
  private TextView phoneNumberTextView;
  private PayButtonView payButtonView;

  private String otpCode="";
  private ArrayList<TextView> textViewsArray = new ArrayList<>();


    /**
     * The interface Confirm otp.
     */
    public interface ConfirmOTP{
        /**
         * Confirm otp.
         */
        void confirmOTP();

        /**
         * Resend otp.
         */
        void resendOTP();
  }


  enum OperationType{
      CONFIRM_OTP,
    RESEND_OTP
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.GoSellSDKAppTheme_NoActionBar);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.gosellapi_fragment_otpscreen, container, true);
    resendConfirmationCodeTimeout = (int) (PaymentDataManager.getInstance().getSDKSettings()
        .getData().getInternalSDKSettings().getOtpResendInterval() * (double) TICK_LENGTH);

    prepareTextViews(view);
    handleConfirmationCodeInputEditText(view);
    startCountdown(view);
    return view;
  }


  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width =ViewGroup.LayoutParams.MATCH_PARENT; // displayMatrix.widthPixels;//
      int height = ViewGroup.LayoutParams.MATCH_PARENT;// displayMatrix.heightPixels - 56;//
      dialog.getWindow().setLayout(width, height);
    }

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }


  private void prepareTextViews(View view) {

    LayoutInflater inflater = LayoutInflater.from(view.getContext());
    LinearLayout textViewsLayout = view.findViewById(R.id.textViewsLayout);
    RelativeLayout otpParentLayout = view.findViewById(R.id.otpParentLayout);

    int index = 0;
    while (index < CONFIRMATION_CODE_LENGTH) {

      LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0,
          ViewGroup.LayoutParams.MATCH_PARENT);
      textViewParams.weight = 1;
      textViewParams.setMargins(10, 10, 10, 10);

      TextView textView = (TextView) inflater
          .inflate(R.layout.gosellapi_layout_textview_password_cell, null);
      textViewsLayout.addView(textView, textViewParams);

      textViewsArray.add(textView);
      index++;
    }

    timerTextView = view.findViewById(R.id.timerTextView);
    timerTextView.setOnClickListener(v -> checkInternetConnectivity(v.getContext(), OperationType.RESEND_OTP));

    phoneNumberTextView = view.findViewById(R.id.phoneNumberValue);
    Bundle b = getArguments();
    String phoneNumber = b.getString("phoneNumber", "");
    String sentNumberDisclaimer = getString(R.string.textview_disclaimer_otp_sent_number);
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sentNumberDisclaimer+" ");
    spannableStringBuilder.append(phoneNumber);
    Utils.highlightPhoneNumber(getContext(),spannableStringBuilder,sentNumberDisclaimer.length()+1,phoneNumber);

      phoneNumberTextView
          .setText(spannableStringBuilder);

    payButtonView = view.findViewById(R.id.payButtonId);
    payButtonView.setEnabled(false);
    payButtonView.getPayButton().setText(R.string.btn_title_confirm);

    payButtonView.setOnClickListener(v -> checkInternetConnectivity(v.getContext(),OperationType.CONFIRM_OTP));



  }

  private void checkInternetConnectivity(Context context,OperationType  action){
    ConnectivityManager connectivityManager =   (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
    if(connectivityManager!=null){
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
       switch (action){
         case CONFIRM_OTP:
           confirmOTPCode();
           break;
         case RESEND_OTP:
           resendOTPCode();
       }

      }
      else
        showDialog(context,action,getResources().getString(R.string.internet_connectivity_title),getResources().getString(R.string.internet_connectivity_message));
    }
  }

  private void showDialog(Context context, OperationType action, String title, String message){
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    dialogBuilder.setTitle(title);
    dialogBuilder.setMessage(message);
    dialogBuilder.setCancelable(false);


    dialogBuilder.setNegativeButton(getResources().getString(R.string.retry), (dialog, which) -> checkInternetConnectivity(context,action));

    dialogBuilder.show();

  }



  private void handleConfirmationCodeInputEditText(View view) {

    final EditText confirmationCodeInput = view.findViewById(R.id.confirmationCodeInput);

    confirmationCodeInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        /**
         * this method is to notify you that within s, the count characters beginning at index start
         * are about to be replaced by new text with length <b>after</b>
         * by length before.
         * Check string length first to avoid screen rotation issue (IndexOutOfBoundsException)
         */
        if(s!=null && s.length()>=0){
          String text = before == 0 ? String.valueOf(s.charAt(start)) : "";
          updateConfirmationCodeCells(start, text);
        }

      }

      @Override
      public void afterTextChanged(Editable s) {
        otpCode = s.toString();
        if (s.toString().length() == CONFIRMATION_CODE_LENGTH )
          payButtonView.setEnabled(true);
        else
          payButtonView.setEnabled(false);
      }
    });
  }

  private void updateConfirmationCodeCells(Integer index, String text) {


    if (index < 0 || index > CONFIRMATION_CODE_LENGTH) return;

    TextView passwordCell = textViewsArray.get(index);
    passwordCell.setText(text);
  }

  private void startCountdown(View view) {

    final TextView timerTextView = view.findViewById(R.id.timerTextView);

    if (timer != null) timer.cancel();

    timerTextView.setEnabled(false);

    timer = new CountDownTimer(resendConfirmationCodeTimeout, TICK_LENGTH) {
      @Override
      public void onTick(long millisUntilFinished) {
        timerTextView.setText(formatMilliseconds(millisUntilFinished));
      }

      @Override
      public void onFinish() {
        timerTextView.setText(R.string.textview_title_resend_again);
        timerTextView.setEnabled(true);
      }
    };

    timer.start();
  }

  private String formatMilliseconds(long time) {

    long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS
        .toMinutes(TimeUnit.MILLISECONDS.toHours(time));
    long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES
        .toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

    return String.format(Locale.getDefault(), TIMER_STRING_FORMAT, minutes, seconds);
  }

  private void confirmOTPCode() {
    PaymentDataManager.getInstance().confirmOTPCode(otpCode);
    GoSellPaymentActivity listener = (GoSellPaymentActivity) getActivity();
    if(listener==null) return;
    listener.confirmOTP();
    dismiss();
  }

  private void resendOTPCode() {
    PaymentDataManager.getInstance().resendOTPCode();
    GoSellPaymentActivity listener = (GoSellPaymentActivity) getActivity();
    if(listener==null) return;
    listener.resendOTP();
    dismiss();
  }

}
