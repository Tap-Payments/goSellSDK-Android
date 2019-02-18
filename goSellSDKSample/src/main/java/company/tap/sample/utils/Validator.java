package company.tap.sample.utils;

import android.text.TextUtils;
import android.util.Patterns;

import company.tap.sample.activity.CustomerCRUDActivity;

public class Validator {


    public boolean isValidEmail(CharSequence target) {

        boolean validation =  (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        System.out.println("isValidEmail :"+ validation);
        CustomerCRUDActivity.EMAIL_IS_VALID = validation;
        return validation;
    }

    public boolean isValidPhoneNumber(CharSequence target){
        boolean validation =   android.util.Patterns.PHONE.matcher(target).matches();
        System.out.println("isValidPhoneNumber :"+ validation);
        CustomerCRUDActivity.MOBILE_IS_VALID = validation;
        return validation;
    }


    public boolean isValidName(CharSequence charSequence){

        boolean validation = charSequence.length() > 2;
        System.out.println("isValidName : "+validation);
        CustomerCRUDActivity.NAME_IS_VALID =validation;
        return validation;
    }


    public static Validator getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }



    private static class SingletonCreationAdmin {
        private static final Validator INSTANCE = new Validator();
    }
}
