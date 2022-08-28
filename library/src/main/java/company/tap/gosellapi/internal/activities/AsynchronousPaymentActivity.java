package company.tap.gosellapi.internal.activities;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.enums.AppearanceMode;

public class AsynchronousPaymentActivity extends BaseActivity implements View.OnClickListener {
    TextView textView_emailPhone_sent;
    TextView textView_emailphone;
    TextView textView_ordernumber;
    TextView textView_codeexp_datetime;
    TextView textView_storelink;
    TextView textView_paymentprogress;
    TextView textView_fawry_payreference;
    TextView textView_ordercode;
    TextView textView_codeexp;
    TextView textView_fawrytext;
    String reference;
    String storeURL;
    Button buttonClose;
    String payymentName;
    String imageURL;
    String dateTimestr;
    String phonNumber;
    String emailId;
    private AppearanceMode apperanceMode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // overridePendingTransition(R.anim.slide_in_top,R.anim.fade_out);
        setContentView(R.layout.activity_asynchronous_payment);
        apperanceMode = ThemeObject.getInstance().getAppearanceMode();

      /*  if (apperanceMode == AppearanceMode.WINDOWED_MODE) {
            setContentView(R.layout.activity_asynchronous_payment);
        } else {*/
            setContentView(R.layout.activity_asynchronous_payment_full);
      //  }
        setupHeader();
        initializeView();
        new AsyncTaskGetData().execute();
    }
/**
 * Converting the epoch time period and created time to datetime string.
 *
 * **/

    private String getDateTimeStamp(Long mdateTime) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm a ");
        SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm a ");
        Date dateTime = null;

        try {

            dateTime = dateParser.parse(format.format(mdateTime));
            Log.e("dateTime", "dateTime" + format.format(dateTime));
            dateTimestr = format.format(dateTime);
            return dateTimestr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Initializing the fields in layout.
     */

    private void initializeView() {
        textView_emailPhone_sent = findViewById(R.id.tv_email_phone_sent);
        textView_emailphone = findViewById(R.id.tv_emailaddress_phonenumber);
        textView_ordernumber = findViewById(R.id.tv_ordernumber);
        textView_codeexp_datetime = findViewById(R.id.tv_codeexp_datetime);
        textView_storelink = findViewById(R.id.tv_storelink);
        buttonClose = findViewById(R.id.close_btn);
        textView_paymentprogress = findViewById(R.id.tv_paymentprogress);
        textView_ordercode = findViewById(R.id.tv_ordercode);
        textView_codeexp = findViewById(R.id.tv_codeexp);
        textView_fawrytext = findViewById(R.id.tv_fawrytext);
        textView_fawry_payreference = findViewById(R.id.tv_fawry_payreference);
        Typeface fontstyle = Typeface.createFromAsset(getAssets(),"fonts/roboto_regular.ttf");
        textView_codeexp_datetime.setTypeface(fontstyle);
        textView_emailPhone_sent.setTypeface(fontstyle);
        textView_emailphone.setTypeface(fontstyle);
        textView_storelink.setTypeface(fontstyle);
        textView_paymentprogress.setTypeface(fontstyle);
        textView_fawry_payreference.setTypeface(fontstyle);
        textView_ordercode.setTypeface(fontstyle);
        textView_codeexp.setTypeface(fontstyle);
        textView_fawrytext.setTypeface(fontstyle);
        buttonClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_btn) {
            setResult(RESULT_OK);
            finish();
            PaymentDataManager.getInstance().clearPaymentProcessListeners();
            overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
            if (PaymentDataManager.getInstance().getChargeOrAuthorize() != null) {
                SDKSession.getListener().paymentSucceed(PaymentDataManager.getInstance().getChargeOrAuthorize());
            }
        }
    }
   /**
    * Setting Header fo the screen.
    * */
    private void setupHeader() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView cancel_payment_text = findViewById(R.id.cancel_payment_icon);

        LinearLayout cancel_payment_ll = findViewById(R.id.cancel_payment);

        cancel_payment_ll.setOnClickListener(v -> onBackPressed());

        ////////////////////////////////////////////////////////////////////////////////////////////
        ImageView businessIcon = findViewById(R.id.businessIcon);
        TextView businessName = findViewById(R.id.businessName);
        LinearLayout businessIconNameContainer = findViewById(R.id.businessIconNameContainer);
        businessIconNameContainer.removeView(businessIcon);
        businessIconNameContainer.removeView(businessName);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.leftMargin = 0;
        ll.bottomMargin = 5;
        ll.topMargin = 18;
        ll.gravity = Gravity.CENTER_VERTICAL;
        businessIconNameContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        businessName.setLayoutParams(ll);
        businessIconNameContainer.addView(businessIcon);
        businessIconNameContainer.addView(businessName);
if(PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse()!=null && PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions()!=null)
            for (int i = 0; i < PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions().size() ; i++) {
                if (PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions().get(i).isAsynchronous()){
                    payymentName = PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions().get(i).getName();
                    imageURL = PaymentDataManager.getInstance().getPaymentOptionsDataManager().getPaymentOptionsResponse().getPaymentOptions().get(i).getImage();
                }

            }




        businessName.setTextSize(17);
        businessName.setTextColor(getResources().getColor(R.color.black1));
        businessName.setText(payymentName);
        businessName.setPadding(35, 0, 0, 15);
        businessName.setGravity(Gravity.CENTER_HORIZONTAL);
        Glide.with(this).load(imageURL).apply(RequestOptions.circleCropTransform()).into(businessIcon);

        cancel_payment_ll.removeView(cancel_payment_text);
        toolbar.setBackgroundColor(getResources().getColor(R.color.french_gray_new));


    }
    /**
     * Getting the  data from the Charge response.
     * */

    class AsyncTaskGetData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            if (PaymentDataManager.getInstance().getChargeOrAuthorize() != null) {
                reference = PaymentDataManager.getInstance().getChargeOrAuthorize().getTransaction().getOrder().getReference();
                storeURL = PaymentDataManager.getInstance().getChargeOrAuthorize().getTransaction().getOrder().getStore_url();
                phonNumber = PaymentDataManager.getInstance().getChargeOrAuthorize().getCustomer().getPhone().getNumber();
                emailId = PaymentDataManager.getInstance().getChargeOrAuthorize().getCustomer().getEmail();
                long created = PaymentDataManager.getInstance().getChargeOrAuthorize().getTransaction().getCreated();
                double period = PaymentDataManager.getInstance().getChargeOrAuthorize().getTransaction().getExpiry().getPeriod();
                long experiod = (long) period;
                long totalTime = experiod + created;

                getDateTimeStamp(totalTime);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            textView_ordernumber.setText(reference);
            textView_storelink.setText(storeURL);
            String[] parts = dateTimestr.split(" ");

            textView_codeexp_datetime.setText(parts[0] + " @ " + parts[1] + parts[2]);
            if(phonNumber!=null && emailId != null ){
                textView_emailPhone_sent.setText(R.string.email_sms__sent);
                textView_emailphone.setVisibility(View.GONE);
            }
           else if(phonNumber!=null){
                textView_emailphone.setText(phonNumber);
                textView_emailPhone_sent.setText(R.string.sms_sent);

            }else if(emailId != null){
                textView_emailphone.setText(emailId);
                textView_emailPhone_sent.setText(R.string.email_sent);
            }
        }


    }
}