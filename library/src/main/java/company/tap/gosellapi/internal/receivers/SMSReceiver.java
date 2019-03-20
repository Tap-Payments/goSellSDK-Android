package company.tap.gosellapi.internal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import company.tap.gosellapi.internal.interfaces.OTPListener;


/**
 * The type Sms receiver.
 */
public class SMSReceiver extends BroadcastReceiver {

  private static OTPListener mListener;
  private static final String TAG = "SmsBroadcastReceiver";

  private final String serviceProviderNumber = "";
  private final String serviceProviderSmsCondition = "";

  private static OTPListener listener;

  @Override
  public void onReceive(Context context, Intent intent) {

//      Bundle data = intent.getExtras();
//      Object[] pdus = (Object[]) data.get("pdus"); // pdu >> Protocol Data Unit
//      System.out.println(" SMSReceiver ....  pdus :"+pdus.length);
//      for(int i=0; i<pdus.length; i++){
//        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
//        String message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
//            + "Email From: " + smsMessage.getEmailFrom()
//            + "Emal Body: " + smsMessage.getEmailBody()
//            + "Display message body: " + smsMessage.getDisplayMessageBody()
//            + "Time in millisecond: " + smsMessage.getTimestampMillis()
//            + "Message: " + smsMessage.getMessageBody();
//        mListener.messageReceived(message);
//      }
//    }
//
//    public static void bindListener(OTPListener listener){
//      System.out.println(" SMSReceiver .... ");
//      mListener = listener;
//    }
    System.out.println("SMS ... "+ intent.getAction());
    if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
      String smsSender = "";
      String smsBody = "";
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
          smsSender = smsMessage.getDisplayOriginatingAddress();
          smsBody += smsMessage.getMessageBody();
        }
      } else {
        Bundle smsBundle = intent.getExtras();
        if (smsBundle != null) {
          Object[] pdus = (Object[]) smsBundle.get("pdus");
          if (pdus == null) {
            // Display some error to the user
            System.out.println(TAG  + "SmsBundle had no pdus key");
            return;
          }
          SmsMessage[] messages = new SmsMessage[pdus.length];
          for (int i = 0; i < messages.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            smsBody += messages[i].getMessageBody();
          }
          smsSender = messages[0].getOriginatingAddress();
        }
      }

//      if (smsSender.equals(serviceProviderNumber) && smsBody.startsWith(serviceProviderSmsCondition)) {
        if (listener != null) {
          listener.messageReceived(smsBody);
        }
//      }
    }
    System.out.println("SMS ... "+ intent.getAction());
  }

    /**
     * Sets listener.
     *
     * @param _listener the listener
     */
    public void setListener(OTPListener _listener) {
    listener = _listener;
  }



}