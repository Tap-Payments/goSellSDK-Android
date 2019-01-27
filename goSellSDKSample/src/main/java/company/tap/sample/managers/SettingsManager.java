package company.tap.sample.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import company.tap.sample.viewmodels.CustomerViewModel;

public class SettingsManager {

    public static SettingsManager getInstance(){
        return SingleInstanceAdmin.instance;
    }

    public void saveCustomer(String name,String middle,String last, String email,String sdn, String mobile, Context ctx) {

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);

        Gson gson = new Gson();

        String response = preferences.getString("customers" , "");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        if(customersList==null) customersList = new ArrayList<>();


        customersList.add(new CustomerViewModel(name,middle,last,email,sdn,mobile));


        String data =  gson.toJson(customersList);

        writeCustomersToPreferences(data,preferences);
    }

    public void editCustomer(CustomerViewModel oldCustomer, CustomerViewModel newCustomer, Context ctx){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);

        Gson gson = new Gson();

        String response = preferences.getString("customers","");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        if(customersList!=null){
//            Iterator iterator = customersList.iterator();
//            while (iterator.hasNext()){
//                CustomerViewModel cc = (CustomerViewModel) iterator.next();
//
//                if( cc.compareTo(oldCustomer) == 0 )
//                {
//                    System.out.println(" match customer ......" + oldCustomer.getMobile());
//                    iterator.remove();
//                }
//            }
            customersList.clear();

            customersList.add(new CustomerViewModel(
                    newCustomer.getName(),
                    newCustomer.getMiddleName(),
                    newCustomer.getLastName(),
                    newCustomer.getEmail(),
                    newCustomer.getSdn(),
                    newCustomer.getMobile()));

            String data =  gson.toJson(customersList);

            writeCustomersToPreferences(data,preferences);

        }else {
            saveCustomer(newCustomer.getName(),
                    newCustomer.getMiddleName(),
                    newCustomer.getLastName(),
                    newCustomer.getEmail(),
                    newCustomer.getSdn(),
                    newCustomer.getMobile(),ctx);
        }
    }

    private void writeCustomersToPreferences(String data, SharedPreferences preferences){
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString("customers",data);
        editor.commit();
    }

    public List<CustomerViewModel> getRegisteredCustomers(Context ctx) {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(ctx);
        Gson gson = new Gson();

        String response = preferences.getString("customers","");

        ArrayList<CustomerViewModel> customersList = gson.fromJson(response,
                new TypeToken<List<CustomerViewModel>>(){}.getType());

        return (customersList!=null)?customersList:new ArrayList<>();
    }


    private static class SingleInstanceAdmin{
        static SettingsManager instance = new SettingsManager();
    }
}
