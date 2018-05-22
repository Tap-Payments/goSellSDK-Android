package company.tap.gosellapi.internal.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;

public class GoSellPaymentOptionsFragment extends Fragment {
    private static final String DATA_SOURCE_ARGUMENT = "dataSourceArgument";

    private RecyclerView paymentOptionsRecyclerView;
    private LinearLayoutManager layoutManager;
    private PaymentOptionsRecyclerViewAdapter adapter;
    private PaymentOptionsDataManager dataSource;
    private Parcelable layoutManagerState;

    public GoSellPaymentOptionsFragment() {
        // Required empty public constructor
    }

    public static GoSellPaymentOptionsFragment newInstance(PaymentOptionsDataManager dataSource) {
        GoSellPaymentOptionsFragment fragment = new GoSellPaymentOptionsFragment();
        fragment.dataSource = dataSource;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gosellapi_fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMainRecyclerView(view);
        restoreRecyclerState();
    }

    private void initMainRecyclerView(View view) {
        paymentOptionsRecyclerView = view.findViewById(R.id.paymentOptionsRecyclerView);

        //Configuring layout manager
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        paymentOptionsRecyclerView.setLayoutManager(layoutManager);

        // Configuring MainRecycleViewAdapter and handle PaymentOptionsViewAdapterListener
        adapter = new PaymentOptionsRecyclerViewAdapter(dataSource);

        paymentOptionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveRecyclerState();
    }

    private void saveRecyclerState() {
        layoutManagerState = layoutManager.onSaveInstanceState();
        dataSource.saveState();
    }

    private void restoreRecyclerState() {
        if (layoutManagerState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerState);
            layoutManagerState = null;
        }
    }
}
