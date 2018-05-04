package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsDataSource;
import company.tap.gosellapi.internal.view_holders.CardCredentialsViewHolder;
import company.tap.gosellapi.internal.view_holders.CurrencyViewHolder;
import company.tap.gosellapi.internal.view_holders.PaymentSystemsViewHolder;
import company.tap.gosellapi.internal.view_holders.RecentSectionViewHolder;

public class PaymentOptionsRecyclerViewAdapter extends RecyclerView.Adapter {

    public interface PaymentOptionsViewAdapterListener {
        void cardScannerButtonClicked();
        void saveCardSwitchCheckedChanged();
        void paymentSystemViewHolderClicked();
        void recentPaymentItemClicked();
    }

    private PaymentOptionsDataSource dataSource;
    private PaymentOptionsViewAdapterListener listener;

    public PaymentOptionsRecyclerViewAdapter(PaymentOptionsDataSource dataSource, PaymentOptionsViewAdapterListener listener) {
        this.listener = listener;
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        PaymentOptionsDataSource.PaymentType type = PaymentOptionsDataSource.PaymentType.valueOf(viewType);
//        return getViewHolderForType(type, parent);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        PaymentOptionsDataSource.PaymentType type = PaymentOptionsDataSource.PaymentType.valueOf(position);
//
//        switch (type) {
//
//            case RECENT:
//                ((RecentSectionViewHolder) holder).bind();
//                break;
//
//            case WEB:
//                ((PaymentSystemsViewHolder) holder).bind();
//                break;
//
//            case CARD:
//                ((CardCredentialsViewHolder) holder).bind();
//                break;
//
//            default:
//
//                 break;
//        }
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getItemViewType(position);
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolderForType(PaymentOptionsDataSource.PaymentType type, ViewGroup parent) {

        switch (type) {

            case CURRENCY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_currency, parent, false);
                return new CurrencyViewHolder(view, this.listener);

            case RECENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view, this.listener);

            case WEB:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false);
                return new PaymentSystemsViewHolder(view, this.listener);

            case CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                return new CardCredentialsViewHolder(view);

            default:
                view = new View(parent.getContext());
                return new BlankViewHolder(view);
        }
    }

    private class BlankViewHolder extends RecyclerView.ViewHolder {

        BlankViewHolder(View itemView) {
            super(itemView);
        }
    }
}
