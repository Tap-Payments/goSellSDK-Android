package company.tap.gosellapi.internal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.activities.MainActivity;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter.MainRecyclerViewAdapterListener;

public class MainScreenFragment extends Fragment {

    private MainRecyclerViewAdapterListener listener;

    public MainScreenFragment() {
        // Required empty public constructor
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof MainActivity) {
            this.listener = (MainRecyclerViewAdapterListener) context;
        }
        else {
        throw new ClassCastException(context.toString()
                + " must implement MainScreenFragment.MainScreenFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    private void initMainRecyclerView(View view) {
        RecyclerView mainRecyclerView = view.findViewById(R.id.mainRecyclerView);

        //Configuring layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(layoutManager);

        // Configuring MainRecycleViewAdapter and handle MainRecyclerViewAdapterListener
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(new MainRecyclerViewAdapter.MainRecyclerViewAdapterListener() {
            @Override
            public void cardScannerButtonClicked() {
                listener.cardScannerButtonClicked();
            }

            @Override
            public void saveCardSwitchCheckedChanged() {
                listener.saveCardSwitchCheckedChanged();
            }

            @Override
            public void paymentSystemViewHolderClicked() {
                listener.paymentSystemViewHolderClicked();
            }

            @Override
            public void recentPaymentItemClicked() {
                listener.recentPaymentItemClicked();
            }
        });

        mainRecyclerView.setAdapter(adapter);
    }
}
