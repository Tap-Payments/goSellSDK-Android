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
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;
import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener;

public class GoSellPaymentOptionsFragment extends Fragment {

    private PaymentOptionsViewAdapterListener listener;

    public GoSellPaymentOptionsFragment() {
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

        if(context instanceof GoSellPaymentActivity) {
            this.listener = (PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener) context;
        }
        else {
        throw new ClassCastException(context.toString()
                + " must implement GoSellPaymentOptionsFragment.GoSellPaymentOptionsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    private void initMainRecyclerView(View view) {
        RecyclerView paymentOptionsRecyclerView = view.findViewById(R.id.paymentOptionsRecyclerView);

        //Configuring layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        paymentOptionsRecyclerView.setLayoutManager(layoutManager);

        // Configuring MainRecycleViewAdapter and handle PaymentOptionsViewAdapterListener
//        PaymentOptionsRecyclerViewAdapter adapter = new PaymentOptionsRecyclerViewAdapter(new PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener() {
//            @Override
//            public void cardScannerButtonClicked() {
//                listener.cardScannerButtonClicked();
//            }
//
//            @Override
//            public void saveCardSwitchCheckedChanged() {
//                listener.saveCardSwitchCheckedChanged();
//            }
//
//            @Override
//            public void paymentSystemViewHolderClicked() {
//                listener.paymentSystemViewHolderClicked();
//            }
//
//            @Override
//            public void recentPaymentItemClicked() {
//                listener.recentPaymentItemClicked();
//            }
//        });

//        paymentOptionsRecyclerView.setAdapter(adapter);
    }
}
