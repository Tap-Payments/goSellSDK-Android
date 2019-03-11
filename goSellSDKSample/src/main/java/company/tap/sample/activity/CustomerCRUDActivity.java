package company.tap.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import java.util.Objects;

import company.tap.sample.R;
import company.tap.sample.managers.SettingsManager;
import company.tap.sample.utils.Validator;
import company.tap.sample.viewmodels.CustomerViewModel;

public class CustomerCRUDActivity extends AppCompatActivity {

    private AppCompatEditText name ;
    private AppCompatEditText middleName;
    private AppCompatEditText lastName;
    private AppCompatEditText email ;
    private AppCompatEditText sdn ;
    private AppCompatEditText mobile ;


    private TextInputLayout name_l;
    private TextInputLayout middlename_l;
    private TextInputLayout lastname_l;
    private TextInputLayout email_l;
    private TextInputLayout mobile_l;
    private TextInputLayout sdn_l;

    private String operation="";
    private CustomerViewModel customer;

    public static String OPERATION_ADD = "add";
    public static String OPERATION_EDIT = "edit";

    public static boolean NAME_IS_VALID = false;
    public static boolean EMAIL_IS_VALID = false;
    public static boolean MOBILE_IS_VALID = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_crudactvity);

        name            = findViewById(R.id.customer_name);
        middleName      = findViewById(R.id.middlename);
        lastName        = findViewById(R.id.lastname);
        email           = findViewById(R.id.customer_email);
        sdn             = findViewById(R.id.sdn);
        mobile          = findViewById(R.id.customer_mobile);

        name_l          = findViewById(R.id.name_il);
        middlename_l    = findViewById(R.id.middlename_il);
        lastname_l      = findViewById(R.id.lastname_il);
        email_l         = findViewById(R.id.email_il);
        mobile_l        = findViewById(R.id.mobile_il);
        sdn_l           = findViewById(R.id.sdn_il);

         operation = getIntent().getStringExtra("operation");

        if(operation .equalsIgnoreCase(OPERATION_EDIT)){
             customer = (CustomerViewModel) getIntent().getSerializableExtra("customer");
            System.out.println(" customer : "+ customer.getEmail() );
            populateCustomerFields(customer);
        }

    }



    private void populateCustomerFields(CustomerViewModel customer) {
        name.setText(customer.getName());
        middleName.setText(customer.getMiddleName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        sdn.setText(customer.getSdn());
        mobile.setText(customer.getMobile());
    }






    public void save(View view){
     if(
             Validator.getInstance().isValidName(Objects.requireNonNull(name.getText()).toString().trim()) &&
             Validator.getInstance().isValidEmail(Objects.requireNonNull(email.getText()).toString().trim()) &&
             Validator.getInstance().isValidPhoneNumber(Objects.requireNonNull(mobile.getText()).toString().trim())
     ){
         if(operation .equalsIgnoreCase(OPERATION_ADD)){
             System.out.println("inside: "+ operation);
             SettingsManager.getInstance().saveCustomer(
                     name.getText().toString().trim(),
                     middleName.getText().toString().trim(),
                     lastName.getText().toString().trim(),
                     email.getText().toString().trim(),
                     sdn.getText().toString().trim(),
                     mobile.getText().toString().trim(),
                     this);
             back(null);

         }else if(operation.equalsIgnoreCase(OPERATION_EDIT)){
             SettingsManager.getInstance().editCustomer(customer,
                     new CustomerViewModel(customer.getRef(),
                             name.getText().toString().trim(),
                             middleName.getText().toString().trim(),
                             lastName.getText().toString().trim(),
                             email.getText().toString().trim(),
                             sdn.getText().toString().trim(),
                             mobile.getText().toString().trim()),this);
             back(null);
         }

     }else {

         if(!NAME_IS_VALID)
            name_l.setError(getString(R.string.name_invalid_msg));
         else
             name_l.setError(null);

         if(!EMAIL_IS_VALID)
             email_l.setError(getString(R.string.email_invalid_msg));
         else
             email_l.setError(null);

         if(!MOBILE_IS_VALID)
             mobile_l.setError(getString(R.string.mobile_invalid_msg));
         else
             mobile_l.setError(null);
     }

    }

    public void back(View view){
       onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,CustomerActivity.class));
        finish();

    }
}
