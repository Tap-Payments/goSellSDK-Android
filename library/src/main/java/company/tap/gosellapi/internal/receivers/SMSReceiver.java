package company.tap.gosellapi.internal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import company.tap.gosellapi.internal.interfaces.OTPListener;

public class SMSReceiver extends BroadcastReceiver {

    private static OTPListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
      Bundle data = intent.getExtras();
      Object[] pdus = (Object[]) data.get("pdus"); // pdu >> Protocol Data Unit
      for(int i=0; i<pdus.length; i++){
        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
        String message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
            + "Email From: " + smsMessage.getEmailFrom()
            + "Emal Body: " + smsMessage.getEmailBody()
            + "Display message body: " + smsMessage.getDisplayMessageBody()
            + "Time in millisecond: " + smsMessage.getTimestampMillis()
            + "Message: " + smsMessage.getMessageBody();
        mListener.messageReceived(message);
      }
    }

    public static void bindListener(OTPListener listener){
      mListener = listener;
    }
  }