package company.tap.sample.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import company.tap.sample.R;
import company.tap.sample.SettingsActivity;
import company.tap.sample.adapters.CustomerAdapter;
import company.tap.sample.managers.SettingsManager;
import company.tap.sample.viewmodels.CustomerViewModel;

public class CustomerActivity extends AppCompatActivity  implements CustomerAdapter.OnClickListenerInterface{


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        recyclerView = findViewById(R.id.customers_settings_recycler_view);

        CustomerAdapter adapter = new CustomerAdapter(generateRegisteredCustomers(),this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        Drawable divider = ContextCompat.getDrawable(this, company.tap.gosellapi.R.drawable.recycler_divider);
        if (divider != null) {
            dividerItemDecoration.setDrawable(divider);
        }
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    private List<CustomerViewModel> generateRegisteredCustomers() {
        return SettingsManager.getInstance().getRegisteredCustomers(this);
    }


    public void back(View view){
      onBackPressed();
    }


    public void addCustomer(View view){
        Intent intent = new Intent(this,CustomerCRUDActivity.class);
        intent.putExtra("operation",CustomerCRUDActivity.OPERATION_ADD);
        startActivity(intent);
    }

    @Override
    public void onClick(CustomerViewModel viewModel) {
        Intent intent = new Intent(this,CustomerCRUDActivity.class);
        intent.putExtra("customer",viewModel);
        intent.putExtra("operation",CustomerCRUDActivity.OPERATION_EDIT);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }
}
