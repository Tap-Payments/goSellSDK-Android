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
import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.api.models.CardRawData;
import company.tap.gosellapi.internal.data_managers.GlobalDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.viewholders_and_viewmodels.PaymentOptionsStateManager;

public class GoSellPaymentOptionsFragment extends Fragment {
    public interface PaymentOptionsFragmentListener {
        void startCurrencySelection();
        void startOTP();
        void startWebPayment();
        void startScanCard();
        void cardDetailsFilled(boolean isFilled, CardRawData cardRawData);
    }

    private PaymentOptionsFragmentListener listener;

    private RecyclerView paymentOptionsRecyclerView;
    private LinearLayoutManager layoutManager;
    private PaymentOptionsRecyclerViewAdapter adapter;
    private PaymentOptionsDataManager dataSource;


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
            this.listener = (PaymentOptionsFragmentListener) context;
        }
        else {
        throw new ClassCastException(context.toString()
                + " must implement PaymentOptionsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    private void initMainRecyclerView(View view) {
        paymentOptionsRecyclerView = view.findViewById(R.id.paymentOptionsRecyclerView);
        dataSource = GlobalDataManager.getInstance().getPaymentOptionsDataManager();

        //Configuring layout manager
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        paymentOptionsRecyclerView.setLayoutManager(layoutManager);

        // Configuring MainRecycleViewAdapter and handle PaymentOptionsViewAdapterListener
        adapter = new PaymentOptionsRecyclerViewAdapter(dataSource,
                new PaymentOptionsRecyclerViewAdapter.PaymentOptionsViewAdapterListener() {
                    @Override
                    public void currencyHolderClicked() {
                        listener.startCurrencySelection();
                    }

                    @Override
                    public void recentPaymentItemClicked(int position, Card recentItem) {
                        listener.startOTP();
                    }

                    @Override
                    public void webPaymentSystemViewHolderClicked(int position) {
                        listener.startWebPayment();
                    }

                    @Override
                    public void cardScannerButtonClicked() {
                        listener.startScanCard();
                    }

                    @Override
                    public void saveCardSwitchCheckedChanged(int position, boolean isChecked) {
                        //show or hide save card details by modifying dataSource?
                    }

                    @Override
                    public void cardDetailsFilled(boolean isFilled, CardRawData cardRawData) {
                        listener.cardDetailsFilled(isFilled, cardRawData);
                    }
                });

        paymentOptionsRecyclerView.setAdapter(adapter);
        restoreRecyclerState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveRecyclerState();
    }

    private void saveRecyclerState() {
        PaymentOptionsStateManager.getInstance().saveState(paymentOptionsRecyclerView, adapter);
    }

    private void restoreRecyclerState() {
//        Parcelable savedState = PaymentOptionsStateManager.getInstance().getSavedTopState();
//        if (savedState != null) {
//            layoutManager.onRestoreInstanceState(savedState);
//            PaymentOptionsStateManager.getInstance().setSavedTopState(null);
//        }
    }
}
